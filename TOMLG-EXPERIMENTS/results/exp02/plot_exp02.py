numbers = []                                                                                 
with open("exp02-mod-func.csv") as csvfile:                                 
    reader = csv.reader(csvfile, delimiter=",")                    
    for row in reader:                                                 
        numbers.append(int(row[3].strip()) / (int(row[4].strip())+0.0))
 
n_exp = 101        
numbers = [numbers[i*n_exp:i*n_exp+n_exp] for i in range(n_exp)]
 
 
data = pd.DataFrame(numbers)
sb.set(font_scale=6)
ax= sb.heatmap(data, xticklabels=False, yticklabels=False)
cbar = ax.collections[0].colorbar
cbar.set_ticks([0, .2, .75, 1])
cbar.set_ticklabels(['0%', '20%', '75%', '100%'])
 
 
 
plt.xlabel('Scenaries')
plt.ylabel('Agents')
plt.title("Percentage of Accuracy of Forecasts")
 
plt.show()

