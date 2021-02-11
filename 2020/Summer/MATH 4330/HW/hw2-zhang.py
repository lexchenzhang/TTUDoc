# Chen Zhang
# 6/3/2020
# hw2.py
# This program is finding roots by using quadratic formula

# get a,b,c's value from user input
# print("input a number for a")
a = float(input("input a number for a: "))
# print("input a number for b")
b = float(input("input a number for b: "))
# print("input a number for c")
c = float(input("input a number for c: "))

# print message if a is 0
if(a==0):
    print("warning: a should not be zero")

# calculate roots
x1 = (-b + (b**2 - 4*a*c)**(1/2))/(2*a)
x2 = (-b - (b**2 - 4*a*c)**(1/2))/(2*a)
# print roots
print("the roots are:")
print (x1,x2)



# for question 2
print("------------------------------------------------")
print("Question 2")
# three interpretted programming languages are:
print("three interpretted programming languages are:")
print("JavaScript")
print("PHP")
print("Ruby")

# three compiled languages are:
print("three compiled languages are:")
print("C#")
print("VisualBasic")
print("Erlang")
