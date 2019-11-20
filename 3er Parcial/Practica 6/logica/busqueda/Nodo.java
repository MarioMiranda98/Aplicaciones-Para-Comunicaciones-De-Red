package logica.busqueda;

public class Nodo {

    public Nodo(String id, String ip, int puerto) {
        this.id = id;
        this.ip = ip;
        this.puerto = puerto;
    }

    public Nodo() {}

    public static char getSeparadorFinal() { return separadorFinal; }
    public String getId() { return id; }
    public String getIp() { return ip; }
    public int getPuerto() { return puerto; }

    public void setId(String id) { this.id = id; }
    public void setIp(String ip) { this.ip = ip; }
    public void setPuerto(int puerto) { this.puerto = puerto; }

    public String getCadena() {
        return id + separador + ip + separador + puerto + getSeparadorFinal();
    }

    public static Nodo getNodo(String datos) {
        Nodo nodo = new Nodo();
        char c;
        String id = "";
        String ip = "";
        String p = "";
        int puerto;
        int i = 0;

        while((c = datos.charAt(i)) != separador) {
            i++;
            id +=c;
        }

        nodo.setId(id);
        i++;

        while((c = datos.charAt(i)) != separador) {
            i++;
            ip += c;
        }

        nodo.setIp(ip);
        i++;

        while((c = datos.charAt(i)) != separadorFinal) {
            i++;
            p += c;
        }

        puerto = Integer.parseInt(p);
        nodo.setPuerto(puerto);

        return nodo;
    }

    public void imprimir() {
        System.out.print("Nodo\n");
        System.out.print("Id: " + id + "\n");
        System.out.print("Ip: " + ip + "\n");
        System.out.print("Puerto: " + puerto + "\n");
    }

    private static final char separadorFinal = '$';
    private static final char separador = '"';
    private String id;
    private String ip;
    private int puerto;
    public short temporalizador;
}