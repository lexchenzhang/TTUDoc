def gcd(a,b):
    if b==0:
        return a
    return gcd(b,a%b)

def gcd_divops(a,b):
    if b==0:
        return 0
    return gcd_divops(b,a%b) + 1

# N = 10
# N = 100
# N = 1000
list = [10,100,1000]
div_num = 0

for N in list:
    for i in range(1,N):
        for j in range(1,N):
           div_num += gcd_divops(i,j)
    average = div_num / (N*N)
    div_num = 0
    print("the average is {0} when N is {1}".format(average,N))
