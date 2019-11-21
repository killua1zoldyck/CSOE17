import matplotlib
matplotlib.use('agg')
import matplotlib.pyplot as plt
import csv

x = []
y = []

with open('april.txt','r') as csvfile:
    plots = csv.reader(csvfile, delimiter='\t')
    for row in plots:
        # print row[0]
        x.append(str(row[0]))
        y.append(float(row[1]))

# dict = {new_list: [] for new_list in range(len(x))} 

# for i in range(len(x)):
#     dict[x[i]]=y[i]

# dict.sort()
# print dict
# plt.plot(x,y, label='Loaded from file!')
plt.xticks(rotation='vertical')
plt.bar(x,y)
plt.xlabel('Date in MM/DD/YY format')
plt.ylabel('Frequency of rides')
plt.title('Date vs Frequency plot of Uber Data Analysis\nApril')


plt.legend()
plt.show()

plt.savefig('april date graph.png',bbox_inches='tight')

print "END"