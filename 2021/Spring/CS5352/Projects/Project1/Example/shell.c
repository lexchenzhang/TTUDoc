#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "shell.h"

void execute(char* const args[])
{
  int i;
  int stat_loc;
  pid_t child_pid;

  printf("*** Entered:");
  for (i = 0; args[i] != NULL; i++)
    printf(" %s", args[i]);
  printf(" (%d words)\n", i);

  child_pid = fork();

  if (child_pid < 0) {
    printf("Fork failed");
    exit(1);
  }

  if (child_pid == 0) {
    if (execvp(args[0], args) < 0) {
      printf("Command not found: %s\n", args[0]);
      exit(1);
    }
  } else {
    waitpid(child_pid, &stat_loc, WUNTRACED);
    if(stat_loc == 0) {
      printf("Command exited successfully\n");
    }else{
      printf("Command exited with (%d)\n", stat_loc);
    }
  }
}

