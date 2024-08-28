#include <stdio.h>
#include <stdlib.h>

int main() {
double a = 0;
double b = 0;
if (scanf("%lf", &a) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
if (scanf("%lf", &b) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
double c = (a + b) * 2.0;
printf("Result:""%lf\n", c);
return 0;
}