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

    all_mAPs = np.full((*get_n_coals_n_test(pattern, files), n_agents), np.nan)
    presence_matrix = np.full((*get_n_coals_n_test(pattern, files), n_agents), np.nan)
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

    all_mAPs = np.full((*get_n_coals_n_test(pattern, files, n_coals=False), n_agents), np.nan)
    presence_matrix = np.full((*get_n_coals_n_test(pattern, files, n_coals=False), n_agents), np.nan)
    for file in files:
        match = pattern.match(file)
        if match:
            eval_num = int(match.group(1))
            perf, pres = load_arrays_from_file(os.path.join(directory,file))
            all_mAPs[0, eval_num] = perf
            presence_matrix[0, eval_num] = pres
    
    return all_mAPs, presence_matrix
            
def plot_detailed_coal(all_mAPs, presence_matrix, coal_idx, ax=None, ref_mAPs = None):
    format_id = 0
    agent_mAPs = all_mAPs[coal_idx,...].T
    if ref_mAPs is not None:
        agent_mAPs -= np.tile(ref_mAPs, (agent_mAPs.shape[1],1)).T
    agent_mAPs = agent_mAPs[...,~np.isnan(agent_mAPs).any(axis=0)]

    events = presence_matrix[coal_idx,...].T

    
    events = events[...,~np.isnan(events).any(axis=0)]
    agent_mAPs = agent_mAPs[np.any(events!=0, axis=1)]
    agent_id = np.arange(all_mAPs.shape[2])[np.any(events!=0, axis=1)]
    events = events[np.any(events!=0, axis=1)]
    
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    x_values = np.arange(agent_mAPs.shape[1])
    inside_mean = np.zeros(agent_mAPs.shape[1])
    outside_mean = np.zeros(agent_mAPs.shape[1])
    for agent_mAP, agent_events in zip(agent_mAPs, events):
        outside_agents = agent_events!=1
        agent_id[format_id]
        #ax.plot(x_values[outside_agents], agent_mAP[outside_agents], marker='.', color = f"C{format_id}", label = f"Cam #{agent_to_cam[agent_id[format_id]]}")
        outside_mean[outside_agents] += agent_mAP[outside_agents]
        inside_agents = agent_events==1
        #ax.plot(x_values[inside_agents], agent_mAP[inside_agents], linestyle='dashed', marker='.', color = f"C{format_id}")
        inside_mean[inside_agents] += agent_mAP[inside_agents]
        format_id+=1
    #outside_mean/=np.sum(events==0, axis=0)
    #inside_mean/=np.sum(events==1, axis=0)
    ax.plot(x_values, np.mean(agent_mAPs, axis=0), color='k', linestyle='solid', label='Avg. performances of network')
    #ax.plot(x_values, inside_mean, color='k', linestyle='dashed', label='Avg. cams inside cluster')
    ax.set_xticks(range(1, agent_mAPs.shape[1]))
    return ax

"""def plot_detailed_coal(all_mAPs, presence_matrix, coal_idx, ax=None, ref_mAPs = None):
    format_id = 0
    agent_mAPs = all_mAPs[coal_idx,...].T
    if ref_mAPs is not None:
        agent_mAPs -= np.tile(ref_mAPs, (agent_mAPs.shape[1],1)).T
    agent_mAPs = agent_mAPs[...,~np.isnan(agent_mAPs).any(axis=0)]

    events = presence_matrix[coal_idx,...].T
    agent_id = np.arange(agent_mAPs.shape[0])[np.any(events==1, axis=1)]
    oustide_mAPs = agent_mAPs[np.any(events==0, axis=1)]
    agent_mAPs = agent_mAPs[np.any(events==1, axis=1)]
    events = events[np.any(events==1, axis=1)]
    events = events[...,~np.isnan(events).any(axis=0)]
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    x_values = np.arange(agent_mAPs.shape[1])
    inside_mean = np.zeros(agent_mAPs.shape[1])
    outside_mean = np.zeros(agent_mAPs.shape[1])

    for agent_mAP, agent_events in zip(agent_mAPs, events):
        outside_agents = agent_events==0
        if len(outside_agents)>=1:
            ax.errorbar(0, np.mean(agent_mAP[outside_agents]), yerr=np.std(agent_mAP[outside_agents]), fmt='.', capsize=5, color = f"C{format_id}")

        #ax.plot(x_values[outside_agents], , marker='.', color = f"C{format_id}", label = f"Cam #{agent_to_cam[agent_id[format_id]]}")
        outside_mean[outside_agents] += agent_mAP[outside_agents]
        inside_agents = agent_events==1
        ax.plot(x_values[inside_agents], agent_mAP[inside_agents], linestyle='dashed', marker='.', color = f"C{format_id}",label = f"Cam #{agent_to_cam[agent_id[format_id]]}")
        inside_mean[inside_agents] += agent_mAP[inside_agents]
        format_id+=1
    #outside_mean/=np.sum(events==0, axis=0)
    #inside_mean/=np.sum(events==1, axis=0)
    ax.plot(x_values, np.mean(agent_mAPs, axis=0), color='k', linestyle='solid', label='Avg. performances in cluster')
    ax.plot(x_values, np.mean(oustide_mAPs, axis=0), color='k', linestyle='dashed', label='Avg. performances out cluster')
    #ax.plot(x_values, inside_mean, color='k', linestyle='dashed', label='Avg. cams inside cluster')
    ax.set_xticks(range(1, agent_mAPs.shape[1]))
    return ax"""

