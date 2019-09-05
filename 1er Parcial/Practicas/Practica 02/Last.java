import java.io.Serializable;
import java.util.ArrayList;

public class Last implements Serializable {
    private static final long serialVersionUID = 8L;
    public Last(int ac, ArrayList<Producto> misProductos) {
        this.ac = ac;
        this.misProductos = misProductos;
    }

    public int getAccion() { return ac; }
    public ArrayList<Producto> getProducto() { return misProductos; }

    public void setAccion(int ac) { this.ac = ac; }
    public void setProducto(ArrayList<Producto> misProductos) { this.misProductos = misProductos; }

    private int ac;
    private ArrayList<Producto> misProductos;
}