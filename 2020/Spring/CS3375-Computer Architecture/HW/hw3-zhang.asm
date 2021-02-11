.data
	#declare and initialze the array
	arr: .word 5,2,15,3,7,15,8,9,5,2,15,3,7
	space: .asciiz " "
	__STR_bigger: .asciiz "bigger..\n"
	__STR_same: .asciiz "same..\n"
	__STR_smaller: .asciiz "smaller..\n"
	Largest: .asciiz "The largest number is "
	dot: .asciiz ".\n"
	count: .asciiz "The largest number is included "
	times: .asciiz " times.\n"
.text
.globl _main

_main:

	#load the array address
	la $s0, arr
	
	#assign the size of array as 13 into $s1.
	addi $s1, $0, 13
	
	#assign the value largest number as 0
	addi $s2, $0, 0
	
	#assign the value for counter as 0
	addi $s3, $0, 0
	
	#assign the value for i as 0
	addi $t0, $0, 0
	
	#assign the value for j as 0
	addi $t1, $0, 0
	
	#print the array
	#jal PrintArr
	
	#init the loop counter
	addi $t0, $0, 0
	#start compare
CLoop:
	jal PrintInt
	jal PrintNewLine
	beq $t0, $s1, PrintResult
	move $a1, $s2
	lw $a0, 0($s0)
	jal Compare
	addi $t1, $v0, 0
	#If the compare result 0 means $a0 < $a1
	beq $t1, 0, PrintBigger
	#If the compare result 1 means $a0 == $a1
	beq $t1, 1, PrintSame
	#If the compare result 2 means $a0 > $a1
	beq $t1, 2, PrintSmaller
	
PrintBigger:
	#print the string big
	la $a0, __STR_bigger
	addi $v0, $0, 4
	syscall
	#assign the value of array to the $s2
	lw $s2, ($s0)
	#makes the counter value $s3 as 1
	addi $s3, $0, 1
	#increment the value of i
	addi $t0, $t0, 1
	#increment the value of $s0 by 4
	#bytes to get the next element of the array.
	addi $s0, $s0, 4
	#jump to forLoop2
	j CLoop
	
PrintSame:
	#print the string equal
	la $a0,__STR_same
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
	j CLoop
	
PrintSmaller:
        #print the string small
        la $a0,__STR_smaller
        addi $v0,$0,4
        syscall
        #increment the value of i
        addi $t0,$t0,1
        #increment the value of $s0 by 4
        #bytes to get the next element of the array.  
        addi $s0,$s0,4
        #jump to forLoop2
        j CLoop
        
PrintResult:
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
	jal Exit
	
#Print Array
PrintArr:
for_loop:
	beq $t0, $s1, PrintNewLine
	lw $a0, 0($s0)
        #print the value
        addi $v0, $0, 1
        syscall
        #print the space
        la $a0, space
        addi $v0, $0, 4
        syscall
        #increment the value of i
        addi $t0, $t0, 1
        #increment the value of $s0 by 4
        #bytes to get the next element of the array.
        addi $s0, $s0, 4
        #jump to the loop
        j for_loop
        
#PrintNewLine
.text
PrintNewLine:
	li $v0, 4
	la $a0, __PNL_newline
	syscall
	jr $ra
.data
__PNL_newline: .asciiz "\n"

# subprogram: PrintInt
.text
PrintInt:
	move $a0, $s2
	li $v0, 1
	syscall
	jr $ra

#Subtract function
#subtract from a0 to a1 and returns the result in v0
Subtract:
	sub $v0, $s2, $a0
	jr $ra
	
#definition of the method compare
Compare:
	move $t3,$ra
	#jump and link to the function subtract
	jal Subtract
	#move $a1, $v0
	#jal PrintInt
	#compare the return value from the function subtract
	#if the value is 2 , then go to return2 label
	bgt $v0, 0, Return2
	#if the value is 0 , then go to return1 label
	beq $v0, 0, Return1
	#Other wise return 0 as result
	addi $v0, $0, 0
	#Return the value
	move $ra,$t3
	jr $ra
	
#this loop returns the value 2

Return2:
	addi $v0, $0, 2
	move $ra,$t3
	jr $ra

#this loop returns the value 1
Return1:
	addi $v0, $0, 1
	move $ra,$t3
	jr $ra
	
#End of the program
Exit:
	addi $v0,$0,10                                    
	syscall