<<<<<<< HEAD
;Title: This program clears the screen and located the cursor
;And prompt for two integers and add the values
INCLUDE Irvine32.inc
.DATA
;Prompt string for first integer
prompt1 BYTE "Enter the first integer: ",0
;Prompt string for first integer
prompt2 BYTE "Enter the second integer: ",0
;Display string for sum of two values
sum BYTE "The sum of the integers is: ",0
.CODE
main PROC
;Repeat loop until 3 times
mov ecx,3
;Start the loop
L1:
;to set the screen to middle of the screen
;set the row and column values   
mov dl, 20
mov dh, 10
call Clrscr ;clear screen
call Gotoxy ;place cursor
;move the first prompt string in edx
mov edx, OFFSET prompt1
;print the string
call WriteString
;Read the integer
call ReadInt   
;save the value in ebx register
mov ebx, eax   
;to set the screen to middle of the screen
;set the row and column values
mov dl, 19   
mov dh, 11
;place cursor
call Gotoxy
;mov the second prompt string in edx
mov edx, OFFSET prompt2
;print the string
call WriteString
;Read the integer
call ReadInt
;add the values in registers
add eax, ebx   
;to set the screen to middle of the screen
;set the row and column values
mov dl, 17
mov dh, 12
call Gotoxy ;place cursor

;move the third output string in edx
mov edx, OFFSET sum
call WriteString ;print the thrid print string
call WriteInt ;print the sum of integers
call Crlf ;print space
call ReadInt ;pause the screen
;Iterate loop
loop L1
exit ;exit from the program
=======
TITLE Project 5 (p5.asm)

; Program Description: First seven values in Fibonacci
; Author: Chen Zhang
; Creation Date: 11/07/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.DATA
	;Prompt string for first integer
	prompt1 BYTE "Enter the first integer: ",0
	;Prompt string for first integer
	prompt2 BYTE "Enter the second integer: ",0
	;Display string for sum of two values
	sum BYTE "The sum of the integers is: ",0
.CODE

main PROC
	;Repeat loop until 3 times
	mov ecx,3
	;Start the loop
L1:
	;to set the screen to middle of the screen
	;set the row and column values   
	mov dl, 20
	mov dh, 10
	call Clrscr ;clear screen
	call Gotoxy ;place cursor
	;move the first prompt string in edx
	mov edx, OFFSET prompt1
	;print the string
	call WriteString
	;Read the integer
	call ReadInt   
	;save the value in ebx register
	mov ebx, eax   
	;to set the screen to middle of the screen
	;set the row and column values
	mov dl, 19   
	mov dh, 11
	;place cursor
	call Gotoxy
	;mov the second prompt string in edx
	mov edx, OFFSET prompt2
	;print the string
	call WriteString
	;Read the integer
	call ReadInt
	;add the values in registers
	add eax, ebx   
	;to set the screen to middle of the screen
	;set the row and column values
	mov dl, 17
	mov dh, 12
	call Gotoxy ;place cursor

	;move the third output string in edx
	mov edx, OFFSET sum
	call WriteString ;print the thrid print string
	call WriteInt ;print the sum of integers
	call Crlf ;print space
	call ReadInt ;pause the screen
	Loop L1
	exit
>>>>>>> f3d86d16062636ef79dc1f24fd6b9149aba411a8
main ENDP
END main