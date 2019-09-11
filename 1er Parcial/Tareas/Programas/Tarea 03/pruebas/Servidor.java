package pruebas;

import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            String mensaje = "Mundo";
            while (true) {
                DatagramSocket s = new DatagramSocket(9999);
                DatagramPacket paquete = new DatagramPacket(new byte[1024], 1024);
                s.receive(paquete);
                System.out.println(new String(paquete.getData(), 0, paquete.getLength()));

                byte[] b = mensaje.getBytes();
                DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getByName("localhost"), paquete.getPort());
                s.send(packet);
                s.close();
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
}