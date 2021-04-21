//Chen Zhang
//CS5352 Project 3 - Part 1
//4-21-2021

#include <stdio.h> 
#include <ctype.h>
#include <stdlib.h>
#include <getopt.h>
#include <dirent.h>
#include <string.h>
#include <libgen.h>
#include <sys/stat.h>
#include <time.h>

//go through the dirs.
void find(char * where);

int main (int argc, char ** argv)
{
    int valid = 1;
    char *where = NULL;
    char c;

    //set the dir argument
    if (argc > 1 && argv[1][0] != '-') {
        where = argv[1];
        argc--;
        argv++;
    } else {
        //if no input value use current dir
        where = ".";
    }

    argc -= optind;
    argv += optind;

    printf("%s\n", where);
    if (valid) {
        find(where);
    }
    return 0;
}

void find(char * where)  {
    DIR * dir;
    struct dirent * entry;
    char * e_name;
    char fullpath[512];
    char curr_dir[] = ".";
    char pre_dir[] = "..";
    int e_type;
    int is_dots;

    if ((dir = opendir(where))) {

        while ((entry = readdir(dir)) != NULL) {
            e_type = entry -> d_type;
            e_name = entry -> d_name;
            is_dots = strcmp(e_name, curr_dir) == 0 || strcmp(e_name, pre_dir) == 0;

            snprintf(fullpath, sizeof(fullpath), "%s/%s", where, e_name);
            char * found = strstr(fullpath, "//");
            snprintf(fullpath, sizeof(fullpath), found != NULL ? "%s%s" : "%s/%s", where, e_name);
    
            if (!is_dots) {
            	printf("%s\n", fullpath);
                if (e_type == DT_DIR) {
                    //recursively call the find function to go inside the dirs.
                    find(fullpath);
                }
            } 
  
        }
        closedir(dir);  
    } else {
        //open dir failed.
        printf("Could not open directory %s\n", where);
    }
}