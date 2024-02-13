from PIL import Image
import shutil
import numpy as np
import os
import torch
import seaborn as sns
import matplotlib.pyplot as plt
from multiprocessing import Pool

#sys.path.append(os.path.join(sys.path[0], "yolov8", "ultralytics"))
from ultralytics import YOLO

PATH = "/home/dani/aptitude/extension"
DEFAULT_SUB_SAMPLE = 300

class SamplingException(Exception):
    pass

class Clock():
    def __init__(self):
        self._time = 0

    @property
    def time(self):
        return self._time

    @time.setter
    def time(self, value):
        self._time = value

    def inc_time(self):
        self._time += 1

def get_conf(results_array):
    return np.array([torch.max(item.boxes.conf).item() for item in results_array])

def build_yaml_file(my_path, base_file, dataset):
    lines_to_write = []
    with open(base_file, 'r') as f:
        lines = f.readlines()
        for line in lines:
            if 'path:' in line:
                lines_to_write += [f'path : {dataset}\n']
            else:
                lines_to_write += [line]

    with open(f'{my_path}/TMP_YAML.yaml', 'w') as f:
        f.writelines(lines_to_write)

def extract_buffer_imgs(holon, my_path):
    os.makedirs(my_path, exist_ok=True)
    os.makedirs(os.path.join(my_path,'val'), exist_ok=True)
    os.makedirs(os.path.join(my_path,'val', 'images'), exist_ok=True)
    os.makedirs(os.path.join(my_path,'val', 'labels'), exist_ok=True)
    results = holon.buffer[:holon.buf_ptr]
    for r, res in enumerate(results):
        Image.fromarray(res.orig_img).save(f'{my_path}/val/images/{r}.jpg')
        res.save_txt(f'{my_path}/val/labels/{r}.txt')
    build_yaml_file(my_path, 'templates/base.yaml', my_path)

def naive_buffer_n_first(holon, prediction):
    if holon.buf_ptr < len(holon.buffer):
        holon.buffer[holon.buf_ptr]=prediction
        holon.buf_ptr+=1

def buffer_most_confident(holon, prediction):
    if len(prediction.boxes)==0:
        return

    if holon.buf_ptr < len(holon.buffer):
        holon.buffer[holon.buf_ptr]=prediction
        holon.buf_ptr+=1    
    else:
        confidences = get_conf(holon.buffer)
        #print(prediction.boxes.conf)
        obs_conf = torch.max(prediction.boxes.conf).item()
        if np.min(confidences) <= obs_conf:
            pos = np.argmin(confidences)
            holon.buffer[pos]=prediction


def thresholding_top_confidence(
    image_labels_path: str,
    n: int = DEFAULT_SUB_SAMPLE,
    aggregation_function: str = "max",
    warmup_length=720,
    sampling_rate=0.10,
    **kwargs,
) -> list:
    """
    Performs active learning for object detection using the confidence scores.

    Parameters:
    - image_labels_path: paths to the .txt files with the object detections (last element of each line = confidence score).
    - n: number of images to label.
    - aggregation_function: how to compute the confidence of an image based on the confidence of the single objects:
        a) "max": minmax approach, where the confidence of an image is given by the most confidently detected object.
        b) "min": confidence of the whole image is given by the most difficult object detected.
        c) "mean": average of all the confidence scores, it is not sensible to the number of objects detected.
        d) "sum": sensible to the number of objects detected.

    Returns:
    - images_to_label: list of strings, paths to the .txt files with the images to be labeled
    """
    txt_files = [filename for filename in os.listdir(image_labels_path)]
    if n <= 0:
        raise SamplingException(
            f"You must select a strictly positive number of frames to select"
        )
    if n > len(txt_files):
        raise SamplingException(
            f"Image bank contains {len(txt_files)} frames, but {n} frames where required for the "
            f"least confidence strategy !"
        )
    confidences = []
    for txt_file in txt_files:
        with open(os.path.join(image_labels_path, txt_file), "r") as f:
            lines = f.readlines()
            if lines:
                # If the file is not empty, compute the image confidence score
                if aggregation_function == "max":
                    image_confidence = max(
                        [float(line.strip().split()[5]) for line in lines]
                    )
                elif aggregation_function == "min":
                    image_confidence = min(
                        [float(line.strip().split()[5]) for line in lines]
                    )
                elif aggregation_function == "mean":
                    object_confidences_scores = [
                        float(line.strip().split()[5]) for line in lines
                    ]
                    image_confidence = sum(object_confidences_scores) / len(
                        object_confidences_scores
                    )
                elif aggregation_function == "sum":
                    object_confidences_scores = [
                        float(line.strip().split()[5]) for line in lines
                    ]
                    image_confidence = sum(object_confidences_scores)
                else:
                    raise SamplingException(
                        f"You must select a valid aggregation function"
                    )
                confidences.append((txt_file, image_confidence))

    # Get the warm-up set
    warmup_set = confidences[:warmup_length]

    # Compute the threshold
    threshold = np.percentile(
        [conf for _, conf in warmup_set], 100 * (1 - sampling_rate)
    )

    # Filtering images based on the confidence scores
    top_confidence_images = [
        (img, conf) for img, conf in confidences[warmup_length:] if conf > threshold
    ]

    # Get N-first images with a confidence lower than the threshold
    images_to_label = [os.path.splitext(img)[0] for img, _ in top_confidence_images[:n]]

    return images_to_label

