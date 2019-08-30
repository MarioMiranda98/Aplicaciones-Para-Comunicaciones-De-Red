import java.io.*;
import java.net.*;

public class Servidor {
    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void conectar() {
        try {
            servidor = new ServerSocket(puerto);
            servidor.setReuseAddress(true);
            System.out.println("Servidor establecido");
            for(;;) {   
                cliente = servidor.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                int accion = dis.readInt();
                String nombre = dis.readUTF();
                long tam = dis.readLong();
                String ruta = dis.readUTF();

                if (accion == 0) {
                    nombre = directorio(nombre, ruta);
                    nombre = "./ArchivosServidor/" + nombre;
                    recibirArchivo(nombre, tam, dis);
                } else if(accion == 1) {
                    enviaArchivoPedido(nombre, tam, ruta);
                }

                cliente.close();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void recibirArchivo(String nombre, long tam, DataInputStream dis) {
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

    public String directorio(String nombre, String ruta) {
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

    public void enviaArchivoPedido(String nombre, long tam, String ruta) {
        //System.out.println("Todo bien");
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
            System.out.println("");
            dos.close();
            dis.close();
        } catch(Exception e) { e.printStackTrace(); }
    }

    private int puerto;
    private ServerSocket servidor;
    private Socket cliente;
}