import java.net.*;
import java.io.*;

public class SHM {
    public static void main(String[] args) {
        try {
            final int PUERTO = 8000;
            ServerSocket s = new ServerSocket(PUERTO);
            System.out.println("Servicio Iniciado...\nEsperando Clientes en el puerto: " + PUERTO);

            for (;;) {
                Socket cl = s.accept();
                System.out.println("Cliente Conectado Donde " + cl.getInetAddress() + " : " + cl.getPort());
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                int v1 = 1;
                float v2 = 2.0f;
                String v3 = "Tres";
                System.out.println("Enviando Datos\nV1: " + v1 + "\tV2: " + v2 + "\tV3: " + v3);
                dos.writeInt(v1);
                dos.flush();
                dos.writeFloat(v2);
                dos.flush();
                dos.writeUTF(v3);
                dos.flush();
                dos.close();
                cl.close();
            }//for
        } catch(Exception e) {
            e.printStackTrace();
        }//try/catch
    }//main
}//class