def buffer_least_confident(holon, prediction):
    if len(prediction.boxes)==0:
        return
    if holon.buf_ptr < len(holon.buffer):
        holon.buffer[holon.buf_ptr]=prediction
        holon.buf_ptr+=1    
    else:
        confidences = get_conf(holon.buffer)
        obs_conf = torch.max(prediction.boxes.conf).item()
        if np.max(confidences) >= obs_conf:
            pos = np.argmax(confidences)
            holon.buffer[pos]=prediction

def delete_files_in_directory(directory):
    """
    Recursively deletes all files in a directory and its subdirectories.
    Does not delete the subdirectories themselves.
    
    Args:
    - directory (str): The path to the directory to delete files from.
    """
    # Iterate over all items in the directory
    for item in os.listdir(directory):
        # Get the full path of the item
        item_path = os.path.join(directory, item)
        
        # Check if the item is a file
        if os.path.isfile(item_path):
            # Delete the file
            os.remove(item_path)
        # Check if the item is a directory
        elif os.path.isdir(item_path):
            # Recursively call the function on the subdirectory
            delete_files_in_directory(item_path)

def copy_file(args):
    src, dst = args
    shutil.copy(src, dst)

def parallel_copy(index, in_folder, out_folder, imgExtension, labelsFolder):
    """
    :param index: an array of the name of the images that are selected ('e.g. ['frame_0001','frame_0020'])
    :param in_folder: path to the directory of the source folder containing images and labels subfolders (e.g., "C:/banks")
    :param out_folder: path to the dest (e.g., "C:/train")

    Create a new directory that copies all the images and the labels following the index in a new folder
    """
    images = os.listdir(os.path.join(in_folder, "images")) # Source of the bank images
    labels = os.listdir(os.path.join(in_folder, labelsFolder)) # Source of the bank of labels
    camwithjpg = ["cam1","cam2","cam3","cam4","cam5","cam6","cam7","cam8","cam9"]
    camwithpng= ["cam16","cam17","cam18","cam19","cam20","cam22","cam24"]

    
    print(f"Copying {len(index)} images from", os.path.join(in_folder, "images"), f"containing {len(images)} images and from labels folder ", os.path.join(in_folder, labelsFolder),f"contaning {len(labels)} labels")
    
    os.makedirs(os.path.join(out_folder, "images"), exist_ok=True) # Create image directory in out_folder if it doesn't exist in out_folder
    os.makedirs(os.path.join(out_folder, "labels"), exist_ok=True) # Create labels directory in out_folder if it doesn't exist in out_folder

    copy_args = []
    for img in index:
        for cam in camwithjpg:
            if cam in in_folder:
                imgExtension="jpg"
        for cam in camwithpng:
            if cam in in_folder:
                imgExtension="png"
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

    with Pool() as pool:
        pool.map(copy_file, copy_args)

def list_files_without_extensions(path: str, extension: str = "png") -> list:
    """
    :param path: path to scan for files
    :param extensions: what type of files to scan for
    :return path_list: list of file names without the extensions
    """
    path_list = [
        os.path.splitext(filename)[0]
        for filename in os.listdir(path)
        if filename.endswith(extension)
    ]
    return path_list

