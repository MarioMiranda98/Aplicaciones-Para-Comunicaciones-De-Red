package logica.transferencia;

import logica.TransferenciaListener;
import java.io.*;
import java.rmi.*;

public class RecibirArchivo extends Thread {
    
    public RecibirArchivo(String ip, int puerto, int totalNodos, int nodo, String nombreArchivo, 
                            int carpeta, TransferenciaListener listener)
                          throws RemoteException, NotBoundException, FileNotFoundException, IOException {
        this.ip = ip;
        this.puerto = puerto + 100;
        this.totalNodos = totalNodos;
        this.nodo = nodo;
        this.nombreArchivo = nombreArchivo;
        ruta = (carpeta + "/");
        this.listener = listener;
    }

    public static void unirArchivos(int totalNodos, String nombreArchivo, String ruta) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(ruta + nombreArchivo);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
            byte[] array = new byte[TAM];
            
            for(int i = 0; i < totalNodos; i++) {
                String nombre = ruta + "(" + i + ")" + nombreArchivo;
                FileInputStream fileInput = new FileInputStream(nombre);
                BufferedInputStream bufferedInput =  new BufferedInputStream(fileInput);

                int leidos = bufferedInput.read(array);
                while(leidos > 0) {
                    bufferedOutput.write(array, 0, leidos);
                    leidos = bufferedInput.read(array);
                }

                bufferedInput.close();
                File f = new File(nombre);
                f.delete();
            }

            bufferedOutput.close();
        }catch(Exception e) { e.printStackTrace(); }
    }

    public void run() {
        String nombreCopia = "./Carpetas/" + ruta + "(" + nodo + ")" + nombreArchivo;
        try {
            final Transferencia lookUp = (Transferencia) Naming.lookup("//" + ip + ":" + puerto + "/transferir");
            lookUp.inicializarDescarga(nombreArchivo, totalNodos, nodo);
            long tam = lookUp.getTamArchivo();
            
            if(listener != null) {
                listener.cantBytesRecibir(tam);
            }

            long cant = tam / totalNodos;
            if(totalNodos - 1 == nodo) {
                cant += tam % totalNodos;
            }

            FileOutputStream fileOutput = new FileOutputStream(nombreCopia);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);

            int buff = 1024;
            while(cant > 0) {
                if(cant < buff) {
                    buff = (int) cant;
                }

                array = lookUp.transferirArchivo(buff);
                bufferedOutput.write(array, 0, buff);

                if(listener != null) {
                    System.out.println("Hilo: " + nodo + "\n");
                    listener.bytesRecibidos(buff);
                }

                cant -= buff;
            }

            bufferedOutput.close();
            if(listener != null) {
                listener.mensaje("Finalizada la recepcion de " + ip + ":" + puerto);
            }
        }catch(Exception e) { e.printStackTrace(); }
    }

    private byte[] array;
    private String ip;
    private int puerto;
    private int totalNodos;
    private int nodo;
    private String nombreArchivo;
    private static final int TAM = 10000;
    private String ruta = "./Carpetas/";
    private TransferenciaListener listener;
}