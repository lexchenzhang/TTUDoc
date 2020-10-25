#include "bitmap.h"
#include <errno.h>
#include <getopt.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//Chen Zhang
#include <pthread.h>
#include <sys/time.h>

typedef struct _thread_args_
{
    int tid;
    struct bitmap* bm;
    double xmin;
    double xmax;
    double ymin;
    double ymax;
    double max;
    int width;
    int height;
    int s_height;
    int e_height;
} thread_args;

int iteration_to_color(int i, int max);
int iterations_at_point(double x, double y, int max);
void *compute_image(void *args);

void show_help()
{
    printf("Use: mandel [options]\n");
    printf("Where options are:\n");
    printf("-m <max>    The maximum number of iterations per point. (default=1000)\n");
    printf("-x <coord>  X coordinate of image center point. (default=0)\n");
    printf("-y <coord>  Y coordinate of image center point. (default=0)\n");
    printf("-s <scale>  Scale of the image in Mandlebrot coordinates. (default=4)\n");
    printf("-W <pixels> Width of the image in pixels. (default=500)\n");
    printf("-H <pixels> Height of the image in pixels. (default=500)\n");
    printf("-o <file>   Set output file. (default=mandel.bmp)\n");
    printf("-h          Show this help text.\n");
    printf("\nSome examples are:\n");
    printf("mandel -x -0.5 -y -0.5 -s 0.2\n");
    printf("mandel -x -.38 -y -.665 -s .05 -m 100\n");
    printf("mandel -x 0.286932 -y 0.014287 -s .0005 -m 1000\n\n");
}

int main(int argc, char* argv[])
{
    char c;

    // These are the default configuration values used
    // if no command line arguments are given.

    const char* outfile = "mandel.bmp";
    double xcenter = 0;
    double ycenter = 0;
    double scale = 4;
    int image_width = 500;
    int image_height = 500;
    int max = 1000;
    int thread_num = 4;

    // For each command line argument given,
    // override the appropriate configuration value.

    while ((c = getopt(argc, argv, "x:y:s:W:H:m:o:n:h")) != -1) {
        switch (c) {
        case 'x':
            xcenter = atof(optarg);
            break;
        case 'y':
            ycenter = atof(optarg);
            break;
        case 's':
            scale = atof(optarg);
            break;
        case 'W':
            image_width = atoi(optarg);
            break;
        case 'H':
            image_height = atoi(optarg);
            break;
        case 'm':
            max = atoi(optarg);
            break;
        case 'o':
            outfile = optarg;
            break;
        case 'h':
            show_help();
            exit(1);
            break;
        case 'n'://add the -n parm for thread num
            thread_num = atoi(optarg);
            break;
        }
    }

    // Display the configuration of the image.
    printf("mandel: x=%lf y=%lf scale=%lf max=%d outfile=%s\n", xcenter, ycenter, scale, max, outfile);

    struct timeval  tv1, tv2;

    gettimeofday(&tv1, NULL);

    pthread_t thr[thread_num];

    int h_sec = image_height / thread_num;

    printf("h_sec is %d\n", h_sec);

    // Create a bitmap of the appropriate size.
    struct bitmap* bm = bitmap_create(image_width, image_height);

    // Fill it with a dark blue, for debugging
    bitmap_reset(bm, MAKE_RGBA(0, 0, 255, 0));

    // Compute the Mandelbrot image
    thread_args thr_args[thread_num];

    int i, rc;
    
    for (i = 0; i < thread_num; i++) {
        //init thread datas
        thr_args[i].tid = i;
        thr_args[i].bm = bm;
        thr_args[i].xmin = xcenter - scale;
        thr_args[i].xmax = xcenter + scale;
        thr_args[i].ymin = ycenter - scale;
        thr_args[i].ymax = ycenter + scale;
        thr_args[i].max = max;
        thr_args[i].width = image_width;
        thr_args[i].height = image_height;
        thr_args[i].s_height = h_sec * i;
        thr_args[i].e_height = h_sec;

        //handle the non even distribution of height
        if (i == thread_num - 1)
        {
            thr_args[i].e_height = image_height - thr_args[i].s_height;
        }

        // printf("s_height is %d\n", thr_args[i].s_height);

        if ((rc = pthread_create(&thr[i], NULL, compute_image, &thr_args[i]))) {
            fprintf(stderr, "error: pthread_create, rc: %d\n", rc);
        }
    }

    for (i = 0; i < thread_num; ++i) {
        pthread_join(thr[i], NULL);
    }

    gettimeofday(&tv2, NULL);

    //compute_image(bm, xcenter - scale, xcenter + scale, ycenter - scale, ycenter + scale, max, thread_num);

    // Save the image in the stated file.
    if (!bitmap_save(bm, outfile)) {
        fprintf(stderr, "mandel: couldn't write to %s: %s\n", outfile, strerror(errno));
        return 1;
    }

    printf ("Total time = %f seconds\n",
         (double) (tv2.tv_usec - tv1.tv_usec) / 1000000 +
         (double) (tv2.tv_sec - tv1.tv_sec));

    return 0;
}

/*
Return the number of iterations at point x, y
in the Mandelbrot space, up to a maximum of max.
*/

int iterations_at_point(double x, double y, int max)
{
    double x0 = x;
    double y0 = y;

    int iter = 0;

    while ((x * x + y * y <= 4) && iter < max) {

        double xt = x * x - y * y + x0;
        double yt = 2 * x * y + y0;

        x = xt;
        y = yt;

        iter++;
    }

    return iteration_to_color(iter, max);
}

/*
Convert a iteration number to an RGBA color.
Here, we just scale to gray with a maximum of imax.
Modify this function to make more interesting colors.
*/

int iteration_to_color(int i, int max)
{
    int gray = 255 * i / max;
    return MAKE_RGBA(gray, gray, gray, 0);
}

/*
Compute an entire Mandelbrot image, writing each point to the given bitmap.
Scale the image to the range (xmin-xmax,ymin-ymax), limiting iterations to "max"
*/

void *compute_image(void *args)
{
    int i, j;
    thread_args *data = (thread_args *)args;
    printf("thread id: %d\n", data->tid);
    for (j = data->s_height; j < (data->s_height+data->e_height); j++) {
        for (i = 0; i < data->width; i++) {
            double x = data->xmin + i * (data->xmax - data->xmin) / data->width;
            double y = data->ymin + j * (data->ymax - data->ymin) / data->height;
            int iters = iterations_at_point(x, y, data->max);
            bitmap_set(data->bm, i, j, iters);
        }
    }
    pthread_exit(NULL);
}
