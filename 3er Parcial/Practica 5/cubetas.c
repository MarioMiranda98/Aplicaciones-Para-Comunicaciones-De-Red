#include "cubetas.h"

void crearServidores(int numeroServidores) {
    pthread_t hilos[numeroServidores];
    
    for(int i = 0; i < numeroServidores; i += 1) {
        int *j = malloc(sizeof(int));
        *j = i;
        int e = pthread_create(&hilos[i], NULL, &servidor, (void *)j);
        
        if(e) 
            exit(EXIT_FAILURE);
    }

    sleep(1);
}

void crearClientes(int numeroClientes) {
    int rango = 4000/numeroClientes;
    pthread_t hilos[numeroClientes];
    int *indices;
    int inicio = 0;
    int error;

    for(int i = 0; i < numeroClientes; ++i) {
        indices = malloc(sizeof(int) * 3);
        indices[0] = i;
        indices[1] = inicio;
        inicio += rango;
        indices[2] = inicio;
        
        if(i == numeroClientes - 1 && inicio < 4000)
            indices[2] = 4000;

        int e = pthread_create(&hilos[i], NULL, cliente, (void *)indices);
        
        if(e) 
            exit(EXIT_FAILURE);   
    }

    int iniciar = 0;
    for(int i = 0; i < numeroClientes; i += 1) {
        int *ordenados;
        
        if(pthread_join(hilos[i], (void **) &ordenados) != 0)
            exit(EXIT_FAILURE);
        
        iniciar = ingresar(ordenados, iniciar);
    }
}

int ingresar(int *ordenados, int inicio) {
    int i = 0;
    
    while(ordenados[i] != -1) {
        final[inicio++] = ordenados[i++];
    }

    return inicio;
}

void crearNumeros() {
    srand(time(NULL));
    
    for(int i = 0; i < 4000; i += 1) {
        numeros[i] = rand() % 4000;
        printf("%d,", numeros[i]);
    }

    printf("\n");
}

void *servidor(void *indice) {
    int *datitos = (int *) indice;
    int puerto = 8000 + datitos[0];
    char pto[10];
    snprintf(pto, 10, "%d", puerto);

    int socketServidor = levantarServidor(pto);
    printf("Servidor #%d listo\n", datitos[0]);
    int socket;

    socklen_t ctam;
    char hbuf[NI_MAXHOST];
    char sbuf[NI_MAXSERV];

    struct sockaddr_storage c_addr;

    for(;;) {
        ctam = sizeof(c_addr);
        socket = accept(socketServidor, (struct sockaddr *)&c_addr, &ctam);

        if(socket == -1) {
            perror("Server: accept");
            continue;
        }

        if(getnameinfo((struct sockaddr *)&c_addr, sizeof(c_addr), hbuf, sizeof(hbuf), sbuf, sizeof(sbuf), NI_NUMERICHOST | NI_NUMERICSERV) == 0)
            printf("Cliente conectado desde %s:%s\n", hbuf, sbuf);

        char buffer[4000];
        memset(&buffer, 0, sizeof(buffer));
        read(socket, buffer, sizeof(buffer));
        struct dato *datos = (struct dato *) buffer;
        ordenamientoMerge(datos -> numeros, 0, datos -> tam - 1);
        write(socket, (const char *)datos, sizeof(struct dato));
        close(socket);

        break;
    }   

    close(socketServidor);
    return NULL;
}

void *cliente(void *indices) {
    int *datitos = (int *) indices;
    int desordenados[4000];
    int j = 0;

    for(int i = 0; i < 4000; i += 1) {
        if(numeros[i] >= datitos[1] && numeros[i] < datitos[2]) {
            desordenados[j++] = numeros[i];
        }
    }

    int puerto = 8000 + datitos[0];
    char pto[10];
    snprintf(pto, 10, "%d", puerto);
    int socket = levantarCliente(pto);

    struct dato *datos = malloc(sizeof(struct dato));
    datos -> tam = j;
    datos -> numeros = desordenados;
    write(socket, (const char *) datos, sizeof(struct dato));

    char buffer[4000];
    memset(&buffer, 0, sizeof(buffer));
    read(socket, buffer, sizeof(buffer));

    struct dato *datosDevueltos = (struct dato *) buffer;
    int *ordenados = malloc((j + 1) * sizeof(int));

    for(int i = 0; i < j; i += 1)
        ordenados[i] = datosDevueltos -> numeros[i];
    
    ordenados[j] = -1;
    close(socket);
    return (void *) ordenados;
}