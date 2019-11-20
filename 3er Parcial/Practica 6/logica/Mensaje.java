package logica;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 2L;
    
    public Mensaje(int id, String nombreOrigen, String nombreDestino, String mensaje) {
        this.id = id;
        this.nombreOrigen = nombreOrigen;
        this.nombreDestino = nombreDestino;
        this.mensaje = mensaje;
    }

    public Mensaje() {
        this.id = 0;
        this.nombreOrigen = "";
        this.mensaje = "";
        this.nombreDestino = "";
    }

    public String getNombreDestino() { return nombreDestino; }
    public int getId() { return id; }
    public String getNombreOrigen() { return nombreOrigen; }
    public String getMensaje() { return mensaje; }

    public void setNombreDestino(String nombreDestino) { this.nombreDestino = nombreDestino; }
    public void setId(int id) { this.id = id; }
    public void setNombreOrigen(String nombreOrigen) { this.nombreOrigen = nombreOrigen; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public void imprimir() {
        System.out.print("Id mensaje: " + id + "\n");
        System.out.print("Origen: " + nombreOrigen + "\n");
        System.out.print("Destino: " + nombreDestino + "\n");
        System.out.print("Mensaje: " + mensaje + "\n");
    }

    public String getCadenaMensaje() {
        return id + "" + separador + nombreOrigen + separador + nombreDestino + separador + mensaje + separador;
    }

    private int id;
    private String nombreOrigen;
    private String nombreDestino;
    private String mensaje;
    private final char separador = '$';
}