class Agent():
    def __init__(self, id, model, stream, buffer_size, buffer_policy):
        self._ID = id
        self.model = model
        self.stream = stream

        self.buffer = buffer_policy(os.path.join(stream,'labels_yolov8n_w_conf'), n=buffer_size)

    def train(self):
        os.makedirs('tmp', exist_ok=True)
        os.makedirs('tmp/agent', exist_ok=True)
        os.makedirs('tmp/agent/train', exist_ok=True)
        os.makedirs('tmp/agent/val', exist_ok=True)
        parallel_copy(self.buffer, self.stream, os.path.join('tmp','agent','train'),'.jpg','labels_yolov8x6')
        parallel_copy(list_files_without_extensions(os.path.join(self.stream,'test','labels'), extension='txt'), os.path.join(self.stream,'test'), os.path.join('tmp','agent','val'),'.jpg','labels')
        build_yaml_file(os.path.join(PATH,'tmp','agent'),os.path.join('templates','base.yaml'),os.path.join(PATH,'tmp','agent'))
        self.model.train(data="tmp/agent/TMP_YAML.yaml",
                            epochs=100,
                            batch=16,
                            device="cuda:0",
                            patience=1000)
        delete_files_in_directory(os.path.join(PATH,'tmp/agent'))

    def annotate_batch(self, size=1, ext_observations=None):
        observations = list(ext_observations) if ext_observations is not None else list(self.stream[self.clock.time:self.clock.time+size])
        predictions = self.model.predict(observations, verbose=False)
        if ext_observations is None:
            for prediction in predictions:
                self.add_to_buffer(self, prediction)
        return predictions

    def __repr__(self):
        return("Agent #{}".format(self._ID))



class Coalition(): #TODO: code remove and retrain !!!
    def __init__(self, coal_model, agents_list):
        self.size = len(agents_list)
        self.agents_list = agents_list
        self.coal_model = coal_model

        self.combined_stream = [agent.stream for agent in agents_list]
        
        self.combined_buffers = [agent.buffer for agent in agents_list]

    def train(self):
        os.makedirs('tmp', exist_ok=True)
        os.makedirs('tmp/coal', exist_ok=True)
        os.makedirs('tmp/coal/train', exist_ok=True)
        os.makedirs('tmp/coal/val', exist_ok=True)
        for buffer,stream in zip(self.combined_buffers, self.combined_stream):
            parallel_copy(buffer, stream, os.path.join('tmp','coal','train'),'.jpg','labels_yolov8x6')
            parallel_copy(list_files_without_extensions(os.path.join(stream,'test','labels'), extension='txt'), os.path.join(stream,'test'), os.path.join('tmp','coal','val'),'.jpg','labels')
        build_yaml_file(os.path.join(PATH,'tmp','coal'),os.path.join('templates','base.yaml'),os.path.join(PATH,'tmp','coal'))
        self.coal_model.train(data="tmp/coal/TMP_YAML.yaml",
                                epochs=20,
                                batch=16,
                                device="cuda:0",
                                patience=1000)
        delete_files_in_directory(os.path.join(PATH,'tmp/coal'))
    
    def annotate_batch(self, size=1, ext_observations=None, query_coal_model=False):
        if ext_observations is not None:
            return self.coal_model.predict(list(ext_observations), verbose=False)

        if query_coal_model:
            observations = list(self.combined_stream[..., self.clock.time:self.clock.time+size].flatten())
            predictions = self.coal_model.predict(observations, verbose=False)
        else:
            predictions = np.array([agent.annotate_batch(size=size) for agent in self.agents_list], dtype=object).flatten()

        for prediction in predictions:
            self.add_to_buffer(self, prediction)

        return predictions

    def add_agent(self, agent):
        self.size += 1
        self.agents_list= np.append(self.agents_list,agent)
        self.combined_stream.append(agent.stream)


