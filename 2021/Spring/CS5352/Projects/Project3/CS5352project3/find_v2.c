//Chen Zhang
//CS5352 Project 3 - Part 2
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
void find(char * where, char * name, char * mmin, char * inum, char * action, char ** remaining);
//see if the file meets any criteria.
int meets_criteria(char * fullpath, char * entry_name, char * name, char * mmin, char * inum);

int main (int argc, char ** argv)
{
    int valid = 1;
    char *where = NULL;
    char c;

    // criteria variables
    char *name = NULL;
    char *mmin = NULL;
    char *inum = NULL;
    // action variables
    char *action = NULL;

    //set the dir argument
    if (argc > 1 && argv[1][0] != '-') {
        where = argv[1];
        argc--;
        argv++;
    } else {
        //if no input value use current dir
        where = ".";
    }

    // comand line option arguments
    static struct option long_options[] = {
        {"name", required_argument, NULL, 'n'},
        {"min", required_argument, NULL, 'm'},
        {"inum", required_argument, NULL, 'i'},
        {"exec", required_argument, NULL, 'e'},
        {NULL, 0, NULL, 0}
    };

    // get command line options
    while ((c = getopt_long_only(argc, argv, "w:n:m:i:e:", long_options, NULL)) != -1) {
        switch (c) {
            case 'n':
                name = optarg;
                break;
            case 'm':
                mmin = optarg;
                break;
            case 'i':
                inum = optarg;
                break;
            case 'e':
                action = optarg;
                break;
            default:
                valid = 0;
                break;
        }
    }

    argc -= optind;
    argv += optind;
    
    if (name == NULL && mmin == NULL && inum == NULL && action == NULL) {
        printf("%s\n", where);        
    }

    if (valid) {
        find(where, name, mmin, inum, action, argv);
    }
    return 0;
}

void find(char * where, char * name, char * mmin, char * inum, char * action, char ** remaining)  {
    DIR * dir;
    struct dirent * entry;
    char * e_name;
    char fullpath[512];
    char curr_dir[] = ".";
    char pre_dir[] = "..";
    int e_type;
    int is_dots;
    int criteria = name != NULL || mmin != NULL || inum != NULL;

    if ((dir = opendir(where))) {

        while ((entry = readdir(dir)) != NULL) {
            e_type = entry -> d_type;
            e_name = entry -> d_name;
            is_dots = strcmp(e_name, curr_dir) == 0 || strcmp(e_name, pre_dir) == 0;

            snprintf(fullpath, sizeof(fullpath), "%s/%s", where, e_name);
            char * found = strstr(fullpath, "//");
            snprintf(fullpath, sizeof(fullpath), found != NULL ? "%s%s" : "%s/%s", where, e_name);
    
            if (!is_dots) {
            	if (meets_criteria(fullpath, e_name, name, mmin, inum)) {
                    printf("%s\n", fullpath);
                }
                if (e_type == DT_DIR) {
                    //recursively call the find function to go inside the dirs.
                    find(fullpath, name, mmin, inum, action, remaining);
                }
            } 
  
        }
        closedir(dir);  
    } else {
        //open dir failed.
        printf("Could not open directory %s\n", where);
    }
}

int meets_criteria(char * fullpath, char * entry_name, char * name, char * mmin, char * inum) {

    int criteria_met = 0;
    struct stat filestats;

    if (name != NULL) {
        // if the entry name matches the critera name
        if(strcmp(entry_name, name) == 0) {
            criteria_met = 1;
        }
    } else if (mmin != NULL) {
        stat(fullpath, &filestats);
        char modifier;
        int minutes;
        int lastmodified;
        // check for '+' or '-' and convert
        // time given in minutes to minutes
        if (!isdigit(mmin[0])) {
            modifier = mmin[0];
            minutes = atoi(++mmin);
        } else {
            minutes = atoi(mmin);
        }
        lastmodified = (int)(time(0) - filestats.st_mtime) / 60;
        // greater than 
        if (modifier == '+' && lastmodified > minutes) {
            criteria_met = 1;
        // less than
        } else if ( modifier == '-' && lastmodified < minutes) {
            criteria_met = 1;
        // equal (exactly)
        } else if (lastmodified == minutes) {
            criteria_met = 1;            
        }
    } else if (inum != NULL) {
        stat(fullpath, &filestats);
        // file's inode number equals input inode number
        // cast/convert to long long to support 32bit architectures
        if ((long long)filestats.st_ino == atoll(inum)) {
            criteria_met = 1;            
        }
    } else {
        criteria_met = 1;
    }

    return criteria_met;
}