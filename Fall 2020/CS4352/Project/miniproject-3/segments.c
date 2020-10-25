#include "segments.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const char* f1()
{
	return "text 1";
}

const char* f2()
{
	return "text 2";
}

// initilaled global variables stored in data
char data_1[] = "data 1";
char data_2[] = "data 2";

// uninitialed variables stored in bss
char bss_1[80];
char bss_2[80];

void init_pointers()
{
	// local variables stored in stacks
	char stack_1[80] = "stack 1";
	char stack_2[80] = "stack 2";

	text_ptr_1 = f1;
	text_ptr_2 = f2;

	data_ptr_1 = data_1;
	data_ptr_2 = data_2;

	bss_ptr_1 = bss_1;
	bss_ptr_2 = bss_2;
	strcpy(bss_1, "bss 1");
	strcpy(bss_2, "bss 2");

	heap_ptr_1 = (char*)malloc(80);
	heap_ptr_2 = (char*)malloc(80);
	strcpy(heap_ptr_1, "heap 1");
	strcpy(heap_ptr_2, "heap 2");
	
	stack_ptr_1 = stack_1;
	stack_ptr_2 = stack_2;
}

void free_pointers()
{
	free(heap_ptr_1);
	free(heap_ptr_2);
}
