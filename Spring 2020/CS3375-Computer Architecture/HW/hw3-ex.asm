#Homework #3, Chen Zhang

#data section

.data

#declare and initialze the array

a: .word 5,2,15,3,7,15,8,9,5,2,15,3,7

#declare the string to be displayed.

Largest: .asciiz "The largest number is "

count: .asciiz "The largest number is included "

times: .asciiz " times. "

space: .asciiz " "

dot: .asciiz ". "

newLine: .asciiz " "

big: .asciiz "bigger.. "

small: .asciiz "smaller.. "

equal: .asciiz "same.. "

#.text section

.text

.globl main

main:

                #load the array address

                la $s0,a      

                #assign the size of array as 13 into $s1.                                     

                addi $s1,$0,13      

                #assign the value largest number as 0                        

                addi $s2,$0,0

                #assign the value for counter as 0                                         

                addi $s3,$0,0    

                #assign the value for i as 0                                      

                addi $t0,$0,0  

                #assign the value for j as 0                                      

                addi $t1,$0,0                                       

#This loop prints the data of the array

forLoop1:

                #check if i value is equal to size of the array

                beq $t0,$s1,next

                #get the value from the array                            

                lw $a0,0($s0)     

                #print the value                              

                addi $v0,$0,1                                  

                syscall    

                #print the space                                     

                la $a0,space                                     

                addi $v0,$0,4                                     

                syscall

                #increment the value of i                                          

                addi $t0,$t0,1  

                #increment the value of $s0 by 4

                #bytes to get the next element of the array.                         

                addi $s0,$s0,4

                #jump to the loop                                 

                j forLoop1                                          

#after printing the array elements

#this code prints a new line

next:

                #print a new line

                la $a0,newLine

                addi $v0,$0,4

                syscall

                #set the value of i to 0

                addi $t0,$0,0

                #load the array address

                la $s0,a

#this loop compares each value

#of the array and prints the output

forLoop2:

                #check if i value is equal to size of the array

                beq $t0,$s1,printResult

                #move the value into #a1

                move $a1,$s2

                #get the value from the array

                lw $a0,0($s0)

                #jump and link to the function compare

                jal compare

                #assign the return value from the function to $t1      

                #j=compare(largest,a[i])                              

                addi $t1,$v0,0    

                #If the compare result 0 means value bigger                            

                beq $t1,0,biggest  

                #If the compare result 1 means same value                           

                beq $t1,1,same     

                #If the compare result 2 means value smaller                            

                beq $t1,2,smallest                           

#This loop prints the output as bigger

#assign the value of array to the $s2

#makes the counter value $s3 as 1

biggest:

                #print the string big

                la $a0,big

                addi $v0,$0,4

                syscall

                #assign the value of array to the $s2

                lw $s2,($s0)

                #makes the counter value $s3 as 1

                addi $s3,$0,1

                #increment the value of i                                          

                addi $t0,$t0,1

                #increment the value of $s0 by 4

                #bytes to get the next element of the array.   

                addi $s0,$s0,4

                #jump to forLoop2

                j forLoop2

#This loop prints the output as same

same:

                #print the string equal

                la $a0,equal

                addi $v0,$0,4

                syscall

                #increment the counter by 1

                addi $s3,$s3,1

                #increment the value of i

                addi $t0,$t0,1

                #increment the value of $s0 by 4

                #bytes to get the next element of the array.  

                addi $s0,$s0,4

                #jump to forLoop2

                j forLoop2

#This loop prints the output as smaller

smallest:
        #print the string small
        la $a0,small
        addi $v0,$0,4
        syscall
        #increment the value of i
        addi $t0,$t0,1
        #increment the value of $s0 by 4
        #bytes to get the next element of the array.  
        addi $s0,$s0,4
        #jump to forLoop2
        j forLoop2

#Print result

printResult:
        #print the string Largest
        la $a0,Largest                    
        addi $v0,$0,4                    
        syscall      
        #print the value of $s2                     
        move $a0,$s2                      
        addi $v0,$0,1                   
        syscall                          
        #Print dot
        la $a0,dot
        addi $v0,$0,4
        syscall
        #print the string count
        la $a0,count
        addi $v0,$0,4
        syscall
        #Print count value
        move $a0,$s3
        addi $v0,$0,1
        syscall
        #print the string Largest
        la $a0,times
        addi $v0,$0,4
        syscall

#End of the program

exit:

addi $v0,$0,10                                    

syscall       

                                     

#definition of the method compare

compare:

                #move the value into $t3

                move $t3,$ra

                #jump and link to the function subtract

                jal subtract

                #compare the return value from the function subtract   

                #if the value is 2 , then go to return2 label                                   

                bgt $v0,0,return2   

                #if the value is 0 , then go to return1 label                               

                beq $v0,0,return1

                #Other wise return 0 as result                             

                addi $v0,$0,0                                    

                move $ra,$t3

                #Return the value

                jr $ra        

                                                 

#this loop returns the value 2

return2:

                addi $v0,$0,2

                move $ra,$t3

                jr $ra

#this loop returns the value 1

return1:

                addi $v0,$0,1

                move $ra,$t3

                jr $ra

#Subtract function

#subtracts two input values and returns the result

subtract:

                sub $v0,$a1,$a0

                jr $ra