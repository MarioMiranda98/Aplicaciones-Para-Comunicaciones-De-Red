import java.io.File;

public class Archivo {
    public Archivo(String nombre, long tamanio, String path, File file) {
        this.nombre = nombre;
        this.tamanio = tamanio;
        this.path = path;
        this.file = file;
    }

    public String getNombre() { return nombre; }
    public long getTamanio() { return tamanio; }
    public String getPath() { return path; }
    public File getFile() { return file; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTamanio(long tamanio) { this.tamanio = tamanio; }
    public void setPath(String path) { this.path = path; }
    public void setFile(File file) { this.file = file; }

    private String nombre;
    private long tamanio;
    private String path;
    private File file;
}