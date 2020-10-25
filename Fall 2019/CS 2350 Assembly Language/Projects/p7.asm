TITLE Project 7 (p7.asm)

; Program Description: 
; Author: Chen Zhang
; Creation Date: 11/07/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.data
	loopcount DWORD ?
	foreground DWORD ?
	background DWORD ?
.code
main PROC
	mov ecx,16
L1:
	mov loopcount,ecx
	mov foreground,ecx
	dec foreground
	mov ecx,16
L2:
	mov background,ecx
	dec background
	mov eax,background
	shl eax,4
	add eax,foreground
	call settextcolor
	mov al,"A"
	call writechar
	loop L2
	call crlf
	mov ecx,loopcount
	loop L1
	
	mov eax,0
	shl eax,4
	add eax,7
	call settextcolor
	
	exit
main ENDP
END main