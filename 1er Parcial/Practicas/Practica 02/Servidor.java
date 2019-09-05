import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Servidor {
    public Servidor(int puerto, Producto[] misProductos) {
        this.puerto = puerto;
        this.misProductos = misProductos;
    }//Constructor

    public void online() throws ClassNotFoundException {
        try {
            servidor = new ServerSocket(this.puerto);
            System.out.println("Servidor Activo");
            for(;;) {
                cliente = servidor.accept();
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                Last l = (Last) ois.readObject();
                int accion = l.getAccion();
                if(accion == 1) {
                    System.out.println("Mandando catalogo");
                    mandarCatalogo();
                } else if(accion == 2) {
                    System.out.println("Procesando Compra");
                    registrarCompra(l);
                }
                ois.close();
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

    private void registrarCompra(Last l) {
            ArrayList<Producto> productoRecibido = l.getProducto();
            for(Producto p : productoRecibido) 
                System.out.println(p.getNombre());
            
            actualizarExistencia(productoRecibido); 
    }//registrarCompra

    private void actualizarExistencia(ArrayList<Producto> productoRecibido) {
        for(Producto p : misProductos)
            p.setExistencias(p.getExistencias() - 1);
        for(Producto p : misProductos)
            System.out.println(p.getExistencias());
    }//actualizarExistencia

    private int puerto;
    private ServerSocket servidor;
    private Socket cliente;
    private Producto[] misProductos;
}//Clase