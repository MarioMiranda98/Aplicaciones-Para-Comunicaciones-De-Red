import java.io.*;
import java.net.*;

public class Servidor {
    public Servidor(int puerto, Producto[] misProductos) {
        this.puerto = puerto;
        this.misProductos = misProductos;
    }//Constructor

    public void online() {
        try {
            servidor = new ServerSocket(this.puerto);
            System.out.println("Servidor Activo");
            for(;;) {
                cliente = servidor.accept();
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                int accion = dis.readInt();
                if(accion == 1) {
                    System.out.println("Mandando catalogo");
                    mandarCatalogo();
                }
                dis.close();
                cliente.close();
            }//for
        } catch (IOException e) { e.printStackTrace(); }
    }//online

    private void mandarCatalogo() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.writeObject(misProductos);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }//try/catch
    }//mandarCatalogo

    private int puerto;
    private ServerSocket servidor;
    private Socket cliente;
    private Producto[] misProductos;
}//Clase