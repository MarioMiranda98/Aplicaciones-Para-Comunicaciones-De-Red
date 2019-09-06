import java.io.Serializable;

public class Producto implements Serializable {
    private static final long serialVersionUID = 3L;
    public Producto(int id, String nombre, double precio, int existencias, String descripcion, boolean promocion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.existencias = existencias;
        this.descripcion = descripcion;
        this.promocion = promocion;
        this.imagen = imagen;
        cantidad = 0;
    }

    public int getID() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getExistencias() { return existencias; }
    public String getDescripcion() { return descripcion; }
    public boolean getPromocion() { return promocion; }
    public String getImagen() { return imagen; }
    public int getCantidad() { return cantidad; }

    public void setID(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setExistencias(int existencias) { this.existencias = existencias; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPromocion(boolean promocion) { this.promocion = promocion; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    private int id;
    private String nombre;
    private double precio;
    private int existencias;
    private String descripcion;
    private boolean promocion;
    private String imagen;
    private int cantidad;
}