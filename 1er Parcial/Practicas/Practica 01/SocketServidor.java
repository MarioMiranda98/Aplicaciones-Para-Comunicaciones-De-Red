import java.net.*;
import java.io.*;

public class SocketServidor {
    public static void recibeArchivo(String nombre, DataInputStream dis, long tam) {
        try {
            int porcentaje = 0;
                int r = 0;
                int n = 0;
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

                while(r < tam) {
                    byte[] b = new byte[2000];
                    n = dis.read(b);
                    r += n;
                    dos.write(b, 0, n);
                    dos.flush();
                    porcentaje = (int) ((r*100) / tam);
                    System.out.println("\rSe ha recibido " + porcentaje + "%");
                }    

                dos.close();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public static String directorio(String nombre, String ruta) {
        if(!ruta.equals("")) {
            File carpeta = new File("./ArchivosServidor/" + ruta);
            if(!carpeta.exists()) {
                try {
                    if (carpeta.mkdir())
                        System.out.println("Carpeta creada");
                    else 
                        System.out.println("No se creo la carpeta");
                }catch(SecurityException se) { se.printStackTrace(); }
            }
            nombre = ruta + "//" + nombre;
        }
        return nombre;
    }

    public static void main(String[] args) {
        try {
            servidor = new ServerSocket(9000);
            servidor.setReuseAddress(true);
            System.out.println("Servidor listo y esperando");
            for(;;) {
                cliente = servidor.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());

                String nombre = dis.readUTF();
                long tam = dis.readLong();
                String ruta = dis.readUTF();
                System.out.println("Recibiendo archivo de " + cliente.getInetAddress() + " : " + cliente.getPort());
                
                nombre = directorio(nombre, ruta);
                nombre = "./ArchivosServidor/" + nombre;
                recibeArchivo(nombre, dis, tam);

                dis.close();
                cliente.close();
                System.out.println("Se ha recibido el archivo");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private static ServerSocket servidor;
    private static Socket cliente;
}