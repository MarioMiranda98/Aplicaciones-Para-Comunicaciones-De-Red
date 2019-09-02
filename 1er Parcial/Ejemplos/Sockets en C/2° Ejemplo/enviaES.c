#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netinet/in.h>
#define PUERTO "8000"

struct datos {
    char nombre[30];
    char apellido[30];
    short edad;
};

int main(){
    int sd,n,v=1,rv,op=0;
  socklen_t ctam;
  char s[INET6_ADDRSTRLEN], hbuf[NI_MAXHOST], sbuf[NI_MAXSERV];
  FILE *f;
 struct addrinfo hints, *servinfo, *p;
 struct sockaddr_storage their_addr; // connector's address 
 //struct stat st;
 ctam= sizeof(their_addr);
 memset(&hints, 0, sizeof (hints));  //indicio
 hints.ai_family = AF_INET6;    /* Allow IPv4 or IPv6  familia de dir*/
 hints.ai_socktype = SOCK_STREAM;
 hints.ai_flags = AI_PASSIVE; // use my IP
 hints.ai_protocol = 0;          /* Any protocol */
 hints.ai_canonname = NULL;
 hints.ai_addr = NULL;
 hints.ai_next = NULL;

 if ((rv = getaddrinfo(NULL, PUERTO, &hints, &servinfo)) != 0) {
     fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(rv));
     return 1;
 }//if

    for(p = servinfo; p != NULL; p = p->ai_next) {
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
	 printf("Servicio iniciado... esperando cliente\n");
        break;
    }

    freeaddrinfo(servinfo); // all done with this structure

    if (p == NULL)  {
        fprintf(stderr, "servidor: error en bind\n");
        exit(1);
    }
            listen(sd, 5);
            socklen_t clientADDRESSLENGTH;
            struct sockaddr_in clientADDRESS;
	  for(;;){  
	    int cd,enviados=0;
            clientADDRESSLENGTH = sizeof(clientADDRESS);
            cd = accept(sd, (struct sockaddr *)&their_addr, &ctam);
            if (cd == -1) {
              perror("accept");
              exit(1);
             }//if
            if (getnameinfo((struct sockaddr *)&their_addr, sizeof(their_addr), hbuf, sizeof(hbuf), sbuf,sizeof(sbuf), NI_NUMERICHOST | NI_NUMERICSERV) == 0)
               printf("cliente conectado desde %s:%s\n", hbuf,sbuf);
  	   // f1= fdopen(cd,"w+"); 
	    struct linger linger;
            linger.l_onoff = 1;
            linger.l_linger = 30;
            if(setsockopt(cd,SOL_SOCKET, SO_LINGER,(const char *) &linger,sizeof(linger))==-1){
               perror("setsockopt(...,SO_LINGER,...)");
            }//if    
   
     	char b[100];
	memset(b,0,sizeof (b));
	n = read(cd,b,sizeof(b));
	printf("\nSe recibieron %d bytes\n",n);
	if(n<0){
	  perror("Error en funcion read()\n");
	  close(cd);
	  exit(1);
	} else if(n==0){
	  perror("Socket cerrado\n");
	  close(cd);
	  exit(1);
	} else{
	 struct datos *d1;
	 d1 = (struct datos *)b;
	 printf("\nNombre: %s\nApellido: %s\n Edad:%d\n",d1->nombre,d1->apellido,ntohs(d1->edad));
	}//else
	close(cd);

/*	    struct datos *d = (struct datos *)malloc(sizeof(struct datos));
	    char *tmp = (char *)malloc(sizeof(char)*30);
	    size_t tam;
	    memset(tmp,0,sizeof(tmp));
	    printf("\nEscribe un nombre:");
	    n = getline(&tmp,&tam,stdin);
	    strncpy(d->nombre,tmp,strlen(tmp));
	    printf("\nEscribe un apellido:");
	    memset(tmp,0,sizeof(tmp));
	    n = getline(&tmp,&tam,stdin);
	    strncpy(d->apellido,tmp,strlen(tmp));
	    printf("\nEscribe la edad:");
	    int ed;
	    scanf("%d",&ed);
	    fflush(stdin);
	    d->edad = htons(ed);
	    printf("\nEnviendo estructura con %d bytes, datos:\nNombre: %s\nApellido: %s\nEdad: %d\n",(int)(strlen(d->nombre)+strlen(d->nombre)+4),d->nombre,d->apellido,ntohs(d->edad));
	    n = write(cd,(const char *)d,sizeof(struct datos));
	    fflush(f1);
	    printf("\nSe enviaron: %d bytes\n",n);
	    free(d);
	    close(cd);
*/
	}//for   
            close(sd);


    return 0;
}