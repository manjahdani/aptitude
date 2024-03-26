import shutil
import numpy as np
import os
import sys
import torch
import seaborn as sns
import matplotlib.pyplot as plt
from multiprocessing import Pool, cpu_count
from tqdm import tqdm
import gc
import functools
import tempfile
import time

sys.path.append(os.path.join(sys.path[0], "yolov8", "ultralytics"))
from ultralytics import YOLO

PATH = "/home/dani/aptitude/extension"
PATH_TO_DATA = "/home/dani/data/WALT-challenge"
DEFAULT_SUB_SAMPLE = 256
N_OPERATIONS_PER_NEW_AGENT = 10_000
BATCH_SIZE = 16
MAX_PROCESSES = cpu_count()

TRAIN = True
VALIDATE = True
EVALUATE = True

CAM_IDS = [1,2,3,4,5,6,7,8,9,16,17,18,19,20,22,24]
CAM_TO_AGENT = dict(zip(CAM_IDS, range(16)))
AGENT_TO_CAM = dict(zip(range(16), CAM_IDS))

N_WORKERS = 4
LEARNING_RATE = 1e-3
NAME = "GD_m"

network_settings = {
    1: {"Cluster Models 1": "2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 1},
    2: {"Cluster Models 1": "1o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 1},
    3: {"Cluster Models 1": "1o2o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 1},
    4: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 2},
    5: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 2},
    6: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 2},
    7: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 2},
    8: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 2},
    9: {"Cluster Models 1": "1o2o3", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22o24", "Cluster": 1},
    16: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "17o18o19o20o22o24", "Cluster": 3},
    17: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o18o19o20o22o24", "Cluster": 3},
    18: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o19o20o22o24", "Cluster": 3},
    19: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o20o22o24", "Cluster": 3},
    20: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o22o24", "Cluster": 3},
    22: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o24", "Cluster": 3},
    24: {"Cluster Models 1": "1o2o3o9", "Cluster Models 2": "4o5o6o7o8", "Cluster Models 3": "16o17o18o19o20o22", "Cluster": 3}
}

print(f"The current process ID is: {os.getpid()}")

def check_train(func):
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        global TRAIN
        if TRAIN:
            return func(*args, **kwargs)
        else:
            print("Skipping model training (TRAIN set to False).")
    return wrapper

def check_validate(func):
    @functools.wraps(func)
    def wrapper(*args, **kwargs):
        global VALIDATE
        if VALIDATE:
            return func(*args, **kwargs)
        else:
            print("Skipping proximity computation (VALIDATE set to False).")
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

def convert_operations_to_epochs(n_data):
    n_epochs = int(np.round(N_OPERATIONS_PER_NEW_AGENT*BATCH_SIZE/n_data))
    return n_epochs

def build_yaml_file(path:str, base_file:str):
    lines_to_write = []
    with open(base_file, 'r') as f:
        lines = f.readlines()
        for line in lines:
            if 'path:' in line:
                lines_to_write += [f'path : {path}\n']
            else:
                lines_to_write += [line]

    with open(f'{path}/TMP_YAML.yaml', 'w') as f:
        f.writelines(lines_to_write)

def get_test_path_from_train_path(train_path:str)->str:
    cam_index = train_path.find("cam")  # Find the index of "cam" in the path
    if cam_index != -1:  # If "cam" is found in the path
        cam_number = ""
        for char in train_path[cam_index + 3:]:  # Iterate over characters after "cam"
            if char.isdigit():
                cam_number += char
            else:
                break
        new_path = train_path[:cam_index + 3] + cam_number + "/test"  # Construct the new path
        return new_path
    else:
        return None  # Return None if no cam number is found in the path
    
