import os
import numpy as np
import matplotlib.pyplot as plt
import re

PATH = "/home/dani/aptitude/extension"

def load_arrays_from_file(file_path: str):
    perf, pres  = np.loadtxt(file_path)
    return perf, pres

def get_n_coals_n_test(pattern, file_list, n_coals=True):
    highest_coal = -1
    highest_test = -1

    # Iterate over the list of strings
    for file in file_list:
        match = pattern.match(file)
        if match:
            # Extract the numerical part and convert it to an integer
            if n_coals:
                coal_nb = int(match.group(2))
                if coal_nb > highest_coal:
                    highest_coal = coal_nb
            test_nb = int(match.group(1))
            if test_nb > highest_test:
                highest_test = test_nb
    if n_coals:
        return highest_coal + 1, highest_test + 1
    return 1, highest_test +1

def decode_files(directory: str, prefix: str, n_agents):
    files = os.listdir(directory)
    pattern = re.compile(fr"{prefix}_eval_(\d+)_coal_(\d+)\.txt")

    all_mAPs = np.empty((*get_n_coals_n_test(pattern, files), n_agents))
    presence_matrix = np.empty((*get_n_coals_n_test(pattern, files), n_agents), dtype=int)

    for file in files:
        match = pattern.match(file)
        if match:
            coal_id = int(match.group(2))
            eval_num = int(match.group(1))
            perf, pres = load_arrays_from_file(os.path.join(directory,file))
            all_mAPs[coal_id, eval_num] = perf
            presence_matrix[coal_id, eval_num] = pres
    
    return all_mAPs, presence_matrix

def decode_files_net(directory: str, prefix: str, n_agents):
    files = os.listdir(directory)
    pattern = re.compile(fr"{prefix}_eval_(\d+)_net\.txt")

    all_mAPs = np.empty((*get_n_coals_n_test(pattern, files, n_coals=False), n_agents))
    print(all_mAPs.shape)
    presence_matrix = np.empty((*get_n_coals_n_test(pattern, files, n_coals=False), n_agents), dtype=int)

    for file in files:
        match = pattern.match(file)
        if match:
            eval_num = int(match.group(1))
            perf, pres = load_arrays_from_file(os.path.join(directory,file))
            all_mAPs[0, eval_num] = perf
            presence_matrix[0, eval_num] = pres
    
    return all_mAPs, presence_matrix
            

def plot_within_cluster(all_mAPs, presence_matrix, ax):
    x_array = np.arange(all_mAPs.shape[1])+1
    id = 0
    for cluster_mAPs, cluster_presence_matrix in zip(all_mAPs, presence_matrix):
        avg_in_cluster_maps = np.array([cluster_mAPs[i, cluster_presence_matrix[i] == 1].mean() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        std_in_cluster_maps = np.array([cluster_mAPs[i, cluster_presence_matrix[i] == 1].std() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        ax.plot(x_array, avg_in_cluster_maps, color = f'C{id}', linestyle='solid', label = f'Coalition {id}')
        id+=1

def plot_encountered(all_mAPs, presence_matrix, ax):
    x_array = np.arange(all_mAPs.shape[1])+1
    id = 0
    for cluster_mAPs, cluster_presence_matrix in zip(all_mAPs, presence_matrix):
        avg_encountered_maps = np.array([cluster_mAPs[i, cluster_presence_matrix[i] != 0].mean() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        std_encountered_maps = np.array([cluster_mAPs[i, cluster_presence_matrix[i] != 0].std() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        ax.plot(x_array, avg_encountered_maps, color = f'C{id}', linestyle='dashed')
        id+=1

def plot_all_agents(all_mAPs, presence_matrix, ax):
    x_array = np.arange(all_mAPs.shape[1])+1
    id = 0
    for cluster_mAPs, cluster_presence_matrix in zip(all_mAPs, presence_matrix):
        avg_encountered_maps = np.mean(cluster_mAPs, axis=1)
        std_encountered_maps = np.std(cluster_mAPs, axis=1)
        ax.plot(x_array, avg_encountered_maps, color = f'C{id}', linestyle='dotted')
        id+=1

def plot_memory(all_mAPs, presence_matrix, ax):
    x_array = np.arange(all_mAPs.shape[1])+1
    id = 0
    for cluster_mAPs, cluster_presence_matrix in zip(all_mAPs, presence_matrix):
        avg_memory = np.array([cluster_mAPs[i, cluster_presence_matrix[i] == 2].mean() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        std_memory = np.array([cluster_mAPs[i, cluster_presence_matrix[i] == 2].std() if np.any(cluster_presence_matrix[i] == 1) else np.nan for i in range(cluster_mAPs.shape[0])])
        ax.plot(x_array, avg_memory, color = f'C{id}', linestyle='dashdot')
        id+=1

all_mAPs, presence_matrix = decode_files(os.path.join(PATH,"results"), "ar_removal_ 0 1 2 3 4 5 6 7 8 9101112_mAPs", 16)

fig, ax = plt.subplots(figsize=(10,10))

plot_within_cluster(all_mAPs, presence_matrix, ax)
plot_encountered(all_mAPs, presence_matrix, ax)
plot_all_agents(all_mAPs, presence_matrix, ax)
plot_memory(all_mAPs, presence_matrix, ax)

ax.set_xlabel('Event number')
ax.set_xticks(range(1, all_mAPs.shape[1]+1))
ax.set_ylabel('$mAP_{50-95}$')
ax.legend()
plt.show()

all_mAPs, presence_matrix = decode_files_net(os.path.join(PATH,"results"), "ar_removal_ 0 1 2 3 4 5 6 7 8 9101112_mAPs", 16)
fig, ax = plt.subplots(figsize=(10,10))

plot_within_cluster(all_mAPs, presence_matrix, ax)
plot_encountered(all_mAPs, presence_matrix, ax)
plot_all_agents(all_mAPs, presence_matrix, ax)
plot_memory(all_mAPs, presence_matrix, ax)

ax.set_xlabel('Event number')
ax.set_xticks(range(1, all_mAPs.shape[1]+1))
ax.set_ylabel('$mAP_{50-95}$')
ax.legend()
plt.show()