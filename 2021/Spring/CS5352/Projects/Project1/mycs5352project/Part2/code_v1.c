#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(int argc, char* argv[])
{
  int stat_loc;
  pid_t child_pid;

  //make sure the user input the number of child process.
  if(argc < 2)
  {
    printf("you should input an integer for nubmer of child process.\n");
    exit(0);
  }

  //convert the input argument into integer
  char *a = argv[1];
  int num = atoi(a);

  //fork the process in for loop
  for (int i = 0; i < num; ++i)
  {
    child_pid = fork();

    if (child_pid < 0) {
      printf("Fork failed");
      exit(1);
    }
    //if it's child process, print out hello with its pid
    if (child_pid == 0) {
      printf("Hello %d.\n", getpid());
      printf("My parent's pid is %d\n", getppid());
      exit(0);
    } else {
      //if it's parent process, wait its child process finish.
      waitpid(child_pid, &stat_loc, 0);
      if (stat_loc == 0) {
        //child process finish.
      } else {
        printf("Command exited with (%d)\n", stat_loc);
      }
    }
  }
}
