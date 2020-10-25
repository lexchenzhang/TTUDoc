# Chen Zhang
# 6/4/2020
# hw6-zhang.py

def f(x):
    return x**2 + x + 41

def build_list(n):
    ret_list=[]
    for i in range(n):
        ret_list.insert(1, f(n-i))
    return ret_list

def isprime(n):
    if n > 1:
        for i in range(2, n):
            if (n % i ) == 0:
                return False
        else:
            return True
    else:
        return False

mylist = build_list(50)
for m in mylist:
    if isprime(m):
        print("{0} is prime.".format(m))
    else:
        print("{0} is NOT prime.".format(m))
