import java.net.*;
import java.io.*;
import java.lang.instrument.Instrumentation;

public class ClienteO {
    public static void main(String[] args) {
        try {
            String host = "localhost";
            Instrumentation inst;
            final int PUERTO = 2500;
            Socket cl = new Socket(host, PUERTO);
            System.out.println("Conexion establecida, comienza intercambio de objetos");
            ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
            Dato d1 = new Dato(1, 2.0f, "tres");
            System.out.println("Enviando Objetos con los datos\nV1: " + d1.getV1() + "\tV2: " + d1.getV2() + "\tV3: " + d1.getV3());
            oos.writeObject(d1);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
            Dato d2 = (Dato)(ois.readObject());
            System.out.println("Se recibio un paquete con los datos\nV1: " + d2.getV1() + "\tV2: " + d2.getV2() + "\tV3: " + d2.getV3());
            ois.close();
            oos.close();
            cl.close();
            System.out.println("Termina Programa");
        } catch(Exception e) { e.printStackTrace(); }
    }
}