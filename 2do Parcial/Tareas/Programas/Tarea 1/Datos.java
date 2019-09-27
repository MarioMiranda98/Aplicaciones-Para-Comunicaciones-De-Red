import java.io.Serializable;

public class Datos implements Serializable {
    private static final long serialVersionUID = 4L;
  
    public Datos(String nombre, long tamanio, String ruta, int np) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.ruta = ruta;
        this.np = np;
    }

    public String getNombre() { return nombre; }
    public long getTamanio() { return tamanio; }
    public String ruta() { return ruta; }
    public int getNp() { return np; }
    public byte[] getDatos() { return datos; }
    public int getBytesEnviados() { return bytesEnviados; }

    public void setDatos(byte[] datos) { this.datos = datos; }
    public void setBytesEnviados(int bytesEnviados) { this.bytesEnviados = bytesEnviados; }
    
    private String nombre;
    private long tamanio;
    private String ruta;
    private int np;
    private byte[] datos;
    private int bytesEnviados;
}