import numpy as np
import pandas as pd
import seaborn as sb
import csv
import matplotlib.pyplot as plt                                                                
                                           

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
line, = plt.plot(no_learning)
line.set_label("No Learning")
line, = plt.plot(continous)
line.set_label("Continuous Learning")
line, = plt.plot(transfer)
line.set_label("Observation Learning")
plt.legend()
plt.show()
