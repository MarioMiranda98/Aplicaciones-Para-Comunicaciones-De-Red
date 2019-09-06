import java.io.Serializable;
import java.util.ArrayList;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 9L;
    public Ticket(ArrayList<Producto> productos, double precio) {
        this.productos = productos;
        this.precio = precio;
    }//constructor

    public ArrayList<Producto> getProductos() { return productos; }
    public double getPrecio() { return precio; }

    private ArrayList<Producto> productos;
    private double precio;
}//clase