# Chen Zhang
# 6/4/2020
# hw3-zhang.py

# ask user input for y and N
y = float(input("input a float value for y which greater or equal 1: "))
N = int(input("input a positive integer N: "))
# initial the previous value and next value
prev_x = 1
next_x = 0

for n in range(N):
    next_x = 0.5*(prev_x+y/prev_x)
    prev_x = next_x

print("result is {0}".format(next_x))
