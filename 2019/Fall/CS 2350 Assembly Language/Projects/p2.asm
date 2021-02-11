TITLE Project 2 (p2.asm)

; Program Description: Display reverse string in memory
; Author: Chen Zhang
; Creation Date: 10/16/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.data
	source BYTE "Test string.",0
	target BYTE SIZEOF source DUP('@')
.code
main PROC
	mov esi, OFFSET target
	mov ebx, 1
	mov ecx, SIZEOF target
	call DumpMem
	mov esi, OFFSET source
	mov ebx, 1
	mov ecx, SIZEOF source
	mov edx, OFFSET target
	call DumpMem
L1:
	;call DumpRegs
	mov al, [esi]
	mov [edx+ecx-1], al
	inc esi
	loop L1
	mov esi, OFFSET target
	mov ebx, 1
	mov ecx, SIZEOF target
	call DumpMem
	exit
main ENDP
END main
