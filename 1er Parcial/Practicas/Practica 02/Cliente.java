import java.io.*;
import java.net.*;

public class Cliente {
    public Cliente(int puerto, String host) {
        this.puerto = puerto;
        this.host = host;
    }

    public Producto[] recibirCatalogo() {
        try {
            cliente = new Socket(this.host, this.puerto);
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            dos.writeInt(1);
            dos.flush();
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            misProductos = (Producto[]) ois.readObject();
            ois.close();
            dos.close();

            for(Producto p : misProductos)
                System.out.println(p.getNombre() + "," + p.getExistencias());

            return misProductos;
        } catch(Exception e) { 
            e.printStackTrace(); 
            return null;
        }//try/catch
    }//recibirCatalogo

    private int puerto;
    private String host;
    private Producto[] misProductos;
    private Socket cliente;
}