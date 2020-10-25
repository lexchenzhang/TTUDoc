# Chen Zhang
# 6/4/2020
# hw4.py

# given integer n print the result and return the number of cases.
def sum_of_squares(n):
    counter = 0
    for i in range(1,n):
        for j in range(1,n):
            if i**2 + j**2 == n:
                print("{0}**2 + {1}**2 = {2}.".format(i,j,n))
                counter = counter + 1
    return counter

# call the function
while True:
    n = int(input("Enter a positive integer n: "))
    numsq = sum_of_squares(n)
    print("{0} can be written as a sum of squares in {1} ways.".format(n, numsq))
