TITLE Project 3 (p3.asm)

; Program Description: Display reverse array in memory
; Author: Chen Zhang
; Creation Date: 10/16/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.data
	array DWORD 1,2,3,4,5,6,7,8,9,10,11
.code
main PROC
	mov esi, 0
	mov eax, SIZEOF array
	mov ebx, TYPE array
	mov edi, (SIZEOF array - TYPE array)
	mov ecx, LENGTHOF array/2
	call DumpRegs
	;mov esi, OFFSET array
	;mov ebx, 1
	;mov ecx, SIZEOF array
	;call DumpMem
L1:
	mov eax, array[esi]
	xchg eax, array[edi]
	mov array[esi], eax
	add esi, TYPE array
	sub edi, TYPE array
	loop L1
	mov esi, OFFSET array
	mov ebx, 1
	mov ecx, SIZEOF array
	call DumpMem
	exit
main ENDP
END main