class Network():
    def __init__(self, paths_to_data, agent_buffer_size, buffer_policy, trained_models = None):
        n_agents = len(paths_to_data)
        self.all_agents = np.empty(n_agents, dtype=Agent)
        for i in range(n_agents):
            model = YOLO('ultralytics/yolov8n.pt') if trained_models is None else YOLO(trained_models[i])
            agent = Agent(i, model, paths_to_data[i], agent_buffer_size, buffer_policy)
            if trained_models is None:
                agent.train()
            self.all_agents[i] = agent

    def clusterize(self, clusters, trained_models=None):
        n_coals = np.max(clusters)+1
        self.all_coalitions = np.empty(n_coals, dtype=Coalition)
        self.free_agents = self.all_agents[clusters==-1]
        for coal in range(n_coals):
            agents_list = self.all_agents[clusters==coal]
            model = YOLO('ultralytics/yolov8m.pt') if trained_models is None else YOLO(trained_models[coal])
            coalition = Coalition(model, agents_list)
            if trained_models is None:
                coalition.train()
            self.all_coalitions[coal]=coalition
            print("coal {}:".format(coal), agents_list)            
        print("free: ", self.free_agents)

    def add_agent(self, agent, id):
        os.makedirs('tmp', exist_ok=True)
        os.makedirs('tmp/coal', exist_ok=True)
        os.makedirs('tmp/coal/val', exist_ok=True)
        os.makedirs('tmp/agent', exist_ok=True)
        os.makedirs('tmp/agent/val', exist_ok=True)
        parallel_copy(agent.buffer, agent.stream, os.path.join('tmp','agent','val'),'.jpg','labels_yolov8x6')
        build_yaml_file(os.path.join(PATH,'tmp','agent'), 'templates/base.yaml', os.path.join(PATH,'tmp','agent'))
        agent_chal_mAP = np.zeros(len(self.all_coalitions))
        coal_chal_mAP = np.zeros(len(self.all_coalitions))
        for coal, coalition in enumerate(self.all_coalitions):
            for buffer, stream in zip(coalition.combined_buffers, coalition.combined_stream):
                parallel_copy(buffer, stream, os.path.join('tmp','coal','val'),'.jpg','labels_yolov8x6')
            build_yaml_file(os.path.join(PATH,'tmp','coal'), 'templates/base.yaml', os.path.join(PATH,'tmp','coal'))
            coal_chal_mAP[coal] = agent.model.val(data=os.path.join(PATH,'tmp/coal/TMP_YAML.yaml'), verbose=False).box.map
            agent_chal_mAP[coal] = coalition.coal_model.val(data=os.path.join(PATH,'tmp/agent/TMP_YAML.yaml'), verbose=False).box.map
        

            delete_files_in_directory(os.path.join(PATH,'tmp/coal'))
        delete_files_in_directory(os.path.join(PATH,'tmp/agent'))
       

        proximity_score = np.sqrt(agent_chal_mAP*coal_chal_mAP)
        #proximity_score = 2/(1/agent_chal_mAP+1/coal_chal_mAP)
        #proximity_score = 1-(agent_chal_mAP+ coal_chal_mAP)/2

        #segregation = np.sort(proximity_score)[-1]-np.sort(proximity_score)[-2]
        #print(segregation)

        self.all_coalitions[np.argmax(proximity_score)].add_agent(agent)

        sns.heatmap(np.array([agent_chal_mAP, coal_chal_mAP, proximity_score]), annot=True, cmap="coolwarm")
        plt.savefig('test_{}.jpg'.format(id))
        plt.clf()

    def main(self):
        for ag, agent in enumerate(self.free_agents):
            self.add_agent(agent, ag)
        for coal in self.all_coalitions:
            print(coal.agents_list)

def get_n_to_m_jpg_filenames(folder_path, n, m):
    jpg_files = [f"{folder_path}/{file}" for file in os.listdir(folder_path) if file.endswith('.jpg')]
    return jpg_files[n:m]

paths_to_data = ['data/cam1/week1/bank',
                 'data/cam2/week1/bank',
                 'data/cam3/week5/bank',
                 'data/cam9/week1/bank']

trained_models= ['cam1.pt','cam2.pt','cam3.pt','cam9.pt']

test = Network(paths_to_data,128, thresholding_top_confidence, trained_models = trained_models)
test.clusterize(np.array([0,0,-1,-1]), trained_models=["runs/detect/train25/weights/last.pt"])
test.main()