def plot_proximity_heatmap(data, agent):
    # Create a larger figure to improve readabilC'est marrant  en fonctione de ity
    plt.figure(figsize=(10, 8))

    row_labels = ['Coalitional model\non agent data\n($mAP_{50-95}$)', 'Agent model\non coalition data\n($mAP_{50-95}$)', 'Proximity Score']
    column_labels = [f"Coalition {i+1}" for i in range(data.shape[1])]

    # Create the heatmap with row and column labels
    ax = sns.heatmap(data, annot=True, cmap="coolwarm", fmt=".2f",
                    linewidths=.5, cbar_kws={'label': 'Score'},
                    xticklabels=column_labels, yticklabels=row_labels)

    # Improve the appearance of the labels
    plt.xticks(rotation=45, ha="right")  # Rotate column labels for better readability
    plt.yticks(rotation=90, ha="center", va='center', position=(0,1))  # Keep row labels horizontal

    # Add labels and title
    ax.set_title(f'Proximity score of {agent} with every cluster')
    plt.tight_layout()
    # Save the plot
    plt.savefig(f'results/{NAME}_{agent}.jpg', bbox_inches='tight')

    # Clear the figure
    plt.clf()

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

class Agent():
    def __init__(self, id, model, stream, buffer_policy, weights):
        self._ID = id
        self.model = model
        self.weights = weights
        self.stream = stream

        self.buffer = buffer_policy(os.path.join(stream,'labels_yolov8n_w_conf'))

    def flush_model(self):
        del self.model
        gc.collect() 
        self.model = YOLO(os.path.join(PATH, self.weights))

    @check_train
    @with_temp_dir
    def train(self, temp_dir):
        train_dir = os.path.join(temp_dir, 'train')
        val_dir   = os.path.join(temp_dir, 'val')

        os.makedirs(train_dir)
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        parallel_copy(self.buffer, self.stream, train_dir,'labels_yolov8x6')
        test_path = get_test_path_from_train_path(self.stream)
        parallel_copy("all", test_path, val_dir,'labels')
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        n_data = len(self.buffer)

        self.coal_model.train(data=os.path.join(temp_dir,'TMP_YAML.yaml'),
                            name = f"Agent_{self._ID}",
                            exist_ok=True,
                            deterministic=False,
                            epochs=convert_operations_to_epochs(n_data),
                            batch=BATCH_SIZE,
                            device=device,
                            optimizer='SGD',
                            lr0 = LEARNING_RATE,
                            lrf = LEARNING_RATE,
                            patience=1000, 
                            plots=False,
                            workers=N_WORKERS,
                            verbose=True)
        self.weights = f"Agent_{self._ID}"
        self.flush_model()

    def __repr__(self):
        return("Agent #{}".format(self._ID))

class Coalition():
    def __init__(self, id, coal_model, agents_list, weights):
        self._ID=id
        self.agents_list = agents_list
        self.coal_model = coal_model
        self.weights=weights

        self.combined_stream = [agent.stream for agent in agents_list]
        self.combined_buffers = [agent.buffer for agent in agents_list]

    def flush_model(self):
        del self.coal_model
        gc.collect() 
        self.coal_model = YOLO(os.path.join(PATH, self.weights))

    @check_train
    @with_temp_dir
    def train(self, temp_dir):
        train_dir = os.path.join(temp_dir, 'train')
        val_dir   = os.path.join(temp_dir, 'val')

        os.makedirs(train_dir)
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        for buffer,stream in zip(self.combined_buffers, self.combined_stream):
            parallel_copy(buffer, stream, train_dir,'labels_yolov8x6')
            test_path = get_test_path_from_train_path(stream)
            parallel_copy("all", test_path, val_dir,'labels')
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        n_data = np.sum([len(buffer) for buffer in self.combined_buffers])
        self.coal_model.train(data=os.path.join(temp_dir,'TMP_YAML.yaml'),
                            name = f"Coalition_{self._ID}",
                            exist_ok=True,
                            deterministic=False,
                            epochs=convert_operations_to_epochs(n_data),
                            batch=BATCH_SIZE,
                            device=device,
                            optimizer='SGD',
                            lr0 = LEARNING_RATE,
                            lrf = LEARNING_RATE,
                            patience=1000, 
                            plots=False,
                            workers=N_WORKERS,
                            verbose=True)
        
        self.weights=f'runs/detect/Coalition_{self._ID}/weights/best.pt'
        self.flush_model()
        
    def add_agent(self, agent):
        self.agents_list= np.append(self.agents_list,agent)
        self.combined_stream.append(agent.stream)
        self.combined_buffers.append(agent.buffer)
        self.train()

    def remove_agent(self, agent):
        self.agents_list = self.agents_list[self.agents_list!=agent]
        self.combined_stream.remove(agent.stream)
        self.combined_buffers.remove(agent.buffer)
        self.train()

    def __repr__(self):
        return f"Coalition {self._ID}: {self.agents_list}"

