import matplotlib
matplotlib.use('agg')
import matplotlib.pyplot as plt
import csv

x = []
y = []

with open('testdate.txt','r') as csvfile:
    plots = csv.reader(csvfile, delimiter='\t')
    print "HERE"
    for row in plots:
        print row[0]
        x.append(str(row[0]))
        y.append(float(row[1]))

plt.xticks(rotation='vertical')
plt.bar(x,y)
plt.xlabel('Date in MM/DD/YY format')
plt.ylabel('Frequency of rides')
plt.title('Date vs Frequency plot of Uber Data Analysis\nSeptember')

repr(x)
plt.legend()
plt.show()

plt.savefig('September.png',bbox_inches='tight')

print "END"