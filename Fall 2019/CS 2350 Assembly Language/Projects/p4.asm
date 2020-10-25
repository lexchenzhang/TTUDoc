<<<<<<< HEAD
TITLE Summation Program                       (main.asm)

INCLUDE Irvine32.inc
.data

    ARRAY_SIZE = 20
    array DWORD ARRAY_SIZE DUP(?)

    str1 BYTE "Enter A Signed Integer:", 0dh, 0ah, 0
    str2 BYTE "The Sum of the Integers is:", 0dh, 0ah, 0
    str3 BYTE "Enter An Integer from 1 to 20:", 0dh, 0ah, 0

.code
main PROC
   call Clrscr

   call PromptForSize

   mov esi, OFFSET array       ; esi = pointer to array first element array[0]
   mov ecx, eax               ; ecx = eax = actual array size
   call PromptForIntegers

   call SumArray

   call DisplaySum
   exit
=======
TITLE Project 4       (p4.asm)

; Program Description: Project 4
; Author: Chen Zhang
; Creation Date: 11/07/2019
; Revisions: 
; Date:              Modified by:

INCLUDE Irvine32.inc
.data
    ARRAY_SIZE = 20
    array DWORD ARRAY_SIZE DUP(?)
    prompt1 BYTE "Enter A signed integer:", 0dh, 0ah, 0
    prompt2 BYTE "The sum of the integers is:", 0dh, 0ah, 0
    prompt3 BYTE "Enter an integer from 1 to 20:", 0dh, 0ah, 0
.code

main PROC
    call Clrscr
    call PromptForSize
    mov esi, OFFSET array       ; esi = pointer to array first element array[0]
    mov ecx, eax                ; ecx = eax = actual array size
    call PromptForIntegers
    call ArraySum
    call DisplaySum
    exit
>>>>>>> f3d86d16062636ef79dc1f24fd6b9149aba411a8
main ENDP

; Returns: EAX = size of the array from 1 to 20
PromptForSize PROC USES EDX
<<<<<<< HEAD
    mov edx, OFFSET str3           ; edx points to str3
    call WriteString

    call ReadInt
ret
PromptForSize ENDP

; Receives: ESI = pointer to array, ECX = array size
PromptForIntegers PROC USES EDX ESI ECX
    mov edx, OFFSET str1           ; edx points to str1
    L1:
       call WriteString
       call ReadInt           ; eax contains input integer
       call Crlf
       mov [esi], eax           ; array[esi] is updated
       add esi, TYPE DWORD       ; esi = esi + 4 'DWORD
       Loop L1
ret
PromptForIntegers ENDP

; Receives: ESI = pointer to the array, ECX = number of array elements
; Returns: EAX = sum of array elements
SumArray PROC USES ESI ECX
    mov eax, 0                   ; eax = 0
    L1:
       add eax, [esi]           ; eax = eax + array[i]
       add esi, TYPE DWORD       ; esi = esi + 4 'DWORD
       Loop L1
ret
SumArray ENDP

; Receives: EAX = the sum
DisplaySum PROC USES EDX
    mov edx, OFFSET str2           ; edx points to str2
    call WriteString
    call WriteInt
    call Crlf
ret
DISPLAYSUM ENDP
=======
  mov edx, OFFSET prompt3           ; edx points to prompt3
  call WriteString
  call ReadInt
  ret
PromptForSize ENDP

;-----------------------------------------------------
PromptForIntegers PROC
;
; Prompts the user for an array of integers, and fills
; the array with the user's input.
; Receives: ESI points to the array, ECX = array size
; Returns:  nothing
;-----------------------------------------------------
  pushad    ; save all registers

  mov  edx,OFFSET prompt1   ; address of the prompt

L1:
  call WriteString    ; display string
  call ReadInt    ; read integer into EAX
  call Crlf   ; go to next output line
  mov  [esi],eax    ; store in array
  add  esi,4    ; next integer
  loop L1

L2:
  popad   ; restore all registers
  ret
PromptForIntegers ENDP

;-----------------------------------------------------
ArraySum PROC
;
; Calculates the sum of an array of 32-bit integers.
; Receives: ESI points to the array, ECX = array size
; Returns:  EAX = sum of the array elements
;-----------------------------------------------------
  push  esi   ; save ESI, ECX
  push  ecx
  mov   eax,0   ; set the sum to zero

L1:
  add   eax,[esi]   ; add each integer to sum
  add   esi,4   ; point to next integer
  loop  L1    ; repeat for array size

L2:
  pop   ecx   ; restore ECX, ESI
  pop   esi
  ret   ; sum is in EAX
ArraySum ENDP

;-----------------------------------------------------
DisplaySum PROC
;
; Displays the sum on the screen
; Recevies: EAX = the sum
; Returns:  nothing
;-----------------------------------------------------
  push edx
  mov  edx,OFFSET prompt2   ; display message
  call WriteString
  call WriteInt   ; display EAX
  call Crlf
  pop  edx
  ret
DisplaySum ENDP
>>>>>>> f3d86d16062636ef79dc1f24fd6b9149aba411a8

END main