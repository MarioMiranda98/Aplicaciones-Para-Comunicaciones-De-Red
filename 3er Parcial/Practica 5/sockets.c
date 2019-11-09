#include "sockets.h"

int levantarServidor(const char *puerto) {
    struct addrinfo *servinfo, *p;
    struct addrinfo hints;
    int rv, sd, v = 1;

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET6;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;
    hints.ai_protocol = 0;
    hints.ai_canonname = NULL;
    hints.ai_addr = NULL;
    hints.ai_next = NULL;

    if((rv = getaddrinfo("localhost", puerto, &hints, &servinfo)) != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }

    for(p = servinfo; p != NULL; p = p -> ai_next) {
        if((sd = socket(p -> ai_family, p -> ai_socktype, p -> ai_protocol)) == -1) {
            perror("server: socket");
            continue;
        }

        if(setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &v, sizeof(int)) == -1) {
            perror("setsockopt");
            close(sd);
            exit(EXIT_FAILURE);
        }

        if(bind(sd, p -> ai_addr, p -> ai_addrlen) == -1) {
            close(sd);
            perror("server: bind");
            continue;
        }
        break;
    }

    freeaddrinfo(servinfo);

    if(p == NULL) {
        fprintf(stderr, "servidor: error en bind\n");
        exit(1);
    }

    if(listen(sd, 5) == -1) {
        perror("server: listen");
        exit(1);
    }

    return sd;
}

int levantarCliente(const char *puerto) {
    struct addrinfo *cliente, *p;
    struct addrinfo hints;
    int rv, cd, v = 1;
    char *srv = "2001::1234:0001";

    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET6;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_protocol = 0;

    if((rv = getaddrinfo("localhost", puerto, &hints, &cliente)) != 0) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
        return 1;
    }

    for(p = cliente; p != NULL; p = p -> ai_next) {
        if((cd = socket(p -> ai_family, p -> ai_socktype, p -> ai_protocol)) == -1) {
            perror("client: socket");
            continue;
        }

        if(connect(cd, p -> ai_addr, p -> ai_addrlen) == -1) {
                close(cd);
                perror("client: connect");
                continue;
        }

        break;
    }

    if(p == NULL) {
        fprintf(stderr, "client: error al conectar con el servidor\n");
        return 2;
    }

    freeaddrinfo(cliente);

    return cd;
}
