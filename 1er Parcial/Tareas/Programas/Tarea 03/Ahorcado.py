import socket
import sys
import os

word = ""#palabra recibida
intentos = 0
caracteresQuemados = []
posiciones = []
impresion = []

def llenarLista():
    global impresion
    global word 
    
    for i in word:
        impresion.append("0")
    
    #print(impresion[:])

def dificultad(dificultad):
    try:
        global word
        cliente = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        cliente.sendto(bytes(str(dificultad), "utf-8"), ("localhost", 9999))
        palabraRecibida, addr = cliente.recvfrom(1024)
        word = palabraRecibida.decode("utf-8")
    finally:
        cliente.close()

def establecerIntentos(decision):
    global intentos
    
    if decision == 1:
        intentos = 5
    elif decision == 2:
        intentos = 8
    elif decision == 3:
        intentos = 10
    
def formatoInicial():
    global word
    global intentos

    os.system("clear")

    print("Juego del Ahorcado")

    for i in word:
        if i != " ":
            print("_", end = " ")
        else:
            print(" ", end = " ")
    
    print("     Intentos Restantes: " + str(intentos))

def checarQuemados(caracter):
    global caracteresQuemados
    #print(caracteresQuemados[:])
    return (caracter in caracteresQuemados)


def formato(caracter):
    global word
    global posiciones
    global impresion
    global intentos
    #print(posiciones[:])
    contador = 0
    cont = 0
    j = 0

    os.system("clear")

    print("Juego del Ahorcado")

    for i in word:
        if cont == posiciones[contador] :
            impresion.pop(cont)
            impresion.insert(cont, caracter)
            if contador == len(posiciones) - 1:
                contador -= 1
            contador += 1      
        cont += 1

    #print(impresion[:])
    for i in word:
        if i == impresion[j]:
            print(impresion[j], end = " ")
        else :
            if i != " ":
                print("_", end = " ")
            else:
                print(" ", end = " ")
        j += 1

    posiciones.clear()
    print("     Intentos Restantes: " + str(intentos))

def validar():
    global word
    global impresion

    #print(impresion[:])
    listaFinal = []
    
    for i in impresion:
        if i != "0":
            listaFinal.append(i)
        else:
            listaFinal.append(" ")

    #print(listaFinal[:])
    cadena = "".join(listaFinal)
    #print(cadena)

    j = 0
    bandera = True
    for i in word:
        if i != cadena[j] :
            bandera = False
            break
        j += 1
    
    if bandera:
        print("Felicidades haz ganado")
        sys.exit(0)

def logica():
    global word
    global intentos
    global caracteresQuemados
    global posiciones
    
    #os.system("clear")

    bandera = False
    posicion = 0

    caracter = str(input("Ingresa un caracter > "))
    caracter = caracter.lower()

    if len(caracter) == 0 or len(caracter) > 1 :
        print("Caracter no valido")
        sys.exit(-1)

    if not checarQuemados(caracter):
        for i in word:
            if caracter == i:
                posiciones.append(posicion)
                posicion += 1
                bandera = True
            else :
                posicion += 1
    else:
        bandera = False

    if bandera:
        formato(caracter)
        intentos += 1
        validar()
    
    caracteresQuemados.append(caracter)    
    intentos -= 1
    print("     Intentos Restantes: " + str(intentos))

def main():
    global word
    global intentos
    print("Ahorcado")
    decision = int(input("Introduce la dificultad del juego \n1.- Facil\n2.- Medio\n3.- Dificil\n"))
    dificultad(decision)
    #print(word)
    establecerIntentos(decision)
    print("Comienza el juego")
    print(intentos)
    llenarLista()
    formatoInicial()

    while intentos != 0:
        logica()

    print("Palabra a buscar: " + str(word))

main()