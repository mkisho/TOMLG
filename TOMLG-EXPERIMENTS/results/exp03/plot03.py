import numpy as np
import pandas as pd
import seaborn as sb
import csv
import matplotlib.pyplot as plt  
import matplotlib                                                              
                                           
font = {'family' : 'normal',
        'weight' : 'bold',
        'size'   : 22}

matplotlib.rc('font', **font)

no_learning = []                                                                   
with open("agent-no-learning.csv") as csvfile:                                 
    reader = csv.reader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        no_learning.append(int(row[-1].strip()))

continous = []                                                                   
with open("agent-continous-learning-test.csv") as csvfile:                                 
    reader = csv.reader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        continous.append(int(row[-1].strip()))


transfer = []                                                                   
with open("exp03-transfer-test.csv") as csvfile:                                 
    reader = csv.reader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        transfer.append(int(row[-1].strip()))


# multiple line plot
line, = plt.plot(no_learning, linewidth=2)
line.set_label("No Learning")
line, = plt.plot(continous, linewidth=2)
line.set_label("Continuous Learning")
line, = plt.plot(transfer, linewidth=2)
line.set_label("Observation Learning")
plt.legend(loc="upper left", bbox_to_anchor=[0, 1],
           ncol=2, shadow=True, title="Legend", fancybox=True)

plt.xlabel('Scenaries')
plt.ylabel('Steps')
plt.title('Number of Steps Comparison Between Different Learning Methods')

#plt.legend()
plt.show()
