package logica;

import logica.busqueda.Busqueda;
import java.io.*;
import java.net.*;

public class AdministradorDeOperacionesDatagrama {

    private Busqueda getBusqueda(String datos) { return Busqueda.getBusqueda(datos); }

    DatagramSocket datagramaSocket = null;

    private AdministradorDeOperacionesDatagrama(int puerto) throws SocketException {
        datagramaSocket = new DatagramSocket(puerto);
    }

    public static AdministradorDeOperacionesDatagrama getInstance(int puerto) throws SocketException {
        if(INSTANCE == null) {
            INSTANCE = new AdministradorDeOperacionesDatagrama(puerto);
        }

        return AdministradorDeOperacionesDatagrama.INSTANCE;
    }

    public Busqueda recibe() throws IOException {
        DatagramPacket p = new DatagramPacket(new byte[TAM], TAM);
        datagramaSocket.receive(p);

        return getBusqueda(new String (p.getData()));
    }

    public void enviarMensaje(Busqueda m, String destino, int puerto) throws IOException {
        byte b[] = m.getCadena().getBytes();
        DatagramPacket p = new DatagramPacket(b, b.length, InetAddress.getByName(destino), puerto);
        datagramaSocket.send(p);
    }

    public static final int BUSCAR = 1;
    public static final int RESPUESTA = 2;
    private final static int TAM = 1024;
    private static AdministradorDeOperacionesDatagrama INSTANCE = null;
}