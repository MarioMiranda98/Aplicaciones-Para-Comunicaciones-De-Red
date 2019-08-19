import java.io.*;
import java.net.*;

public class ServidorO {
    public static void main(String[] args) {
        final int PUERTO = 2500;
        ServerSocket servidor;
        try {
            servidor = new ServerSocket(PUERTO);
            servidor.setReuseAddress(true);
            System.out.println("Servidor iniciado en el puerto " + PUERTO + "\nEsperando Cliente");
            for(;;) {
                Socket cl = servidor.accept();
                ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
                Dato datosRecibidos = (Dato)(ois.readObject());
                System.out.println("Objeto Recibido\nV1: " + datosRecibidos.getV1() + "\tV2: " + datosRecibidos.getV2() + "\tV3: " + datosRecibidos.getV3());

                Dato d2 = new Dato(7, 8.0f, "nueve");                
                System.out.println("Enviando Objeto");
                ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                oos.writeObject(d2);
                oos.flush();
                ois.close();
                oos.close();
                cl.close();
                System.out.println("Sesion Terminada");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
}
