import java.net.*;
import java.io.*;

public class Recibe {
    public static void main(String[] args) {
        try {
            final int PUERTO = 9000;
            ServerSocket s = new ServerSocket(PUERTO);
            System.out.println("Servidor listo y esperando");
            for(;;) {
                Socket cl = s.accept();
                DataInputStream no = new DataInputStream(cl.getInputStream());
                DataInputStream  dis = new DataInputStream(cl.getInputStream());
                DataOutputStream dos;
                int t = no.readInt();
                for(int i = 0; i < t; i += 1) {
                   
                    String nombre = dis.readUTF();
                    long tam = dis.readLong();
                    System.out.println("Preparado para recibir el archivo: " + cl.getInetAddress() + ":" + cl.getPort());
                
                    long r = 0;
                    int n = 0;
                    int porcentaje = 0;
                    dos = new DataOutputStream(new FileOutputStream(nombre));
                    
                    while(r < tam) {
                        byte[] b = new byte[2000];
                        n = dis.read(b);
                        r += n;
                        dos.write(b, 0, n);
                        dos.flush();
                        porcentaje = (int) ((r*100) / tam);
                        System.out.println("Se ha recibido " + porcentaje + "%");
                    }
                    dos.close();
                }
                System.out.println("Se completo la descarga");
                no.close();
                dis.close();
                cl.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}