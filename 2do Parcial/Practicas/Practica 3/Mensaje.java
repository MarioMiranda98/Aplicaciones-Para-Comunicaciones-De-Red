import java.io.*;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 3L;
    public Mensaje(String mensaje, String usuarioOrigen, String usuarioDestino, int tipo) {
        this.mensaje = mensaje;
        this.usuarioOrigen = usuarioOrigen;
        this.usuarioDestino = usuarioDestino;
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

    private String mensaje;
    private String usuarioOrigen;
    private String usuarioDestino;
    private int tipo;
}