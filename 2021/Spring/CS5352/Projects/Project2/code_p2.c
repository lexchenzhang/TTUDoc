# include <stdio.h>
# include <pthread.h>
# include <stdlib.h>
#include <getopt.h>

# define BufferSize 10
# define MAX 100

void *Producer();
void *Consumer();

int BufferIndex = 0;
char *BUFFER;

pthread_cond_t Buffer_Not_Full = PTHREAD_COND_INITIALIZER;
pthread_cond_t Buffer_Not_Empty = PTHREAD_COND_INITIALIZER;
pthread_mutex_t mVar = PTHREAD_MUTEX_INITIALIZER;

int main(int argc, char* argv[])
{
	char c;
	//define the producer number and consumer number
	int p_num, c_num;
	//define counter i for loops
	int i;

	//set the default producer and consumer's number to 1
	p_num = 1;
	c_num = 1;

	while ((c = getopt(argc, argv, "p:c:h")) != -1) {
		switch (c) {
		case 'p'://add the -p parm for parent num
			p_num = atoi(optarg);
			break;
		case 'c'://add the -c parm for child num
			c_num = atoi(optarg);
			break;
		case 'h':
			exit(1);
			break;
		}
	}

	pthread_t ptids[p_num], ctids[c_num];

	// Display the configuration of the image.
	printf("There are %d producers and %d consumers.\n", p_num, c_num);

	BUFFER = (char *) malloc(sizeof(char) * BufferSize); //allocating buffer

	for (i = 0; i < p_num; i++) {
		//create producer thread based on p_number
		pthread_create(&ptids[i], NULL, Producer, NULL);
	}

	for (i = 0; i < c_num; i++) {
		//create producer thread based on c_number
		pthread_create(&ctids[i], NULL, Consumer, NULL);
	}

	for (i = 0; i < p_num; i++) {
		pthread_join(ptids[i], NULL);
	}

	for (i = 0; i < c_num; i++) {
		pthread_join(ctids[i], NULL);
	}

	free(BUFFER);

	pthread_mutex_destroy(&mVar);

	return 0;
}

//Producer Code
void *Producer()
{
	for (int i=0;i<MAX;i++)
	{
		pthread_mutex_lock(&mVar);
		if (BufferIndex == BufferSize)
		{
			printf("Producer found that buffer is full and waiting for the consumer to consume");
			pthread_cond_wait(&Buffer_Not_Full, &mVar); //producer thread will wait as buffer is full
		}
		BUFFER[BufferIndex++] = '@';
		printf("Producer produced one item : %d \n", BufferIndex);
		pthread_mutex_unlock(&mVar);
		pthread_cond_signal(&Buffer_Not_Empty);
	}
	pthread_exit(0);
}

//Consumer Code
void *Consumer()
{
	for (int i=0;i<MAX;i++)
	{
		pthread_mutex_lock(&mVar);
		if (BufferIndex == -1)
		{
			printf("Consumer found that the buffer is empty and waiting for the producer to produce");
			pthread_cond_wait(&Buffer_Not_Empty, &mVar); //consumer thread will wait as buffer is empty
		}
		printf("Consumer consumed one item : %d \n", BufferIndex--);
		pthread_mutex_unlock(&mVar);
		pthread_cond_signal(&Buffer_Not_Full);
	}
	pthread_exit(0);
}