import shutil
import numpy as np
import os
import sys
import torch
import matplotlib.pyplot as plt
from multiprocessing import Pool, cpu_count
from tqdm import tqdm
import gc
import functools
import tempfile
import time
import math

from settings import *

sys.path.append(os.path.join(sys.path[0], "yolov8", "ultralytics"))
from ultralytics import YOLO

TRAIN_PARAMS:{"exist_ok":True,
              "deterministic":False,
              "epochs":100,
              "batch":16,
              "optimizer":'SGD',
              "lr0":5e-3,
              "lrf":5e-3
              "patience":1000, 
              "plots":False,
              "workers":4,
              "verbose":True}

def check_train(func):
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        global TRAIN
        if TRAIN:
            return func(*args, **kwargs)
        else:
            print("Skipping model training (TRAIN set to False).")
    return wrapper

def check_evaluate(func):
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        global EVALUATE
        if EVALUATE:
            return func(*args, **kwargs)
        else:
            print("Skipping model evaluation (EVALUATE set to False).")
    return wrapper

def with_temp_dir(func):
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        # Create a temporary directory
        with tempfile.TemporaryDirectory() as temp_dir:
            print(f"Temporary directory created at: {temp_dir}")
            # Pass the temporary directory path to the function
            result = func(*args, temp_dir=temp_dir, **kwargs)
        # Temporary directory and its contents are automatically cleaned up
        return result
    return wrapper

class SamplingException(Exception):
    pass

def thresholding_top_confidence(image_labels_path:str, n:int=DEFAULT_SUB_SAMPLE, warmup_length:int=720, sampling_rate:float=0.10) -> list:
    """
    Performs active learning for object detection using the confidence scores.

    Parameters:
    - image_labels_path: paths to the .txt files with the object detections (last element of each line = confidence score).
    - n: number of images to label.

    Returns:
    - images_to_label: list of strings, paths to the .txt files with the images to be labeled
    """

    txt_files = np.array(os.listdir(image_labels_path))
    if n <= 0:
        raise SamplingException(f"You must select a strictly positive number of frames")
    if n > len(txt_files):
        raise SamplingException(f"Image bank contains {len(txt_files)} frames, but {n} frames where required"
                                )
    confidences = np.empty(txt_files.shape[0])
    for i,txt_file in tqdm(enumerate(txt_files), desc='Analysing files...'):
        if os.path.getsize(os.path.join(image_labels_path, txt_file))!=0: 
            file_data = np.loadtxt(os.path.join(image_labels_path, txt_file))
            image_confidence = np.max(file_data[...,-1])
            confidences[i] = image_confidence

    # Get the warm-up set
    warmup_set = confidences[:warmup_length]

    # Compute the threshold
    threshold = np.percentile(warmup_set, 100 * (1 - sampling_rate))

    confidences = confidences[warmup_length:]
    txt_files = txt_files[warmup_length:]

    top_conf = np.argwhere(confidences > threshold).flatten()

    # Filtering images based on the confidence scores
    top_confidence_images = txt_files[top_conf]

    # Get N-first images with a confidence lower than the threshold
    images_to_label = [os.path.splitext(img)[0] for img in top_confidence_images[:n]]

    return images_to_label

