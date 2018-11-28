import numpy as np
import pandas as pd
import seaborn as sb
import csv
import matplotlib.pyplot as plt                                                                
                                           

numbers = [[]]                                                                                 
with open("exp01-100-continuos.csv") as csvfile:                                 
    reader = csv.DictReader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        if row[" mind_load"].strip() == "null":                                                  
            numbers.append([])                                         
            continue                                                                             
        #print(numbers[-1])                           
        numbers[-1].append(row[" steps"])

numbers = numbers[1:] 

best = []
     with open("exp01.csv") as csvfile:
         reader = csv.DictReader(csvfile, delimiter=",")
         for row in reader:
             if row[" mind_load"].strip() == "null":
                 numbers.append([])
             #print(numbers[-1])
             best.append(row[" steps"])


complete_data = []
     for experiment in range(len(numbers)):
         exp_processed = []
         for index in range(len(numbers[experiment])):
             n = int(numbers[experiment][index].strip())
             b = int(best[index].strip())
             exp_processed.append( n -b +1)
         complete_data.append(np.array(exp_processed))

data = pd.DataFrame(complete_data)
data[100].fillna(0, inplace=True)
best = [int(x.strip()) for x in best]
