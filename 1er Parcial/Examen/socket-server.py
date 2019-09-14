import socket

vuelta = ""

def dificultad(recibido):
	if recibido == '1':
		return "9X9M10"
	elif recibido == '2':
		return "16X16M40"
	elif recibido == '3':
		return "16X30M99"
	
#se define el objeto socket
#family type, type of socket
#AF_INET TO IPV4 AND SOCK_STREAM TO TCP
s=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
#enlace IP - Puerto
#server in the same machine 
s.bind(("localhost", 1234))
s.listen(5)

while True:
	print("Servidor iniciado...\nEsperando Conexiones")
	#checar si todos ya se conectaron al servidor
	clientsocket, address = s.accept()
	#variable u objeto para guardar el cliente y su direccion IP

	#depuración para saber si se conectó
	print(f"Conectado desde {address}\nConexion establesida!")

	#enviar información al cliente
	#clientsocket.send(bytes("Bienvenido al servidor!","utf-8"))
	recibido = clientsocket.recv(1024)
	print(recibido.decode("utf-8"))
	vuelta = dificultad(recibido.decode("utf-8"))
	print(vuelta)
	clientsocket.send(bytes(str(vuelta) + "\r\n", "utf-8"))
	clientsocket.close()