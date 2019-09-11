import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

sock.sendto(bytes("Hola", "utf-8"), ("localhost", 9999))

recibido = sock.recv(1024)
print(recibido.decode("utf-8"))
