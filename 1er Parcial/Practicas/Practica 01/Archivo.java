public class Archivo {
    public Archivo(String nombre, long tamanio, String path) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.path = path;
    }

    public String getNombre() { return nombre; }
    public long getTamanio() { return tamanio; }
    public String getPath() { return path; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTamanio(long tamanio) { this.tamanio = tamanio; }
    public void setPath(String path) { this.path = path; }

    private String nombre;
    private long tamanio;
    private String path;
}