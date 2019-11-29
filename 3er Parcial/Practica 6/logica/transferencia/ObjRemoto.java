package logica.transferencia;

import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.TransferenciaListener;
import java.io.*;

public class ObjRemoto implements Transferencia {
    
    public ObjRemoto(int puertoRuta, TransferenciaListener listener) {
        ruta += (puertoRuta + "/");
        //System.out.println(ruta);
        this.listener = listener;
    }

    public void inicializarDescarga(String name, int totalNodos, int nodo) {
        final File f = new File(ruta + name);
        this.f = f;
        long tamanio = f.length();
        long parte = (tamanio / totalNodos);
        long res =  (tamanio % totalNodos);
        long inicio = nodo * parte;
        tam = parte;
        if(nodo == totalNodos - 1)
            tam += res;
        if(listener != null) {
            listener.cantBytesEnviar(tam);
        }
        try {
            final FileInputStream fis = new FileInputStream(f);
            fis.skip(inicio);
            this.fis = fis;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] transferirArchivo(int buff) throws RemoteException {
        try {
            array = new byte[buff];
            fis.read(array,0,buff);
            if(listener != null) {
                listener.bytesEnviados(buff);
                System.out.print(buff+"\n");
            }
            else {
                System.out.print("listener null\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(EnviarArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return array;
    }

    public long getTamArchivo() throws RemoteException {
        return f.length();
    }

    private File f;
    private FileInputStream fis;
    private byte[] array;
    private long tam;
    String ruta = "Carpetas/";  
    private TransferenciaListener listener;
}
