def func(x): 
    a=x.split()
    b=a[0].split('/')
    print b[1]
    return int(b[1])

f = open("check.txt","r")
if f.mode == 'r':
    f1 = f.readlines()

f1 = sorted(f1, key=func)

for row in f1:
    print row

f2 = open("testdate.txt","w") 
f2.writelines(f1)