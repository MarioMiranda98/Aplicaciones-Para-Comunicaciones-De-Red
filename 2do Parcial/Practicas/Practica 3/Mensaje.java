import java.io.*;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 3L;
    public Mensaje(String mensaje, String usuarioOrigen, String usuarioDestino, int tipo) {
        this.mensaje = mensaje;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
        this.tipo = tipo;
    }

    public Mensaje(String nombreArchivo, String usuarioOrigen, String usuarioDestino, int tipo, long tamanio, String ruta, int np) {
        this.nombreArchivo = nombreArchivo;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
        this.tamanio = tamanio;
        this.ruta = ruta;
        this.np = np;
        this.tipo = tipo;
    }

    public String getMensaje() { return mensaje; }
    public String getUsuarioOrigen() { return usuarioOrigen; }
    public String getUsuarioDestino() { return usuarioDestino; }
    public int getTipo() { return tipo; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setUsuarioOrigen(String usuarioOrigen) { this.usuarioOrigen = usuarioOrigen; }
    public void setUsuarioDestino(String usuarioDestino) { this.usuarioDestino = usuarioDestino; }
    public void setTipo(int tipo) { this.tipo = tipo; }

    public String getNombre() { return nombreArchivo; }
    public long getTamanio() { return tamanio; }
    public String ruta() { return ruta; }
    public int getNp() { return np; }
    public byte[] getDatos() { return datos; }
    public int getBytesEnviados() { return bytesEnviados; }

    public void setDatos(byte[] datos) { this.datos = datos; }
    public void setBytesEnviados(int bytesEnviados) { this.bytesEnviados = bytesEnviados; }

    private String mensaje;
    private String usuarioOrigen;
    private String usuarioDestino;
    private int tipo;
    private String nombreArchivo;
    private long tamanio;
    private String ruta;
    private int np;
    private byte[] datos;
    private int bytesEnviados;
}