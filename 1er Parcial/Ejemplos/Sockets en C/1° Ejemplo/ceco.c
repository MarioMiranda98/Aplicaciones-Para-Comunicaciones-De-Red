#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> //getaddrinfo() getnameinfo() freeaddrinfo()
#include <string.h>
#include <unistd.h>//read
#include <stdlib.h>
#define PUERTO "9000"

void * get_in_addr(struct sockaddr *sa);
void error(const char *msj);

int main() {
    struct addrinfo hints, *servInfo, *p;
    int cd, n, n1, rv, op = 0;
    char* serv = "2001::1234:0001";

    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = 0;

    if((rv = getaddrinfo(serv, PUERTO, &hints, &servInfo)) != 0) {
        fprintf(stderr, "getaddrinfo %s\n", gai_strerror(rv));
        return 1;
    }

    for(p = servInfo; p != NULL; p = p->ai_next) {
        if((cd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1) {
            perror("client: socket");
            continue;
        }

        if(connect(cd, p->ai_addr, p->ai_addrlen) == -1) {
            close(cd);
            perror("client: socket");
            continue;
        }

        break;
    }

    if(p == NULL) {
        fprintf(stderr, "client: error al conectar con el servidor\n");
        return 2;
    }

    freeaddrinfo(servInfo);
    FILE * f = fdopen(cd, "w+");
    printf("Conexionen establecida.. Escribe una serie de cadenas <enter> para enviar, SALIR para terminar\n");
    char *linea = (char *) malloc(sizeof(char) * 50);
    bzero(linea, sizeof(linea));
    size_t tam;
    while((n = getline(&linea, &tam, stdin)) != -1) {
        if(strstr(linea, "SALIR") != NULL) {
            printf("Escribio SALIR\n");
            n1 = write(cd, linea, n);
            fflush(f);
            fflush(cd);
            exit(0);
        } else {
            int cantidad = strlen(linea);
            n1 = write(cd, linea, cantidad + 1);
            printf("Se escribieron %d caracteres -> %s\n", n, linea);
            fflush(f);
            bzero(linea, sizeof(linea));
            char eco[cantidad+1];
            bzero(eco, sizeof(eco));
            n1 = read(cd, eco, sizeof(eco));
            printf("Tamanio Eco: %d\n", (int)(sizeof(eco)));
            if(n1 < 0) {
                error("Error al leer desde el socket");
            } else if(n1 == 0) {
                error("Socket Cerrado");
            }
            printf("\n ECO recibido %s\n", eco);
            //free(eco);
        }
    }
    return 0;
}

void * get_in_addr(struct sockaddr *sa) {
    if(sa -> sa_family == AF_INET)
        return &(((struct sockaddr_in *) sa) -> sin_addr);
    
    return &(((struct sockaddr_in6 *) sa) -> sin6_addr);
}

void error(const char *msj) {
    perror(msj);
    exit(1);
}