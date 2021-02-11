# Chen Zhang
# 6/22/2020
# hw9-zhang.py

import math

def Newton(F, dF, x0, epsilon):
    n = 0
    xn = x0
    while abs(F(xn)) >= epsilon:
        Xn = xn - F(xn) / dF(xn)
        xn = Xn
        if n == 25 or abs(dF(xn)) < epsilon:
            print()
            return None
        n = n + 1
    return xn

# test 1
def f1(x):
    return x**2-5

def df1(x):
    return 2*x

x = 2
xn = Newton(f1,df1,x,1e-10)

if type(xn) is float:
    print("fl(%1.8f) = %1.8f" % (xn,f1(xn)))

# part b
def f2(x):
    return x**5 - x**4 + x**2 + x - 1

def df2(x):
    return 5*x**4 - 4*x**3 + 2*x + 1

x = 1
xn = Newton(f2,df2,x,1e-10)

if type(xn) is float:
    print ("f2(%1.8f) = %1.8f" %(xn,f2(xn)))

# part c
def g(x):
    return math.e**(-x**2)-math.sin(x)

def dg(x):
    return -2*math.e**(-x**2)*x-math.cos(x)

x = 1
xn = Newton(g,dg,x,1e-10)

if type(xn) is float:
    print ("f3(%1.8f) = %1.8f" %(xn,g(xn)))
