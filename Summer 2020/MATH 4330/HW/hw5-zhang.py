# Chen Zhang
# 6/4/2020
# hw5-zhang.py

# calculate result based on given function
def f3n1(n):
    if n%2 == 0:
        return n//2
    else:
        return (3*n+1)//2

# return the list
def seq_3n1(n):
    ret_list=[]
    # store first element which is n
    ret_list.insert(1, n)
    tem = f3n1(n)
    while tem != 1:
        # store the f(n),f(f(n)),... without last one
        ret_list.insert(len(ret_list), tem)
        tem = f3n1(tem)
    # store the last one which is 1
    ret_list.insert(len(ret_list), tem)
    return ret_list

n = int(input("Enter a positive integer n:"))
seq = seq_3n1(n)
print("The resulting sequence is: {0}".format(seq))
