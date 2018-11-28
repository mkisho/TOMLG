import numpy as np
import pandas as pd
import seaborn as sb
import csv
import matplotlib.pyplot as plt                                                                
                                           

numbers = []                                                                                 
with open("exp02-plot-test.csv") as csvfile:                                 
    reader = csv.reader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        number.append(int(row[-2].strip()) / (int(row[-1].strip())+0.0))

n_exp = 101        
numbers = [numbers[i*n_exp:i*n_exp+n_exp] for i in range(n_exp)]


data = pd.DataFrame(numbers)
sb.heatmap(data)
