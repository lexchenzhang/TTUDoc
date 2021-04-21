#include <stdio.h> 
#include <ctype.h>
#include <stdlib.h>
#include <getopt.h>
#include <dirent.h>
#include <string.h>
#include <libgen.h>
#include <sys/stat.h>
#include <time.h>

void find(char * where);

int main (int argc, char ** argv)
{
    int valid = 1;

    // criteria variables
    char *where = NULL;
    // sepcified option variable
    char c;

    // set where-to-look
    if (argc > 1 && argv[1][0] != '-') {
        // user given directory
        where = argv[1];
        argc--;
        argv++;
    } else {
        // current directory
        where = ".";
    }

    // adjust after processing of arguments
    argc -= optind;
    argv += optind;

    printf("%s\n", where);

    // if valid options were given, call find funciton
    if (valid) {
        find(where);        
    }

    return 0;

}

void find(char * where)  {
    DIR * dir;// directory stream
    struct dirent * entry;
    char * e_name;
    char fullpath[1024];
    char curr_dir[] = ".";
    char pre_dir[] = "..";
    int e_type;
    int is_dots;

    if (dir = opendir(where)) {

        while ((entry = readdir(dir)) != NULL) {
            e_type = entry -> d_type;
            e_name = entry -> d_name;
            is_dots = strcmp(e_name, curr_dir) == 0 || strcmp(e_name, pre_dir) == 0;
            
            // concat for full path
            snprintf(fullpath, sizeof(fullpath),
            "%s/%s", where, e_name);
    
            // if the entry is a sub directory and is not "." or ".."
            if (!is_dots) {
            	printf("%s\n", fullpath);
                if (e_type == DT_DIR) {
                    // recursive for directory
                    find(fullpath);
                }
            } 
  
        }
        // close directory stream
        closedir(dir);  
    } else {
        // error opening diretory stream
        printf("Could not open directory %s\n", where);
    }
}