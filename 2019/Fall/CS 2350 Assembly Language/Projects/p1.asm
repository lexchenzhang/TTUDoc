TITLE Project 1 (p1.asm)

; Program Description: First seven values in Fibonacci
; Author: Chen Zhang
; Creation Date: 10/16/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.data
.code
main PROC
	mov eax, 1
	call DumpRegs
	mov ebx, 0
	mov edx, 1
	mov ecx, 6
L1:
	mov eax, ebx
	add eax, edx
	call DumpRegs
	mov ebx, edx
	mov edx, eax
	Loop L1
	exit
main ENDP
END main