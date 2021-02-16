#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int P_NUM;
void GenChild();

int main(int argc, char* argv[])
{
  //make sure user input the number of child process.
  if (argc < 2)
  {
    printf("you should input an integer for nubmer of child process.\n");
    exit(0);
  }

  //convert the input argument into integer
  char *a = argv[1];
  P_NUM = atoi(a);

  //fork the child process by using recursive function call.
  GenChild();
}

void GenChild()
{
  int stat_loc;
  pid_t child_pid;

  child_pid = fork();

  //if fork failed.
  if (child_pid < 0) {
    printf("Fork failed");
    exit(1);
  }

  //if it's child process.
  if (child_pid == 0) {
    //if not meet the target number of process, call itself to keep forking.
    if(--P_NUM > 0)
    {
      printf("Hello %d.\n", getpid());
      printf("My parent's pid is %d\n", getppid());
      GenChild();
    }else{
      //the return boundary of recursive function call.
      printf("Hello %d.\n", getpid());
      printf("My parent's pid is %d\n", getppid());
      exit(0);
    }
  } else {
    waitpid(child_pid, &stat_loc, 0);
    if (stat_loc == 0) {
      //child process finish.
    } else {
      printf("Command exited with (%d)\n", stat_loc);
    }
  }
}

