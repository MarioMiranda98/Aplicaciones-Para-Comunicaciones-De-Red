import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {
    public Cliente(int puerto, String host) {
        this.puerto = puerto;
        this.host = host;
    }

    public Producto[] recibirCatalogo() {
        try {
            cliente = new Socket(this.host, this.puerto);
            Last l = new Last(1, null);
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.writeObject(l);
            oos.flush();
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            misProductos = (Producto[]) ois.readObject();
            ois.close();
            oos.close();

            for(Producto p : misProductos)
                System.out.println(p.getNombre() + "," + p.getExistencias());

            return misProductos;
        } catch(Exception e) { 
            e.printStackTrace(); 
            return null;
        }//try/catch
    }//recibirCatalogo

    public void hacerCompra(ArrayList<Producto> productosFinal) {
        try {
            cliente = new Socket(this.host, this.puerto);
            Last l = new Last(2, productosFinal);
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());

            oos.writeObject(l);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            Ticket t = (Ticket) ois.readObject();

            //System.out.println(t.getPrecio());

            ois.close();
            oos.close();
            cliente.close();

            intTicket = new InterfazTicket();
            intTicket.crear(t);
            intTicket.setVisible(true);
        } catch(Exception e) { e.printStackTrace(); }
    }//hacerCompra

    private int puerto;
    private String host;
    private Producto[] misProductos;
    private Socket cliente;
    private InterfazTicket intTicket;
}