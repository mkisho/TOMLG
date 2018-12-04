import numpy as np
import pandas as pd
import seaborn as sb
import csv
import matplotlib.pyplot as plt  
import matplotlib                                                              

fontsize = 25
font = {'family': 'normal',
        'weight': 'bold',
        'size': 15}

matplotlib.rc('font', **font)




best = dict()
with open("exp01-best.csv") as csvfile:
    reader = csv.DictReader(csvfile, delimiter=",")
    for row in reader:
        best[row["cenario"]] = int(row[" steps"].strip())


order_desc = []
with open("exp01-ordered-desc.csv") as csvfile:
    reader = csv.reader(csvfile, delimiter=",")
    for row in reader:
        val = int(row[3]) - best[row[0]]
        order_desc.append(val)

order_asc = []
with open("exp01-ordered-asc.csv") as csvfile:
    reader = csv.reader(csvfile, delimiter=",")
    for row in reader:
        val = int(row[3]) - best[row[0]]
        order_asc.append(val)

fig, ax = plt.subplots()
ax.plot(order_asc, label='Rising Difficulty', linewidth=3)
ax.plot(order_desc, label='Descending Difficulty', linewidth=3)
legend = ax.legend(loc='upper center', shadow=True, fontsize='x-large')

plt.xlabel('Scenaries', fontsize=fontsize)
plt.ylabel('Steps', fontsize=fontsize)

#plt.ylim(-0.5, max(order_desc))
plt.xlim(-0.5, len(order_desc)-1)
plt.title('Continuous Learning by Complexity of Environments', fontsize=fontsize)
# Put a nicer background color on the legend.
#legend.get_frame().set_facecolor('C0')

plt.show()


