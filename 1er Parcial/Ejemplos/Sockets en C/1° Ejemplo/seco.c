#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#define PUERTO "9000"

void * get_in_addr(struct sockaddr *sa);
void error(const char *msj);

int main() {
    int sd,cd,n,n1,v=1,rv,op=0;
    socklen_t ctam;
    char a[INET6_ADDRSTRLEN], hbuf[NI_MAXHOST], sbuf[NI_MAXSERV];
    struct addrinfo hints, *servInfo, *p;
    struct sockaddr_storage their_addr;
    ctam = sizeof(their_addr);
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET6;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_PASSIVE;
    hints.ai_protocol = 0;
    hints.ai_canonname = NULL;
    hints.ai_addr = NULL;
    hints.ai_next = NULL;

    if ((rv = getaddrinfo(NULL, PUERTO, &hints, &servInfo)) != 0){
     fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
     return 1;
 }//if

    for(p = servInfo; p != NULL; p = p->ai_next) {
        if ((sd = socket(p->ai_family, p->ai_socktype,p->ai_protocol)) == -1) {
            perror("server: socket");
            continue;
        }

        if (setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &v,sizeof(int)) == -1) {
            perror("setsockopt");
            exit(1);
        }

	if (setsockopt(sd, IPPROTO_IPV6, IPV6_V6ONLY, (void *)&op, sizeof(op)) == -1) {
            perror("setsockopt   no soporta IPv6");
            exit(1);
        }

        if (bind(sd, p->ai_addr, p->ai_addrlen) == -1) {
            close(sd);
            perror("server: bind");
            continue;
        }

        break;
    }

    freeaddrinfo(servInfo); // all done with this structure

    if (p == NULL)  {
        fprintf(stderr, "servidor: error en bind\n");
        exit(1);
    }

   listen(sd,5);
   printf("Servidor listo.. Esperando clientes \n");
  
  for(;;){
  
    ctam = sizeof their_addr;
      cd = accept(sd, (struct sockaddr *)&their_addr, &ctam);
        if (cd == -1) {
            perror("accept");
            continue;
        }
   if (getnameinfo((struct sockaddr *)&their_addr, sizeof(their_addr), hbuf, sizeof(hbuf), sbuf,sizeof(sbuf), NI_NUMERICHOST | NI_NUMERICSERV) == 0)

        printf("cliente conectado desde %s:%s\n", hbuf,sbuf);



    FILE *f = fdopen(cd,"w+");
    char buf[1024];
    for(;;){
       bzero(buf,sizeof(buf));
       n=read(cd,buf,sizeof(buf));
       if(n<0){
          perror("Error de lectura\n");
          close(cd);
          break;
        } else if(n==0){
	 perror("Socket cerrado\n");
         break;
        }//if
     printf("recibido:  %s  longitud:%d \n",buf,(int)strlen(buf));
     //char *tmp = (char *) malloc(strlen(buf));
    if(strstr(buf, "SALIR")!=NULL){
        printf("escribio SALIR\n");
        fclose(f);
        close(cd);
        break;
        } else {
     n1= write(cd,buf,n);
     fflush(f);
      }//else
    }//for
   }//for
  close(sd);
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
