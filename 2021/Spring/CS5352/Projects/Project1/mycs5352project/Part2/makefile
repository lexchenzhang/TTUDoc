CC=gcc
OBJ=code_v1 code_v2
all: $(OBJ)

$(OBJ):%:%.c
	$(CC) -o $@ $^

clean:
	rm -f *.o
