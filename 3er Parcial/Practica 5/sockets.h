#ifndef __SOCKETS__
#define __SOCKETS__

#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netdb.h>

int levantarServidor(const char *);
int levantarCliente(const char *);

#endif //__SOCKETS__