class Network():
    def __init__(self, paths_to_data, buffer_policy, trained_models = None, global_model = True, global_model_size='x'):
        n_agents = len(paths_to_data)
        self.all_agents = np.empty(n_agents, dtype=Agent)
        if global_model:
            self.weights = f'ultralytics/yolov8{global_model_size}.pt'
            self.net_model = YOLO(self.weights)
        else:
            self.weights = None

        for i in range(n_agents):
            cam_id = AGENT_TO_CAM[i]
            agent_weights = 'ultralytics/yolov8n.pt' if trained_models is None else trained_models[i]
            model = YOLO(agent_weights)
            agent = Agent(cam_id, model, paths_to_data[i], buffer_policy, agent_weights)
            print(f"{agent} initialized.")
            if trained_models is None:
                agent.train()
            self.all_agents[i] = agent

    def flush_model(self):
        del self.net_model
        gc.collect()
        self.net_model = YOLO(os.path.join(PATH, self.weights))

    @check_train
    @with_temp_dir
    def train(self, temp_dir):
        if self.weights is None:
            return
        train_dir = os.path.join(temp_dir, 'train')
        val_dir   = os.path.join(temp_dir, 'val')

        os.makedirs(train_dir)
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        combined_stream = [agent.stream for agent in self.integrated_agents]
        combined_buffers = [agent.buffer for agent in self.integrated_agents]

        for buffer,stream in zip(combined_buffers, combined_stream):
            parallel_copy(buffer, stream, train_dir,'labels_yolov8x6')
            test_path = get_test_path_from_train_path(stream)
            parallel_copy("all", test_path, val_dir,'labels')
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        n_data = np.sum([len(buffer) for buffer in combined_buffers])

        self.net_model.train(data=os.path.join(temp_dir,'TMP_YAML.yaml'),
                            name = "Network",
                            exist_ok=True,
                            deterministic=False,
                            epochs=convert_operations_to_epochs(n_data),
                            batch=BATCH_SIZE,
                            device=device,
                            optimizer='SGD',
                            lr0 = LEARNING_RATE,
                            lrf = LEARNING_RATE,
                            patience=1000, 
                            plots=False,
                            workers=N_WORKERS,
                            verbose=True)
        self.weights = f'runs/detect/Network/weights/best.pt'
        self.flush_model()

    def clusterize(self, clusters, trained_models=None, coal_model_size='m'):
        n_coals = np.max(clusters)+1
        self.all_coalitions = np.empty(n_coals, dtype=Coalition)
        self.free_agents = self.all_agents[clusters==-1]
        self.integrated_agents = self.all_agents[clusters!=-1]
        for coal in range(n_coals):
            agents_list = self.all_agents[clusters==coal]
            coal_weights = f'ultralytics/yolov8{coal_model_size}.pt' if trained_models is None else trained_models[coal]
            model = YOLO(coal_weights)
            coalition = Coalition(coal, model, agents_list, coal_weights)
            if trained_models is None:
                coalition.train()
            self.all_coalitions[coal]=coalition    
        self.integrated_agents = self.all_agents[clusters!=-1]
        self.train()
        for coal_id in range(n_coals):
            self.evaluate(0, coal_id, name=NAME)
        self.print_state("Initialization")
        
    @check_validate
    @with_temp_dir
    def compute_proximity(self, agent, temp_dir):
        agent_dir     = os.path.join(temp_dir,'agent')
        agent_val_dir = os.path.join(agent_dir, 'val')
        os.makedirs(agent_val_dir)
        coal_dir      = os.path.join(temp_dir,'coal')
        coal_val_dir  = os.path.join(coal_dir, 'val')
        os.makedirs(coal_val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None
        parallel_copy(agent.buffer, agent.stream, agent_val_dir,'labels_yolov8x6')
        build_yaml_file(agent_dir, 'templates/base.yaml')
        agent_chal_mAP = np.zeros(len(self.all_coalitions))
        coal_chal_mAP = np.zeros(len(self.all_coalitions))
        for coal, coalition in enumerate(self.all_coalitions):
            for buffer, stream in zip(coalition.combined_buffers, coalition.combined_stream):
                parallel_copy(buffer, stream, coal_val_dir,'labels_yolov8x6')
            build_yaml_file(coal_dir, 'templates/base.yaml')

            coal_chal_mAP[coal] = agent.model.val(data=os.path.join(coal_dir,'TMP_YAML.yaml'), device=device, verbose=False, plots=False, name='val').box.map
            agent_chal_mAP[coal] = coalition.coal_model.val(data=os.path.join(agent_dir, 'TMP_YAML.yaml'), device=device, verbose=False, plots=False, name='val').box.map
            agent.flush_model()
            coalition.flush_model()

            shutil.rmtree(coal_dir)
            os.makedirs(coal_val_dir)

        proximity_score = np.sqrt(agent_chal_mAP*coal_chal_mAP)

        plot_proximity_heatmap(np.array([agent_chal_mAP, coal_chal_mAP, proximity_score]), agent)

        return proximity_score

    def add_agent(self, agent):
        proximity_score = None if len(self.all_coalitions)==1 else self.compute_proximity(agent)
        if proximity_score is None:
            best_fit_coal = 0
        else:
            best_fit_coal = np.argmax(proximity_score)
            
        self.all_coalitions[best_fit_coal].add_agent(agent)
        self.free_agents = self.free_agents[self.free_agents!=agent]
        self.integrated_agents = np.append(self.integrated_agents, agent)

        self.train()

        return best_fit_coal

    def remove_agent(self, coalition, agent):
        coalition.remove_agent(agent)
        self.free_agents = np.append(self.free_agents,agent)
        self.integrated_agents = self.integrated_agents[self.integrated_agents!=agent]
        self.train()

    def routine_add_agents(self, order):
        agents_to_add = self.free_agents[order]
        for ag, agent in enumerate(agents_to_add):
            best_fit_coal = self.add_agent(agent)
            self.evaluate(ag+1, best_fit_coal, name=NAME)
            self.print_state(f"Agent addition #{ag+1}")
        return agents_to_add

    def routine_remove_agents(self, order, agents_to_rmv=None):
        name = NAME + '_rmv'
        if agents_to_rmv is None:
            agents_to_rmv = self.integrated_agents[order]
        for ag, agent in enumerate(agents_to_rmv):
            for coal,coalition in enumerate(self.all_coalitions):
                if agent in coalition.agents_list:
                    coal_to_trim = coalition
                    coal_id = coal
            self.remove_agent(coal_to_trim, agent)
            self.evaluate(ag+1, coal_id, name=name)
            self.print_state(f"Agent deletion #{ag+1}")
    
    def routine_add_and_remove_agents(self, order):
        agents_to_rmv = self.routine_add_agents(order)
        self.routine_remove_agents(order, agents_to_rmv)

    @check_evaluate
    @with_temp_dir            
    def evaluate(self,id, coalition_id, temp_dir, name):
        agent_dir     = os.path.join(temp_dir, "agent")
        agent_val_dir = os.path.join(agent_dir, "val")
        os.makedirs(agent_val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        coalition = self.all_coalitions[coalition_id]
        filename = f"results/{name}_mAPs_eval_{id}_coal_{coalition_id}.txt"
        for agent in coalition.agents_list:
            test_path = get_test_path_from_train_path(agent.stream)
            parallel_copy("all", test_path, agent_val_dir,'labels')
            
        build_yaml_file(agent_dir, 'templates/base.yaml')    
            
        mAPs = coalition.coal_model.val(data=os.path.join(agent_dir,'TMP_YAML.yaml'), device=device, verbose=False, plots=False, name='val').box.map
        coalition.flush_model()

        np.savetxt(filename, (mAPs, 1))

    def print_state(self, step_name):
        print("="*30," Information about the network ", "="*30)
        print(f"Step: {step_name}")
        print('')
        print("Agents in network:", self.free_agents)
        for coal in self.all_coalitions:
            print(f"Agents in {coal}")
        print("="*93)


class GDNetwork:
    def __init__(self, paths_to_data, buffer_policy, trained_models, global_model_size):
        n_agents = len(paths_to_data)
        self.all_agents = np.empty(n_agents, dtype=Agent)

        self.weights = f"weights/yolov8{global_model_size}_all_streams_100.pt"
        self.net_model = YOLO(self.weights)

        for i in range(n_agents):
            cam_id = AGENT_TO_CAM[i]
            agent_weights = trained_models[i]
            model = YOLO(agent_weights)
            agent = Agent(cam_id, model, paths_to_data[i], buffer_policy, agent_weights)
            print(f"{agent} initialized.")
            self.all_agents[i] = agent

    def clusterize(self, clusters):
        self.in_agents = self.all_agents[clusters==0]
        self.out_agents = self.all_agents[clusters==-1]
        self.print_state("Initialization")

    def flush_model(self):
        del self.net_model
        gc.collect()
        self.net_model = YOLO(os.path.join(PATH, self.weights))

    @check_train
    @with_temp_dir
    def train(self, temp_dir):
        train_dir = os.path.join(temp_dir, 'train')
        val_dir   = os.path.join(temp_dir, 'val')

        os.makedirs(train_dir)
        os.makedirs(val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None

        combined_stream = [agent.stream for agent in self.in_agents]
        combined_buffers = [agent.buffer for agent in self.in_agents]

        for buffer,stream in zip(combined_buffers, combined_stream):
            parallel_copy(buffer, stream, train_dir,'labels_yolov8x6')
            test_path = get_test_path_from_train_path(stream)
            parallel_copy("all", test_path, val_dir,'labels')
        build_yaml_file(temp_dir,os.path.join('templates','base.yaml'))

        n_data = np.sum([len(buffer) for buffer in combined_buffers])

        self.net_model.train(data=os.path.join(temp_dir,'TMP_YAML.yaml'),
                            name = "Network",
                            exist_ok=True,
                            deterministic=False,
                            epochs=convert_operations_to_epochs(n_data),
                            batch=BATCH_SIZE,
                            device=device,
                            optimizer='SGD',
                            lr0 = LEARNING_RATE,
                            lrf = LEARNING_RATE,
                            patience=1000, 
                            plots=False,
                            workers=N_WORKERS,
                            verbose=True)
        
        self.weights = f'runs/detect/Network/weights/last.pt'
        self.flush_model()

    @check_evaluate
    @with_temp_dir
    def evaluate(self,id, temp_dir, name):
        agent_dir     = os.path.join(temp_dir, "agent")
        agent_val_dir = os.path.join(agent_dir, "val")
        os.makedirs(agent_val_dir)

        device = "cuda:0" if torch.cuda.is_available() else None
        mAPs = np.empty(2)
        filename = f"results/{name}mAPs_eval_{id}.txt"
        for agent in self.in_agents:
            test_path = get_test_path_from_train_path(agent.stream)
            parallel_copy("all", test_path, agent_val_dir,'labels')
        
        build_yaml_file(agent_dir, 'templates/base.yaml')    
        mAPs[0] = self.net_model.val(data=os.path.join(agent_dir,'TMP_YAML.yaml'), device=device, verbose=False, plots=False, name='val').box.map

        shutil.rmtree(agent_dir)
        os.makedirs(agent_val_dir)
    
        for agent in self.out_agents:
            test_path = get_test_path_from_train_path(agent.stream)
            parallel_copy("all", test_path, agent_val_dir,'labels')
        
        build_yaml_file(agent_dir, 'templates/base.yaml')    
        mAPs[1] = self.net_model.val(data=os.path.join(agent_dir,'TMP_YAML.yaml'), device=device, verbose=False, plots=False, name='val').box.map
        self.flush_model()

        presence = np.array([1,0])
        np.savetxt(filename, (mAPs, presence))

    def print_state(self, step_name):
        print("="*30," Information about the network ", "="*30)
        print(f"Step: {step_name}")
        print('')
        print("Agents in network:", self.in_agents)
        print("Agents out of network:", self.out_agents)
        print("="*93)

    def run_experiment(self, clusters, n_reps):
        self.clusterize(clusters)
        self.evaluate(0, name=NAME)
        for rep in range(n_reps):
            self.train()
            self.evaluate(rep+1, name=NAME)

class EventLogger():
    def __init__(self, filename, name):
        self.filename=filename
        with open(self.filename, 'w') as f:
            f.writelines(f"Logging experiment {name}\n")

    def log_order(self, order):
        with open(self.filename, 'a') as f:
            f.writelines(f"[{time.ctime()}] With order {order}\n")

    def log_params(self, params):
        with open(self.filename, 'a') as f:
            f.writelines(f"\t[{time.ctime()}] With parameters {params}\n")

    def end_step(self):
        with open(self.filename, 'a') as f:
            f.writelines("="*90+"\n\n")        

def check_final_insertion(network_settings, default_disposition, cam_to_agent, paths_to_data, trained_models):
    global TRAIN
    global EVALUATE
    TRAIN = EVALUATE = False

    agent_cams = list(network_settings.keys())

    network = Network(paths_to_data, thresholding_top_confidence, trained_models = trained_models)
    for free_agent_cam in agent_cams:  
        free_agent_nb = cam_to_agent[free_agent_cam]

        clusters = default_disposition.copy()
        clusters[free_agent_nb] = -1
        trained_clust_models =[f"weights/{weights_name}.pt" for weights_name in list(network_settings[free_agent_cam].values())[:-1]]
        network.clusterize(clusters, trained_models=trained_clust_models)
        
        network.add_agent(network.free_agents[0], 0)

paths_to_data = [os.path.join(PATH_TO_DATA,'cam1/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam2/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam3/week5/bank'),
                 os.path.join(PATH_TO_DATA,'cam4/week2/bank'),
                 os.path.join(PATH_TO_DATA,'cam5/week3/bank'),
                 os.path.join(PATH_TO_DATA,'cam6/week4/bank'),
                 os.path.join(PATH_TO_DATA,'cam7/week4/bank'),
                 os.path.join(PATH_TO_DATA,'cam8/week3/bank'),
                 os.path.join(PATH_TO_DATA,'cam9/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam16/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam17/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam18/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam19/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam20/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam22/week1/bank'),
                 os.path.join(PATH_TO_DATA,'cam24/week1/bank'),]

trained_models= ['weights/cam1-week1.pt',
                 'weights/cam2-week1.pt',
                 'weights/cam3-week5.pt',
                 'weights/cam4-week2.pt',
                 'weights/cam5-week3.pt',
                 'weights/cam6-week4.pt',
                 'weights/cam7-week4.pt',
                 'weights/cam8-week3.pt',
                 'weights/cam9-week1.pt',
                 'weights/cam16-week1.pt',
                 'weights/cam17-week1.pt',
                 'weights/cam18-week1.pt',
                 'weights/cam19-week1.pt',
                 'weights/cam20-week1.pt',
                 'weights/cam22-week1.pt',
                 'weights/cam24-week1.pt',]

def random_starting_point(default_disposition):
    condition = True
    n_clusts = np.max(default_disposition)+1
    n_agents = len(default_disposition)
    while condition:
        random_order = np.random.permutation(n_agents)
        base = random_order[:n_clusts]
        if np.all(np.in1d(np.arange(n_clusts), default_disposition[base])):
            condition = False
    return random_order

def preset_clusters(order, n_in):
    set_indices = order[:n_in]
    sorted_indices = np.argsort(order[n_in:])

    # Create a new array with positions
    set_order = np.empty_like(sorted_indices, dtype=int)
    set_order[sorted_indices] = np.arange(len(sorted_indices))

    return set_indices, set_order

def test_agent_inclusion(all_seeds, n_clusts, all_n_ins, cluster_model_sizes):
    global NAME
    if type(all_seeds) is int:
        all_seeds = np.arange(all_seeds)

    log = EventLogger("results/agent_inclusion.txt", "Agent inclusion")

    all_dispositions = {1:np.array([ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]),
                        2:np.array([ 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1]),
                        3:np.array([ 0, 0, 0, 1, 1, 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2]),
                        4:np.array([ 0, 0, 0, 1, 1, 1, 1, 1, 0, 3, 3, 2, 2, 2, 3, 2])}

    shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)
    network = Network(paths_to_data, thresholding_top_confidence, trained_models, global_model=False)

    for seed in all_seeds:
        np.random.seed(seed)
        default_order = random_starting_point(all_dispositions[np.max(n_clusts)])
        log.log_order(default_order)
        for n_clust in n_clusts[:-1]:
            default_disposition = all_dispositions[n_clust]
            for n_in in all_n_ins:
                starting_points, order = preset_clusters(default_order, n_in)
                clusters = -np.ones(len(default_disposition), dtype=int)
                clusters[starting_points] = default_disposition[starting_points]
                for csize in cluster_model_sizes:
                    log.log_params([n_clust, n_in, csize])
                    NAME = f"inclusion_seed_{seed}_n_clusts_{n_clust}_n_in_{n_in}_csize_{csize}"

                    network.clusterize(clusters, coal_model_size=csize)
                    network.routine_add_agents(order)
                    shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)
        log.end_step()


def test_integration(n_seeds, cluster_model_sizes, learning_rates):
    default_disposition = np.array([ 0, 0, 0, 1, 1, 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2])
    global VALIDATE
    VALIDATE = False
    global LEARNING_RATE
    global NAME

    shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)
    network = Network(paths_to_data, thresholding_top_confidence, trained_models, global_model=False)

    for seed in range(n_seeds):
        np.random.seed(seed)
        order = np.random.permutation(len(default_disposition)-1)
        clusters = -np.ones(len(default_disposition), dtype=int)
        clusters[np.random.randint(0, high=16)] = 0
        for lr in learning_rates:
            LEARNING_RATE = lr
            for csize in cluster_model_sizes:
                NAME = f"integration_{seed}_complexity_{csize}_lr_{lr}"
                network.clusterize(clusters, coal_model_size=csize)
                network.routine_add_agents(order)
                shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)

