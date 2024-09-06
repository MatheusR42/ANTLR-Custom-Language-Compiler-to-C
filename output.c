#include <stdio.h>
#include <stdlib.h>

int main() {
double a;
double b;
double c = 2.0;
printf("Digite o valor de A: ");
if (scanf("%lf", &a) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
printf("Digite o valor de b: ");
if (scanf("%lf", &b) != 1) {
    fprintf(stderr, "Error: Invalid input. Expected a decimal number.\n");
    exit(1);
}
printf("C está predefinido com valor: ""%lf\n", c);
double d = a + b * c;
printf("Resultado: ");
printf("");
printf("a + b * c = ""%lf\n", d);
return 0;
}