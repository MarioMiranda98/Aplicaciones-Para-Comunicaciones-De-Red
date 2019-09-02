#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/in.h>

struct datos {
    char nombre[30];
    char apellido[30];
    short edad;
};

int main() {
    int cd, n, rv, op = 0;
    char *PUERTO = "8000";
    struct sockaddr_in serverADDRESS;
    struct hostent *hostINFO;
    char *serv = "2001::1234:0001";
    FILE *f, *f1;
    
    int status;
    struct addrinfo hints, *servInfo, *p;
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = 0;

    if((rv = getaddrinfo(serv, PUERTO, &hints, &servInfo)) != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }

    for(p = servInfo; p != NULL; p = p->ai_next) {
        if((cd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1) {
            perror("client: socket");
            continue;
        }

        if(connect(cd, p->ai_addr, p->ai_addrlen) == -1) {
            close(cd);
            perror("cliente: connect");
            continue;
        }
        break;
    }

    if(p == NULL) {
        fprintf(stderr, "client: error al conectar con el servidor\n");
        return 2;
    }

    freeaddrinfo(servInfo);
    printf("Conexion Establecida\n");
    f1 = fdopen(cd, "w+");
    struct datos *d = (struct datos *) malloc(sizeof(struct datos));
    char *tmp = (char *) malloc(sizeof(char) * 30);
    size_t tam;
    memset(tmp, 0, sizeof(tmp));
    printf("Escribe un nombre\n");
    n = getline(&tmp, &tam, stdin);
    strncpy(d->nombre, tmp, strlen(tmp));
    printf("Escrbe un apellido\n");
    memset(tmp, 0, sizeof(tmp));
    n = getline(&tmp, &tam, stdin);
    strncpy(d->apellido, tmp, strlen(tmp));
    printf("Escribe la edad\n");
    int ed;
    scanf("%d", &ed);
    fflush(stdin);
    d->edad = htons(ed);
    printf("\nEnviendo estructura con %d bytes, datos:\nNombre: %s\nApellido: %s\nEdad: %d\n",(int)sizeof(struct datos),d->nombre,d->apellido,ntohs(d->edad));
    n = write(cd, (const char *) d, sizeof(struct datos));
    fflush(f1);
    printf("\nSe enviaron: %d bytes\n", n);
    fclose(f1);
    free(tmp);
    free(d);
    close(cd);

    return 0;
}