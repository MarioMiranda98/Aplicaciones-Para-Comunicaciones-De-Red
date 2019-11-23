package logica.transferencia;

import logica.TransferenciaListener;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class EnviarArchivo implements Transferencia {

    public EnviarArchivo(int puertoRuta, TransferenciaListener listener) throws RemoteException, AlreadyBoundException {
        EnviarArchivo env = new EnviarArchivo(puertoRuta, listener, 0); 
        Remote stub = UnicastRemoteObject.exportObject(env, 0);
        Registry registry = LocateRegistry.createRegistry(puertoRuta + 100);

        registry.bind("transferir", stub);
        ruta += (puertoRuta + "/");
        System.out.println(ruta);
        this.listener = listener;
    }

    public EnviarArchivo(int puertoRuta, TransferenciaListener listener, int e) {
        ruta += (puertoRuta + "/");
        this.listener = listener;
        e = 0;
    }

    public void inicializarDescarga(String name, int totalNodos, int nodo) {
        File f = new File(ruta + name);
        this.f = f;
        long tamanio = f.length();
        long parte = (tamanio / totalNodos);
        long res = (tamanio % totalNodos);
        long inicio = nodo * parte;
        tam = parte;

        if(nodo == totalNodos - 1) {
            tam += res;
        }

        if(listener != null) {
            listener.cantBytesEnviar(tam);
        }

        try {
            FileInputStream fis = new FileInputStream(f);
            fis.skip(inicio);
            this.fis = fis;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] transferirArchivo(int buff) throws RemoteException {
        try {
            array = new byte[buff];
            fis.read(array, 0, buff);
            if(listener != null) {
                listener.bytesEnviados(buff);
                System.out.println(buff);
            } else {
                System.out.print("Listener null\n");
            }
        }catch(Exception e) { e.printStackTrace(); }

        return array;
    }

    public long getTamArchivo() throws RemoteException {
        return f.length();
    }

    private File f;
    private FileInputStream fis;
    private byte[] array;
    private long tam;
    private String ruta ="./Carpetas/";
    private TransferenciaListener listener;
}