def get_agent_coal(presence_matrix):
    coal_status = np.empty(presence_matrix.shape[2], dtype=int)
    for c, coal in enumerate(presence_matrix):
        for a, agent in enumerate(coal.T):
            if np.any(agent==1):
                coal_status[a]=c
    return coal_status

def plot_cross_perf(all_mAPs, coal_status, ax=None, ref_mAPs= None):
    linestyles = ['solid', 'dashed', 'dotted', 'dashdot']
    n_clusters = int(np.max(coal_status))
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    for format_id, cluster_mAPs in enumerate(np.transpose(all_mAPs, (0, 2, 1))):
        if ref_mAPs is not None:
            cluster_mAPs -= np.tile(ref_mAPs, (cluster_mAPs.shape[1],1)).T
        line_id = 0
        for cluster in range(n_clusters+1):
            mAPs_means = np.nanmean(cluster_mAPs[coal_status==cluster],axis=0)
            
            ax.plot(mAPs_means[~np.isnan(mAPs_means)], color=f"C{format_id}", linestyle=linestyles[line_id], label = f"Cluster #{format_id+1} on cluster #{line_id+1}")
            line_id+=1
    return ax

def plot_cross_perf_net(all_mAPs, coal_status, ax=None, ref_mAPs= None):
    linestyles = ['solid', 'dashed', 'dotted', 'dashdot']
    n_clusters = int(np.max(coal_status))
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    cluster_mAPs = all_mAPs[0].T
    if ref_mAPs is not None:
        cluster_mAPs -= np.tile(ref_mAPs, (cluster_mAPs.shape[1],1)).T
    line_id = 0
    for cluster in range(n_clusters+1):
        ax.plot(np.nanmean(cluster_mAPs[coal_status==cluster],axis=0), color="k", linestyle=linestyles[line_id], label = f"Network on cluster #{line_id+1}")
        line_id+=1
    return ax

"""def plot_detailed_coal(all_mAPs, presence_matrix, coal_idx, ax=None, ref_mAPs = None):
    format_id = 0

    agent_mAPs = all_mAPs[coal_idx,...].T
    if ref_mAPs is not None:
        agent_mAPs -= np.tile(ref_mAPs, (agent_mAPs.shape[1],1)).T
    agent_mAPs = agent_mAPs[...,~np.isnan(agent_mAPs).any(axis=0)]

    events = presence_matrix[coal_idx,...].T
    oustide_mAPs = agent_mAPs[np.any(events==0, axis=1)]

    agent_mAPs = agent_mAPs[np.any(events==1, axis=1)]
    

    events = events[np.any(events==1, axis=1)]
    events = events[...,~np.isnan(events).any(axis=0)]

    in_out_maps = np.array([np.mean(agent_mAPs[...,i][events[...,i]==0]) for i in range(agent_mAPs.shape[1])])
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    x_values = np.arange(agent_mAPs.shape[1])



    ax.plot(x_values, np.mean(agent_mAPs, axis=0), color='k', linestyle='solid', label='Avg. performances in cluster')
    #ax.errorbar(x_values, np.mean(agent_mAPs, axis=0), yerr=np.std(agent_mAPs, axis=0), fmt='.', capsize=5, color = "k")
    ax.plot(x_values, np.mean(oustide_mAPs, axis=0), color='k', linestyle='dashed', label='Avg. performances out cluster')
    #ax.errorbar(x_values, np.mean(oustide_mAPs, axis=0), yerr=np.std(oustide_mAPs, axis=0), fmt='.', capsize=5, color = "k")
    ax.plot(x_values, in_out_maps, color='k', linestyle='dotted', label='Avg. performances before cluster')
    ax.set_xticks(range(1, agent_mAPs.shape[1]))
    return ax
"""
def plot_detailed_net(all_mAPs, presence_matrix, ax=None, ref_mAPs=None):
    format_id = 0
    agent_mAPs = all_mAPs[0,...].T
    if ref_mAPs is not None:
        agent_mAPs -= np.tile(ref_mAPs, (agent_mAPs.shape[1],1)).T
    agent_mAPs = agent_mAPs[...,~np.isnan(agent_mAPs).any(axis=0)]

    events = presence_matrix[0,...].T

    agent_mAPs = agent_mAPs[np.any(events!=0, axis=1)]
    events = events[...,~np.isnan(events).any(axis=0)]
    agent_id = np.arange(all_mAPs.shape[2])[np.any(events!=0, axis=1)]
    events = events[np.any(events!=0, axis=1)]
    
    if ax is None:
        _, ax = plt.subplots(figsize=(10,10))
    x_values = np.arange(agent_mAPs.shape[1])
    inside_mean = np.zeros(agent_mAPs.shape[1])
    outside_mean = np.zeros(agent_mAPs.shape[1])

    for agent_mAP, agent_events in zip(agent_mAPs, events):
        outside_agents = agent_events!=1
        agent_id[format_id]
        ax.plot(x_values[outside_agents], agent_mAP[outside_agents], marker='.', color = f"C{format_id}", label = f"Cam #{agent_to_cam[agent_id[format_id]]}")
        outside_mean[outside_agents] += agent_mAP[outside_agents]
        inside_agents = agent_events==1
        ax.plot(x_values[inside_agents], agent_mAP[inside_agents], linestyle='dashed', marker='.', color = f"C{format_id}")
        inside_mean[inside_agents] += agent_mAP[inside_agents]
        format_id+=1
    #outside_mean/=np.sum(events==0, axis=0)
    #inside_mean/=np.sum(events==1, axis=0)
    ax.plot(x_values, np.mean(agent_mAPs, axis=0), color='k', linestyle='solid', label='Avg. performances of network')
    #ax.plot(x_values, inside_mean, color='k', linestyle='dashed', label='Avg. cams inside cluster')
    ax.set_xticks(range(1, agent_mAPs.shape[1]))
    return ax

