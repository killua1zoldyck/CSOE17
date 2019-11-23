import matplotlib
matplotlib.use('agg')
import matplotlib.pyplot as plt
import csv


def func(x): 
    a=x.split()
    b=a[0].split('/')
    # print b[1]
    return int(b[1])

def func1(x):
    a = x.split()
    b = a[0].split('/')
    # print b[0]
    return b[0]

def plot_func(x,y):
    plt.clf()
    plt.xticks(rotation='vertical')
    plt.bar(x,y)
    plt.xlabel('Date in MM/DD/YY format')
    plt.ylabel('Frequency of rides')
    plt.title('Date vs Frequency plot of Uber Data Analysis\n'+a[i])
    plt.legend()
    plt.show()
    plt.savefig(a[i]+'test.png',bbox_inches='tight')

a = ["April" , "May" , "June" , "July" , "August" , "September"]

f = open("datevscount.txt","r")
if f.mode == 'r':
    f1 = f.readlines()

f1 = sorted(f1, key=func)
f1 = sorted(f1, key=func1)

i = 0
month = a[i]
m = 3  
for line in f1:
        if m==3:
            x = []
            y = []
            m = m+1
        if i>=6:
            break
        row = line.split()
        if int(m)!=int(line[0]):
            plot_func(x,y)
            print i
            i=i+1
            del x
            del y
            x = []
            y = []
            x.append(str(row[0]))
            y.append(float(row[1]))
        else:
            x.append(str(row[0]))
            y.append(float(row[1]))
        m = line[0]

print i
plot_func(x,y)

print "END"

# with open('testdate.txt','r') as csvfile:
#     plots = csv.reader(csvfile, delimiter='\t')
#     print "HERE"
#     for row in plots:
#         print row[0]
#         x.append(str(row[0]))
#         y.append(float(row[1]))



