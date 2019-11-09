#! /bin/bash
#Compilamos el programa
gcc principal.c cubetas.c MergeSort.c sockets.c -o principal -pthread

#lo ejecutamos
./principal
