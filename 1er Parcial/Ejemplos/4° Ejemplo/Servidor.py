import socket
import pickle
import javaobj
from Datos import Datos

final = b""
recibido = b""
servidor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
servidor.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
servidor.bind(("localhost", 2500))
servidor.listen(1)

cliente, dir = servidor.accept()
print(dir)
dobj = Datos(0, 0, "")

try :
    while recibido :
        recibido = cliente.recv(1024)
        print("datos recibidos")
        final += int(recibido)
        #drec = pickle.dumps(recibido)
        #cliente.send(drec)

    asd = javaobj.loads(final)

except Exception as e:
    print(e)

finally :
    cliente.close()
    servidor.close()
