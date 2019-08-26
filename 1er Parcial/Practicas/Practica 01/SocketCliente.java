import java.io.*;
import java.net.*;
import java.util.*;

public class SocketCliente {
    public SocketCliente(int puerto, String host, ArrayList<Archivo> misArchivos) {
        this.puerto = puerto;
        this.host = host;
        this.misArchivos = misArchivos;
    }

    public void enviarArchivo(String destino) {
        try {
            for(Archivo a : misArchivos) {
                File file = a.getFile();
                cliente = new Socket(this.host, this.puerto);
                dis = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
                dos = new DataOutputStream(cliente.getOutputStream());
                System.out.println(a.getNombre() + "," + a.getTamanio() + "," + a.getPath());
                long e = 0;
                int n = 0;
                int porcentaje = 0;
                dos.writeUTF(a.getNombre());
                dos.flush();
                dos.writeLong(a.getTamanio());
                dos.flush();
                dos.writeUTF(destino);
                dos.flush();
                while(e < a.getTamanio()) {
                    byte[] b = new byte[2000];
                    n = dis.read();
                    e += n;
                    dos.write(b, 0, n);
                    dos.flush();
                    porcentaje = (int) ((e * 100) / a.getTamanio());
                    System.out.print("\rPorcentaje: " + porcentaje + "%");
                }
                System.out.println("Archivo Enviado");
                dos.close();
                dis.close();
                cliente.close();
            }
            misArchivos.clear();
        }catch(Exception e) { e.printStackTrace(); }
    }

    public void carpetas(File carpeta, String destino) {
        if(destino.equals(""))
            destino = carpeta.getName();
        else 
            destino = destino + "\\" + carpeta.getName();
        
        for(File archivo : carpeta.listFiles()) {
            if(archivo.isDirectory()) 
                carpetas(carpeta, destino);
            else{
                misArchivos.add(new Archivo(archivo.getName(), archivo.length(), destino, archivo));
                enviarArchivo(destino);
            }
        }
    }

    private Socket cliente;
    private int puerto;
    private String host;
    private ArrayList<Archivo> misArchivos;
    private DataInputStream dis;
    private DataOutputStream dos;
}