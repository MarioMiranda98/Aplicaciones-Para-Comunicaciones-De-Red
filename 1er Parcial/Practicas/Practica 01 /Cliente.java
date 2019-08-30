import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public Cliente(String host, int puerto){
        this.host = host;
        this.puerto = puerto;
    }

    public void enviarArchivo(ArrayList <Archivo> misArchivos, String destino) {
        try {
            for(Archivo a : misArchivos) {
                File f = a.getFile();
                cliente = new Socket(this.host, this.puerto);
                dis = new DataInputStream(new FileInputStream(f.getAbsoluteFile()));
                dos = new DataOutputStream(cliente.getOutputStream());

                long e = 0;
                int n = 0;
                int porcentaje = 0;

                dos.writeInt(0);
                dos.flush();
                dos.writeUTF(a.getNombre());
                dos.flush();
                dos.writeLong(a.getTamanio());
                dos.flush();
                dos.writeUTF(destino);
                dos.flush();

                while (e < a.getTamanio()) {
                    byte[] b = new byte[2000];
                    n = dis.read(b);
                    e += n;
                    dos.write(b, 0, n);
                    dos.flush();
                    porcentaje = (int) ((e * 100) / a.getTamanio());
                    System.out.print("\rPorcentaje:" + porcentaje + "%");
                }

                System.out.println("Archivo Enviado");
                dos.close();
                dis.close();
                cliente.close();
            }
            misArchivos.clear();
        } catch(Exception e) { e.printStackTrace(); };
    }

    public void carpetas(File carpeta, String destino, ArrayList<Archivo> misArchivos) {
        if(destino.equals(""))
            destino = carpeta.getName();
        else 
            destino = destino + "\\" + carpeta.getName();
        
        for(File archivo : carpeta.listFiles()) {
            if(archivo.isDirectory()) 
                carpetas(carpeta, destino, misArchivos);
            else{
                misArchivos.add(new Archivo(archivo.getName(), archivo.length(), destino, archivo));
            }
        }
        enviarArchivo(misArchivos ,destino);
    }

    public void peticionArchivo(Archivo archivoPedido, String destino) {
        try {
            cliente = new Socket(this.host, this.puerto);
            dos = new DataOutputStream(cliente.getOutputStream());
            dos.writeInt(1);
            dos.flush();
            dos.writeUTF(archivoPedido.getNombre());
            dos.flush();
            dos.writeLong(archivoPedido.getTamanio());
            dos.flush();
            dos.writeUTF(archivoPedido.getPath());
            dos.flush();

            System.out.println(destino);
            dis = new DataInputStream(cliente.getInputStream());
            String nombre = dis.readUTF();
            long tam = dis.readLong();
            String ruta = dis.readUTF();
                     
            long r = 0;
            int n = 0;
            int porcentaje = 0;
            DataOutputStream archivo = new DataOutputStream(new FileOutputStream(destino + "/" + nombre));

            while(r < tam) {
                byte[] b = new byte[2000];
                n = dis.read(b);
                r += n;
                archivo.write(b, 0, n);
                archivo.flush();
                porcentaje = (int) ((r*100) / tam);
                System.out.println("Se ha recibido " + porcentaje + "%");
            }

            System.out.println("Se ha recicibido el archivo");

            archivo.close();
            dos.close();
            dis.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String host;
    private int puerto;
    private Socket cliente;
    private DataInputStream dis;
    private DataOutputStream dos;
}