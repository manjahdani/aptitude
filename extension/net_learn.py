import shutil
import numpy as np
import os
import sys
import torch
from multiprocessing import Pool, cpu_count, freeze_support
import gc
import functools
import tempfile
import csv

sys.path.append(os.path.join(sys.path[0], "yolov10", "ultralytics"))
from ultralytics import YOLO

BATCH_SIZE = 16
MAX_PROCESSES = cpu_count()
PATH = "."
PATH_TO_DATA = "./data"
DEFAULT_SUB_SAMPLE = 256

TRAIN = True
EVALUATE=True

TRAIN_PARAMS={"exist_ok":True,
              "deterministic":False,
              "batch":BATCH_SIZE,
              "optimizer":'SGD',
              "lr0":5e-3,
              "lrf":5e-3,
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
            return {1:"NA", 2:"NA", 3:"NA", 4:"NA", 5:"NA"}
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

def copy_file(args):
    src, dst = args
    shutil.copy(src, dst)

def parallel_copy(index, in_folder, out_folder, labelsFolder):
    """
    :param index: an array of the name of the images that are selected ('e.g. ['frame_0001','frame_0020'])
    :param in_folder: path to the directory of the source folder containing images and labels subfolders (e.g., "C:/banks")
    :param out_folder: path to the dest (e.g., "C:/train")

    Create a new directory that copies all the images and the labels following the index in a new folder
    """

    if isinstance(index,str) and index == 'all':
        index = os.listdir(os.path.join(in_folder, "images"))


    images = os.listdir(os.path.join(in_folder, "images")) # Source of the bank images
    labels = os.listdir(os.path.join(in_folder, labelsFolder)) # Source of the bank of labels
    
    os.makedirs(os.path.join(out_folder, "images"), exist_ok=True) # Create image directory in out_folder if it doesn't exist in out_folder
    os.makedirs(os.path.join(out_folder, "labels"), exist_ok=True) # Create labels directory in out_folder if it doesn't exist in out_folder

    print(f'Copying {len(index)} images-label pairs from {in_folder} (containing {len(images)} pairs) to {out_folder}.')

    copy_args = []
    for img in index:
        img_with_label = os.path.splitext(img)[0] + ".txt"
        assert img in images, (
            "Source bank folder does not contain image with name file - "
            + img
        )
        assert img_with_label in labels, (
            "Source folder does not contain a file - " + img_with_label
        )

        copy_args.append((os.path.join(in_folder, "images", img),
                          os.path.join(out_folder, "images", img)))
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

def encode_results(results, csv_path, agent_stream, mixed_streams, proportions, name=None):
    if not os.path.isfile(csv_path):
        with open(csv_path, 'w') as f:
            writer = csv.writer(f)
            writer.writerow(['name', 'agent_dataset', 'mixed_datasets', 'proportions', 'n_agents_mixed', 
                            'precision', 'recall', 'mAP50', 'mAP50-95', 'fitness'])

    # obtain name of directory of agent streams as code name for results
    agent_dataset = os.path.basename(agent_stream)  
    mixed_datasets = ', '.join([os.path.basename(stream) for stream in mixed_streams])
    str_proportions = ':'.join(map(str,proportions))

    name = 'blank' if name is None else name

    with open(csv_path, 'a+') as f:
        writer = csv.writer(f)
        writer.writerow([name,agent_dataset, mixed_datasets, str_proportions, len(mixed_streams)+1, *list(results.values())])

def select_random_images(directory, proportion):
    # List all files in the directory
    all_files = os.listdir(os.path.join(directory, "images"))
    
    # Filter out non-image files (assuming common image extensions)
    image_files = [file for file in all_files if file.lower().endswith(('.png', '.jpg', '.jpeg', '.gif', '.bmp', '.tiff'))]

    # Calculate the number of images to select
    num_images_to_select = int(len(image_files) * proportion)
    
    # Randomly select the specified number of images
    selected_images = np.random.choice(image_files, num_images_to_select, replace=False)
    
    return selected_images

class Agent():
    def __init__(self, id, model, weights, stream):
        """
        :param id: The id of the agent, immutable (int).
        :param model: The DNN architecture used by the agent (e.g. YOLO instance)
        :param weights: The initial DNN weights (e.g. "path/to/weights.pt", "ultralytics/yolovXX.pt")
        :param stream: The directory with the data stream of the agent (e.g. "path/to/data/camX/weekX/bank")

        Instantiates an agent -> a DNN that is specialized on a correlated stream
        """
        self._ID = id
        self.model = model
        self.weights = weights
        self.stream = stream

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
    def train(self, mixed_streams, n_iterations, proportions, temp_dir, train_name=None):
        """
        :param mixed_streams: list of streams from other agents used to enrich training (e.g. ["path/to/stream_1", "path/to/stream_2"])
        :param n_iterations: the number of backpropagations desired for training (as n_iteration = n_batches*n_epochs, and the number of batches is not consistent).
        :param proportions: list of the proportions of the datasets of the agents in the network. Last element of the list is for the new agent. Total proportions can go above 1.
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

        train_path = os.path.join(self.stream, "train")
        val_path = os.path.join(self.stream, "val")

        device = "cuda:0" if torch.cuda.is_available() else None

        #copy data from agent stream to temp file for training
        selected_images = select_random_images(train_path, proportions[-1])
        parallel_copy(selected_images, train_path, train_dir,'labels')

        #copy data from other agents' streams to temp file for training
        for stream_id, stream in enumerate(mixed_streams):
            train_path = os.path.join(stream, "train")
            selected_images = select_random_images(train_path, proportions[stream_id])
            parallel_copy(selected_images, train_path, train_dir,'labels')

        #copy data from validation set of agent stream to temp file
        parallel_copy("all", val_path, val_dir,'labels')

        #make yaml file to give instructions for training
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        name = f"agent_{self._ID}_train_{self._train_id}" if train_name is None else train_name + "_train"

        n_data = len(os.listdir(os.path.join(train_dir, "images")))

        # convert the number of backpropagation to epochs depending on the BATCH_SIZE and size of training set
        n_epochs = int(np.round(n_iterations*BATCH_SIZE/n_data))

        self.model.train(data=os.path.join(temp_dir,'TRAIN_YAML.yaml'), epochs=n_epochs, name=name, device=device, **TRAIN_PARAMS)

        self.weights = f"runs/detect/{name}/weights/best.pt"
        self.flush_model()

    @check_evaluate
    @with_temp_dir
    def evaluate(self, temp_dir, test_name=None):
        test_dir = os.path.join(temp_dir, 'val')
        test_path = os.path.join(self.stream, "test")

        os.makedirs(test_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        #copy data from validation set of agent stream to temp file
        parallel_copy("all", test_path, test_dir,'labels')

        #make yaml file to give instructions for testing
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        name = f"agent_{self._ID}_test_{self._train_id}" if test_name is None else test_name + "_test"
        results = self.model.val(data=os.path.join(temp_dir,'TRAIN_YAML.yaml'), name=name, device=device, verbose=False, plots=False).results_dict

        self.flush_model()

        return results

    def __repr__(self):
        return("agent_{}".format(self._ID))

class Network():
    def __init__(self, agents_list):
        self.agents_list=agents_list
        self.n_agents=len(agents_list)

    def train_new_agent(self, agent, n_iterations, proportions, csv_path, name=None):
        """
        :param agent: instance of Agent that is not the the network agent_list
        :param n_iterations: the number of backpropagations desired for training (as n_iteration = n_batches*n_epochs, and the number of batches is not consistent).
        :param proportions: list of the proportions of the datasets of the agents in the network. Last element of the list is for the new agent. Total proportions can go above 1.

        Trains a new agent by mixing its stream with streams of a random subset of the network.
        """

        #train and test the new agent
        mixed_streams = [agent.stream for agent in self.agents_list]
        agent.train(mixed_streams, n_iterations, proportions, train_name=name)

        results = agent.evaluate(test_name=name)

        encode_results(results, csv_path, agent.stream, mixed_streams, proportions, name=name)

class Experimental_Environment:
    def __init__(self, n_seeds, all_weights, all_streams, all_ids=None):
        """
        :param n_seeds: number of distinct network to repeat experiments
        :param all_weights: list of all the weights of all available agents (["path/to/weight_1.pt", "path/to/weight_2.pt"])
        :param all_streams: list of all the streams of all available agents (["path/to/stream_1", "path/to/stream_2"])
        :param all_ids: custom id for the agents. If None, provides a range of integer from 0 to n_agents

        Build an agent for each provided stream, then builds n_seeds distinct networks of agents for experimentation.
        """

        if n_seeds > len(all_streams):
            raise ValueError("n_seeds limited to the number of evaluated agents")

        if all_ids==None:
            all_ids = list(range(len(all_streams)))

        all_models = [YOLO(weights) for weights in all_weights]

        #build the agents from their streams and weights
        all_agents = [Agent(id, model, weights, stream) for id, model, weights, stream in zip(all_ids, all_models, all_weights, all_streams)]

        # list of excluded agent in each network
        self.out_agents = all_agents.copy()[:n_seeds]

        #generate a list of networks with a distinct excluded agent for each network
        self.networks = [Network([agent for agent in all_agents if agent!=out_agent]) for out_agent in self.out_agents]

    def main(self, csv_path, n_iterations=10_000):
        n_seeds = len(self.networks)
        
        all_proportions=np.vstack((np.hstack((np.zeros((1, n_seeds-1)), np.ones((1,1)))),np.ones(n_seeds),np.array([0.1094]*5+[0.1133]*4)))


        for i in range(n_seeds):
            self.networks[i].train_new_agent(self.out_agents[i], n_iterations, all_proportions[0], csv_path, name=f"cam{i+1}_alone_100")        
            self.out_agents[i].weights = "yolov10n"
            self.out_agents[i].flush_model()
            self.networks[i].train_new_agent(self.out_agents[i], n_iterations, all_proportions[1], csv_path, name=f"cam{i+1}_all_agents_100")
            self.out_agents[i].weights = "yolov10n"
            self.out_agents[i].flush_model()
            self.networks[i].train_new_agent(self.out_agents[i], n_iterations, all_proportions[2], csv_path, name=f"cam{i+1}_all_agents_10")
            self.out_agents[i].weights = "yolov10n"
            self.out_agents[i].flush_model()

if __name__ == '__main__':
    #freeze_support()  

    all_weights = ["yolov10n"]*9
    all_streams = [os.path.join(PATH_TO_DATA,f'cam{i}') for i in range(1,10)]

    all_ids = [f"cam{i}" for i in range(1,10)]

    the_env = Experimental_Environment(9, all_weights, all_streams, all_ids)
    the_env.main('learning_alone_vs_group.csv', n_iterations=10_000)