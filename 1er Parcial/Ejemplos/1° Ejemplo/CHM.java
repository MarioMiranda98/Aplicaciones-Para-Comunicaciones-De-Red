import java.net.*;
import java.io.*;

public class CHM {
    public static void main(String[] args) {
        try {
            final int PUERTO = 8000;
            String host = "127.0.0.1";
            Socket cl = new Socket(host, PUERTO);
            System.out.println("Conexion con servidor establecida...\nRecibiendo Informacion");


            DataInputStream dis = new DataInputStream(cl.getInputStream());
            int v1 = dis.readInt();
            float v2 = dis.readFloat();
            String v3 = dis.readUTF();

            System.out.println("Datos Recibidos:\nV1: " + v1 + "\tV2: " + v2 + "\tV3: " + v3);
            dis.close();
            cl.close();
            System.out.println("Termina Programa");
        } catch (Exception e) {
            e.printStackTrace();
        }//catch
    }//main
}//class