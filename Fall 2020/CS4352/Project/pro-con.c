#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define BUF_SIZE 100

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;
int buf[BUF_SIZE];
int pos1 = 0;
int pos2 = 0;

pthread_cond_t notfull = PTHREAD_COND_INITIALIZER;
pthread_cond_t notempty = PTHREAD_COND_INITIALIZER;

void *
producer(void *args)
{
  int x;
  while (1) {
    pthread_mutex_lock(&lock);
    // critical section  
    while ((pos1 + 1) % BUF_SIZE == pos2) // full
      pthread_cond_wait(&notfull, &lock);
    
    x = rand();
    buf[pos1] = x;
    pos1 = (pos1 + 1) % BUF_SIZE;
    printf("Producer generates %d\n", x);
    
    pthread_mutex_unlock(&lock);
    pthread_cond_signal(&notempty);
    
    //sleep(rand() % 3);
  }
}

void *
consumer(void *args)
{
  int x;
  while (1) {
    sleep(2);
    
    pthread_mutex_lock(&lock);
    // critical section
    while (pos1 == pos2) // empty
      pthread_cond_wait(&notempty, &lock);
    
    x = buf[pos2];
    pos2 = (pos2 + 1) % BUF_SIZE;
    printf("Consumer eats %d\n", x);

    pthread_mutex_unlock(&lock);
    pthread_cond_signal(&notfull);
    
  }
}

int 
main()
{
  srand(7);
  pthread_t t1, t2;
  pthread_create(&t1, NULL, producer, NULL);
  pthread_create(&t2, NULL, consumer, NULL);

  pthread_join(t1, NULL);
  pthread_join(t2, NULL);
}



