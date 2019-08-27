import java.io.Serializable;

public class Datos implements Serializable {
    private static final long serialVersionUID = 4L;
  
    public Datos(String nombre, long tamanio, String ruta, int np, byte[] datos) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.ruta = ruta;
        this.np = np;
        this.datos = datos;
    }

    public String getNombre() { return nombre; }
    public long getTamanio() { return tamanio; }
    public String ruta() { return ruta; }
    public int getNp() { return np; }
    public byte[] getDatos() { return datos; }

    private String nombre;
    private long tamanio;
    private String ruta;
    private int np;
    private byte[] datos;
}