def parallel_copy(index, in_folder, out_folder, labelsFolder):
    """
    :param index: an array of the name of the images that are selected ('e.g. ['frame_0001','frame_0020'])
    :param in_folder: path to the directory of the source folder containing images and labels subfolders (e.g., "C:/banks")
    :param out_folder: path to the dest (e.g., "C:/train")

    Create a new directory that copies all the images and the labels following the index in a new folder
    """
    def copy_file(args):
        src, dst = args
        shutil.copy(src, dst)

    if index == 'all':
        index = [os.path.splitext(file)[0] for file in os.listdir(os.path.join(in_folder, "images"))]


    images = os.listdir(os.path.join(in_folder, "images")) # Source of the bank images
    labels = os.listdir(os.path.join(in_folder, labelsFolder)) # Source of the bank of labels
    camwithjpg = ["cam1","cam2","cam3","cam4","cam5","cam6","cam7","cam8","cam9"]
    camwithpng= ["cam16","cam17","cam18","cam19","cam20","cam22","cam24"]

    imgExtension="jpg"
    for cam in camwithpng:
        if cam in in_folder:
            imgExtension="png"
    
    os.makedirs(os.path.join(out_folder, "images"), exist_ok=True) # Create image directory in out_folder if it doesn't exist in out_folder
    os.makedirs(os.path.join(out_folder, "labels"), exist_ok=True) # Create labels directory in out_folder if it doesn't exist in out_folder

    description = f'Copying {len(index)} images-label pairs from {in_folder} containing {len(images)} pairs.'

    copy_args = []
    for img in tqdm(index, desc=description):
        img_with_extension = img + str(".") + imgExtension
        img_with_label = img + ".txt"
        assert img_with_extension in images, (
            "Source bank folder does not contain image with name file - "
            + img_with_extension
        )
        assert img_with_label in labels, (
            "Source folder does not contain a file - " + img_with_label
        )

        copy_args.append((os.path.join(in_folder, "images", img_with_extension),
                          os.path.join(out_folder, "images", img_with_extension)))
        copy_args.append((os.path.join(in_folder, labelsFolder, img_with_label),
                          os.path.join(out_folder, "labels", img_with_label)))

    with Pool(processes=MAX_PROCESSES) as pool:
        pool.map(copy_file, copy_args)

def build_yaml_file(path: str, base_file: str):
    """
    :param path: the path to the dataset.
    :param base_file: the path to the yaml template.

    Creates a yaml file from a template with the location of the training dataset added.
    """
    with open(base_file, 'r') as f:
        lines = f.readlines()

    #add the path at the proper location in the template
    modified_lines = [
        f'path : {path}\n' if 'path:' in line else line
        for line in lines
    ]

    with open(f'{path}/TRAIN_YAML.yaml', 'w') as f:
        f.writelines(modified_lines)

class Agent():
    def __init__(self, id, model, weights, stream, buffer_policy=thresholding_top_confidence):
    """
    :param id: The id of the agent, immutable (int).
    :param model: The DNN architecture used by the agent (e.g. YOLO instance)
    :param weights: The initial DNN weights (e.g. "path/to/weights.pt", "ultralytics/yolovXX.pt")
    :param stream: The directory with the data stream of the agent (e.g. "path/to/data/camX/weekX/bank")
    :param buffer_policy: Function that selects the data from the stream that are used for training (e.g. thresholding_top_confidence)

    Instantiates an agent -> a DNN that is specialized on a correlated stream
    """
        self._ID = id
        self.model = model
        self.weights = weights
        self.stream = stream

        #Buffer: list of files from the stream used for training
        self.buffer = buffer_policy(os.path.join(stream,'labels_yolov8n_w_conf'))

        # id to identify the training weights in case of re-training
        self._train_id = 0

    def flush_model(self):
    """
    Re-instantiates the agent model to circumvent ultralytics limitations.
    """
        del self.model
        gc.collect() 
        self.model = YOLO(os.path.join(PATH, self.weights))

    @check_train
    @with_temp_dir
    def train(self, mixed_streams, mixed_buffers, temp_dir, train_name=None):
        """
        :param mixed_streams: list of streams from other agents used to enrich training (e.g. ["path/to/stream_1", "path/to/stream_2"])
        :param mixed_buffers: list of list of files from other agents streams used for training
        :param temp_dir: temporary directory provided by the decorator @with_temp_dir. Do not fill.
        :param train_name: name of the resulting training weights. If None, will use the format "agent_{self._ID}_train_{self._train_id}".

        Trains the agent DNN on samples from the agents stream and streams from other agents.
        """

        #increment the training id
        self._train_id+=1

        # change weights so that they do not overwrite
        train_dir = os.path.join(temp_dir, 'train')
        val_dir   = os.path.join(temp_dir, 'val')

        os.makedirs(train_dir)
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        #copy data from agent stream to temp file for training
        parallel_copy(self.buffer, self.stream, train_dir,'labels_yolov8x6')

        #copy data from other agents' streams to temp file for training
        for buffer, stream in zip(mixed_buffers, mixed_streams):
            parallel_copy(buffer, stream, train_dir,'labels_yolov8x6')

        #copy data from validation set of agent stream to temp file
        test_path = get_test_path_from_train_path(self.stream)
        parallel_copy("all", test_path, val_dir,'labels')

        #make yaml file to give instructions for training
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        weights_name = f"agent_{self._ID}_train_{self._train_id}" if train_name is None else train_name

        self.model.train(data=os.path.join(temp_dir,'TRAIN_YAML.yaml'), name=weights_name, device=device, **TRAIN_PARAMS)

        self.weights = weights_name
        self.flush_model()

    @check_train
    @with_temp_dir
    def evaluate(self, temp_dir):
        val_dir = os.path.join(temp_dir, 'val')
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        #copy data from validation set of agent stream to temp file
        test_path = get_test_path_from_train_path(self.stream)
        parallel_copy("all", test_path, val_dir,'labels')

        #make yaml file to give instructions for testing
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        #how to export results ?
        self.model.val(data=os.path.join(agent_dir,'TMP_YAML.yaml'), name='val', device=device, verbose=False, plots=False)

        self.flush_model()

    def __repr__(self):
        return("agent_{}".format(self._ID))

