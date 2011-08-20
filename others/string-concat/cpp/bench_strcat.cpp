/**
 * $Release: $
 * $Copyright: copyright(c) 2011 kuwata-lab.com all rights reserved $
 * $License: Creative Commons Attribution (CC BY) $
 */


#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>
//#include <time.h>
#include <sys/time.h>


const char *members[] = {"Haruhi", "Mikuru", "Yuki", "Itsuki", "Kyon"};


void task_empty(int loop) {
    const char *s1 = members[0], *s2 = members[1], *s3 = members[2], *s4 = members[3], *s5 = members[4];
    while (--loop >= 0) {
        std::string buf("");
        for (int j = 0; j < 20; j++) {
            // pass
        }
    }
}


void task_strcat1(int loop) {
    const char *s1 = members[0], *s2 = members[1], *s3 = members[2], *s4 = members[3], *s5 = members[4];
    while (--loop >= 0) {
        std::string buf("");
        //
        buf.append("<table>\n");
        for (int j = 0; j < 20; j++) {
            buf.append("  <tr>\n"
"    <td>"); buf.append(s1); buf.append("</td>\n"
"    <td>"); buf.append(s2); buf.append("</td>\n"
"    <td>"); buf.append(s3); buf.append("</td>\n"
"    <td>"); buf.append(s4); buf.append("</td>\n"
"    <td>"); buf.append(s5); buf.append("</td>\n"
"  </tr>\n");
        }
        buf.append("</table>\n");
        //
        //if (loop == 1) std::cout << buf;
    }
}


void task_strcat2(int loop) {
    const char *s1 = members[0], *s2 = members[1], *s3 = members[2], *s4 = members[3], *s5 = members[4];
    while (--loop >= 0) {
        std::string buf("");
        buf.reserve(4*1024);       // !!!
        //
        buf.append("<table>\n");
        for (int j = 0; j < 20; j++) {
            buf.append("  <tr>\n"
"    <td>"); buf.append(s1); buf.append("</td>\n"
"    <td>"); buf.append(s2); buf.append("</td>\n"
"    <td>"); buf.append(s3); buf.append("</td>\n"
"    <td>"); buf.append(s4); buf.append("</td>\n"
"    <td>"); buf.append(s5); buf.append("</td>\n"
"  </tr>\n");
        }
        buf.append("</table>\n");
        //
        //if (loop == 1) std::cout << buf;
    }
}


double run(const char *title, void (*body)(int loop_), int loop, double empty_time) {
    struct timeval start, stop;
    gettimeofday(&start, NULL);
    (*body)(loop);
    gettimeofday(&stop, NULL);
    int sec  = stop.tv_sec  - start.tv_sec;
    int usec = stop.tv_usec - start.tv_usec;
    double real = (double)sec + (double)usec / 1000000.0;
    double actual = real - empty_time;
    printf("%-35s %10.3f %10.3f\n", title, real, actual);
    return real;
}


int main(int argc, char *argv[]) {
    int loop = 100*1000;
    int cycle = 3;
    char *s;
    if ((s = getenv("N")) != NULL) loop  = atoi(s);
    if ((s = getenv("C")) != NULL) cycle = atoi(s);
    //
    printf("# loop = %d\n", loop);
    double empty_time = 0.0;
    for (int i = 1; i <= cycle; i++) {
        printf("\n");
        printf("# cycle=%d %25s %10s %10s\n", i, " ", "real", "actual");
        empty_time = \
        run("(Empty)",               task_empty,   loop, empty_time);
        run("string.append()",       task_strcat1, loop, empty_time);
        run("string.append(4*1024)", task_strcat2, loop, empty_time);
    }
    //
    return 0;
}