agent_to_cam = {0:1, 1:2, 2:3, 3:4, 4:5, 5:6, 6:7, 7:8, 8:9, 9:16, 10:17, 11:18, 12:19, 13:20, 14:22, 15:24}
cam_model_mAPS = np.array([0.42, 0.61, 0.60, 0.63, 0.42, 0.65, 0.37, 0.63, .43, 0.836, 0.854, 0.854, 0.656, 0.755, 0.889, 0.516])

all_mAPs_addition_clus, presence_matrix_addition_clus = decode_files(os.path.join(PATH,"results/Sim_3"), "sim_3_mAPs", 16)
all_mAPs_removal_clus, presence_matrix_removal_clus = decode_files(os.path.join(PATH,"results/Sim_3"), "sim_3_rmv_mAPs", 16)

all_mAPs_addition_net, presence_matrix_addition_net = decode_files_net(os.path.join(PATH,"results/Sim_3"), "sim_3_mAPs", 16)

all_mAPs_removal_net, presence_matrix_removal_net = decode_files_net(os.path.join(PATH,"results/Sim_3"), "sim_3_rmv_mAPs", 16)

coal_status=get_agent_coal(presence_matrix_addition_clus)

"""ax=plot_cross_perf(all_mAPs_addition_clus, coal_status, ref_mAPs=cam_model_mAPS)
ax=plot_cross_perf_net(all_mAPs_addition_net, coal_status, ref_mAPs=cam_model_mAPS, ax=ax)
ax.legend()
plt.show()
ax = plot_detailed_coal(all_mAPs_addition_clus, presence_matrix_addition_clus, 0, ref_mAPs=cam_model_mAPS)
ax.legend()
plt.show()"""

#ax = plot_detailed_coal(all_mAPs_removal_clus, presence_matrix_removal_clus, 2, ref_mAPs=cam_model_mAPS)
#ax.legend()
#plt.show()
_, axs = plt.subplots(1,2, sharey=True)

all_mAPs_removal_clus, presence_matrix_removal_clus = decode_files(os.path.join(PATH,"results/GD_n"), "GD_n_rmv_mAPs", 16)
plot_detailed_coal(all_mAPs_removal_clus, presence_matrix_removal_clus, 0, ref_mAPs=cam_model_mAPS, ax=axs[0])

all_mAPs_removal_clus, presence_matrix_removal_clus = decode_files(os.path.join(PATH,"results/GD_x"), "GD_rmv_mAPs", 16)

plot_detailed_coal(all_mAPs_removal_clus, presence_matrix_removal_clus, 0, ref_mAPs=cam_model_mAPS, ax=axs[1])

#axs[0].legend()
plt.show()

ax = plot_detailed_net(all_mAPs_addition_net, presence_matrix_addition_net, ref_mAPs=cam_model_mAPS)
ax.set_xticks(range(1, all_mAPs_addition_net.shape[1]+1))
ax.legend()
#plt.show()

ax = plot_detailed_net(all_mAPs_removal_net, presence_matrix_removal_net, ref_mAPs=cam_model_mAPS)
ax.set_xticks(range(1, all_mAPs_removal_net.shape[1]+1))
ax.legend()
#plt.show()

"""
ax = plot_detailed_coal(all_mAPs_removal_clus, presence_matrix_removal_clus, 0)
ax.set_xticks(range(1, all_mAPs_removal_clus.shape[1]+1))
ax.legend()
plt.show()
"""
"""
fig, ax = plt.subplots(figsize=(10,10))

plot_within_cluster(all_mAPs, presence_matrix, ax)
#plot_encountered(all_mAPs, presence_matrix, ax)
plot_all_agents(all_mAPs, presence_matrix, ax)
#plot_memory(all_mAPs, presence_matrix, ax)

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

"""