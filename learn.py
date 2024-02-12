from PIL import Image
import ultralytics as ul
import numpy as np
import os
import shutil
import torch
import seaborn as sns
import matplotlib.pyplot as plt

PATH = "C:/Users/tbary/Documents/UCL/Doctorat/Year 1/ALA-EMAS 2024"

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

class Agent():
    def __init__(self, id, model, stream, buffer_size, buffer_policy, clock):
        self._ID = id
        self.model = model
        self.stream = stream
        self.clock = clock
        
        self.buffer = np.empty(buffer_size, dtype='object')
        self.buf_ptr = 0
        self.add_to_buffer = buffer_policy

    def annotate_batch(self, size=1, ext_observations=None):
        observations = list(ext_observations) if ext_observations is not None else list(self.stream[self.clock.time:self.clock.time+size])
        predictions = self.model.predict(observations, verbose=False)
        if ext_observations is None:
            for prediction in predictions:
                self.add_to_buffer(self, prediction)
        return predictions

    def __repr__(self):
        return("Agent #{}".format(self._ID))

class Coalition():
    def __init__(self, coal_model, agents_list, buffer_size, buffer_policy, clock):
        self.size = len(agents_list)
        self.agents_list = agents_list
        self.coal_model = coal_model

        self.combined_stream = np.array([agent.stream for agent in agents_list])

        self.clock = clock
        
        self.buffer = np.empty(buffer_size, dtype='object')
        self.buf_ptr = 0
        self.add_to_buffer = buffer_policy
    
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
        self.agents_list.append(agent)
        self.combined_stream = np.vstack((self.combined_stream, np.expand_dims(agent.stream, axis=0)))

class Network():
    def __init__(self,start_time, database, imgs_per_stream, n_coalitions, n_agents, n_agents_in_coals, coal_buffer_size, agent_buffer_size, buffer_policy):
        self.clock=Clock()
        self.clock.time=start_time

        self.all_agents = np.empty(n_agents, dtype=Agent)
        self.all_coalitions = np.empty(n_coalitions, dtype=Coalition)

        for ag in range(n_agents):
            model = ul.YOLO('yolov8n.pt')
            stream = get_n_to_m_jpg_filenames(database, ag*imgs_per_stream, (ag+1)*imgs_per_stream)
            self.all_agents[ag]=Agent(ag, model, stream, agent_buffer_size, buffer_policy, self.clock)

        for coal in range(n_coalitions):
            agents_list = [self.all_agents[coal*n_coalitions+ag] for ag in range(n_agents_in_coals)]
            model = ul.YOLO('yolov8m.pt')
            self.all_coalitions[coal]=Coalition(model, agents_list, coal_buffer_size, buffer_policy, self.clock)
            print("coal {}:".format(coal), agents_list)

        self.free_agents = [self.all_agents[struct*n_coalitions+ag] for struct in range(n_coalitions) for ag in range(n_agents_in_coals, n_agents//n_coalitions)]
        print("free: ", self.free_agents)

    def add_agent(self, agent, id):
        agent_challenges = extract_buffer_imgs(agent, os.path.join(PATH,'tmp/agent'))
        agent_chal_mAP = np.zeros(len(self.all_coalitions))
        coal_chal_mAP = np.zeros(len(self.all_coalitions))
        for coal, coalition in enumerate(self.all_coalitions):
            coal_challenges = extract_buffer_imgs(coalition, os.path.join(PATH,'tmp/coal'))

            coal_chal_mAP[coal] = agent.model.val(data=os.path.join(PATH,'tmp/coal/TMP_YAML.yaml'), verbose=False).box.map
            agent_chal_mAP[coal] = coalition.coal_model.val(data=os.path.join(PATH,'tmp/agent/TMP_YAML.yaml'), verbose=False).box.map

            delete_files_in_directory(os.path.join(PATH,'tmp/coal'))
        delete_files_in_directory(os.path.join(PATH,'tmp/agent'))
       

        proximity_score = np.sqrt(agent_chal_mAP*coal_chal_mAP)
        #proximity_score = 2/(1/agent_chal_mAP+1/coal_chal_mAP)
        #proximity_score = 1-(agent_chal_mAP+ coal_chal_mAP)/2

        segregation = np.sort(proximity_score)[-1]-np.sort(proximity_score)[-2]
        print(segregation)

        self.all_coalitions[np.argmax(proximity_score)].add_agent(agent)

        sns.heatmap(np.array([agent_chal_mAP, coal_chal_mAP, proximity_score]), annot=True, cmap="coolwarm")
        plt.savefig('test_{}.jpg'.format(id))
        plt.clf()

    def main(self, warmup_length):
        for agent in self.free_agents:
            agent.annotate_batch(size=warmup_length)
        for coal in self.all_coalitions:
            coal.annotate_batch(size=warmup_length)
        self.clock.time+=warmup_length
        for ag, agent in enumerate(self.free_agents):
            self.add_agent(agent, ag)
        for coal in self.all_coalitions:
            print(coal.agents_list)

def get_n_to_m_jpg_filenames(folder_path, n, m):
    jpg_files = [f"{folder_path}/{file}" for file in os.listdir(folder_path) if file.endswith('.jpg')]
    return jpg_files[n:m]

delete_files_in_directory(os.path.join(PATH,'tmp/agent'))
delete_files_in_directory(os.path.join(PATH,'tmp/coal'))
test = Network(10, 'val2017', 20, 2, 6, 2, 32, 32, buffer_most_confident)
test.main(5)