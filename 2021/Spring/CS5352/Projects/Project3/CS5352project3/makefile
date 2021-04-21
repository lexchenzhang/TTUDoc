CC	=	gcc
OBJ	=	find_v1	find_v2	find_v3	find_v4
all: $(OBJ)

$(OBJ):%:%.c
	$(CC) -o $@ $^

clean:
	rm -f *.o