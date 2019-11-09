#ifndef __CUBETAS__
#define __CUBETAS__

#include <time.h>
#include <pthread.h>
#include "sockets.h"
#include "MergeSort.h"

struct dato {
    int tam;
    int *numeros;
};

void *cliente(void *);
void *servidor(void *);
void crearNumeros();
void crearClientes(int);
void crearServidores(int);
int ingresar(int *, int);

int numeros[4000];
int final[4000];

#endif // !__CUBETAS__