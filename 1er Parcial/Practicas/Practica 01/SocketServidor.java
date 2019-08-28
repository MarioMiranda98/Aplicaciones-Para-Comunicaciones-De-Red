import java.net.*;
import java.io.*;

public class SocketServidor {
    public static void recibeArchivo(String nombre, DataInputStream dis, long tam) {
        try {
            int porcentaje = 0;
                long r = 0;
                int n = 0;
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                while(r < tam) {
                    byte[] b = new byte[2000];
                    n = dis.read(b);
                    r += n;
                    dos.write(b, 0, n);
                    dos.flush();
                    porcentaje = (int) ((r*100) / tam);
                    System.out.print("\rSe ha recibido " + porcentaje + "%");
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

    public static void enviaArchivoPedido(String nombre, long tam, String ruta) {
        System.out.println("Todo bien");
        try {
            int porcentaje = 0;
            long r = 0;
            int n = 0;
            DataInputStream dis = new DataInputStream(new FileInputStream(ruta));
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());

            dos.writeUTF(nombre);
            dos.flush();
            dos.writeLong(tam);
            dos.flush();
            dos.writeUTF(ruta);
            dos.flush();

            while(r < tam) {
                byte[] b = new byte[2000];
                n = dis.read(b);
                r += n;
                dos.write(b, 0, n);
                dos.flush();
                porcentaje = (int) ((r*100) / tam);
                System.out.print("\rPorcentaje" + porcentaje + "%");
            }
            dos.close();
            dis.close();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        try {
            servidor = new ServerSocket(9000);
            servidor.setReuseAddress(true);
            System.out.println("Servidor listo y esperando");
            for(;;) {
                cliente = servidor.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                int accion = dis.readInt();
                String nombre = dis.readUTF();
                long tam = dis.readLong();
                String ruta = dis.readUTF();
                System.out.println(tam);
                if (accion == 1) {
                    System.out.print("Mandando archivo");
                    enviaArchivoPedido(nombre, tam, ruta);
                } else {
                    System.out.println("Recibiendo archivo de " + cliente.getInetAddress() + " : " + cliente.getPort());
                
                    nombre = directorio(nombre, ruta);
                    nombre = "./ArchivosServidor/" + nombre;
                    recibeArchivo(nombre, dis, tam);
                    System.out.println("\nSe ha recibido el archivo");
                }

                dis.close();
                cliente.close();
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private static ServerSocket servidor;
    private static Socket cliente;
}