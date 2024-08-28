#include <stdio.h>
#include <stdlib.h>

int main() {
double a;
double b;
double c = 2.0;
if (scanf("%lf", &a) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
if (scanf("%lf", &b) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
double d = (a + b) * c;
printf("Result:""%lf\n", d);
return 0;
}