class Network():
    def __init__(self, agents_list):
        self.agents_list=agents_list
        self.n_agents=len(agents_list)

    def random_train_new_agent(self, agent, subset_size, n_reps):
        """
        :param agent: instance of Agent that is not the the network agent_list
        :param subset_size: number of agents whose stream is added to the training set
        :n_reps: number of times the experiment is repeated.

        Trains a new agent by mixing its stream with streams of a random subset of the network.
        """
        if self.n_agents < subset_size:
            raise ValueError("subset_size must be less or equal to the number of agents.")
        
        # limit the number of subsets to the maximum number of distinct subsets.
        max_reps = min(n_reps, math.comb(self.n_agents,subset_size))
        if max_reps<n_reps:
            print(f"Number of subsets reduced to {max_reps}.")

        # generate max_reps distinct subsets of agents
        samples_set = set()
        while len(samples_set) < max_reps:
            sample = tuple(np.sort(np.random.choice(self.agents_list, subset_size, replace=False)))
            samples_set.add(sample)
        subsets_of_mixed_agents = [list(sample) for sample in samples_set]

        #train and test the agent on each sampled subset
        for mixed_agents in subsets_of_mixed_agents:
            mixed_streams = [agent.stream for agent in mixed_agents]
            mixed_buffers = [agent.buffer for agent in mixed_agents]
            agent.train(mixed_streams, mixed_buffers)

            # add agent test functionality
            agent.test()

class Experimental_Environment:
    def __init__(self, n_seeds, all_weights, all_streams, all_ids=None):
        """
        :param n_seeds: number of distinct network to repeat experiments
        :param all_weights: list of all the weights of all available agents (["path/to/weight_1.pt", "path/to/weight_2.pt"])
        :param all_streams: list of all the streams of all available agents (["path/to/stream_1", "path/to/stream_2"])
        :param all_ids: custom id for the agents. If None, provides a range of integer from 0 to n_agents

        Build an agent for each provided stream, then builds n_seeds distinct networks of agents for experimentation.
        """

        if n_seed > len(all_streams):
            raise ValueError("n_seeds limited to the number of evaluated agents")

        if all_ids==None:
            all_ids = list(range(len(all_streams)))

        all_models = [YOLO(weights) for weights in all_weights]

        #build the agents from their streams and weights
        all_agents = [Agent(id, model, weights, stream) for id, model, weights, stream in zip(all_ids, all_models, all_weights, all_streams)]

        # list of excluded agent in each network
        self.out_agents = list(np.random.choice(all_agents, n_seeds, replace=False))

        #generate a list of networks with a distinct excluded agent for each network
        self.networks = [Network([agent for agent in all_agents if agent!=out_agent]) for out_agent in self.out_agents]

    def main(subset_size, n_reps):
        for network, out_agent in zip(self.networks, self.out_agents):
            network.random_train_new_agent(out_agent, subset_size, n_reps)