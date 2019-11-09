#include "cubetas.h"

int main() {
    int chilos = 0;

    printf("Ingresa el numero de hilos que deseas\n");
    scanf("%d", &chilos);

    printf("Numeros generados\n");
    crearNumeros();

    crearServidores(chilos);
    crearClientes(chilos);

    printf("Numeros Ordenados\n");

    for(int i = 0; i < 4000; i += 1) {
        printf("%d,", final[i]);
    }

    printf("\n");
    return 0;
}