def test_removal(n_seeds, cluster_model_sizes, learning_rates):
    global VALIDATE
    VALIDATE = False
    global LEARNING_RATE
    global NAME

    shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)
    network = Network(paths_to_data, thresholding_top_confidence, trained_models, global_model=False)

    for seed in range(n_seeds):
        np.random.seed(seed)
        order = np.random.permutation(16)[:-1]
        clusters = np.zeros(16, dtype=int)

        for lr in learning_rates:
            LEARNING_RATE = lr
            for csize in cluster_model_sizes:
                NAME = f"gracefully_degrade_{seed}_complexity_{csize}_lr_{lr}"
                network.clusterize(clusters, trained_models=[f"weights/yolov8{csize}_all_streams_100.pt"])
                network.routine_remove_agents(order)
                shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)

def test_gracefully_degrade(n_seeds, cluster_model_sizes, learning_rates, n_epochs, n_out):
    global VALIDATE
    VALIDATE = False
    global LEARNING_RATE
    global NAME

    shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)

    for seed in range(n_seeds):
        np.random.seed(seed)
        clusters = np.zeros(16, dtype=int)
        clusters[np.random.choice(len(clusters), n_out, replace=False)] = -1
        for lr in learning_rates:
            LEARNING_RATE = lr
            for csize in cluster_model_sizes:
                network = GDNetwork(paths_to_data, thresholding_top_confidence, trained_models, csize)
                NAME = f"gracefully_degrade_{seed}_complexity_{csize}_lr_{lr}_n_ep_{n_epochs}_n_out_{n_out}"
                network.run_experiment(clusters, n_epochs)
                shutil.rmtree(os.path.join(PATH, 'runs/detect'), ignore_errors=True)

#test_gracefully_degrade(1, ['n','m','x'], [0.01,0.001], 100, 8)

test_agent_inclusion([0], [3,2,4], [3,8], ['n']) # ideal: [1,2,3], [3,8,15], ['n','m','x']
