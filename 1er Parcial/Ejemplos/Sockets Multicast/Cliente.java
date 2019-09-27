import java.net.*;
import java.io.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            MulticastSocket cl = new MulticastSocket(7778);
            cl.setReuseAddress(true);
            InetAddress gpo = InetAddress.getByName("229.1.2.3");
            cl.joinGroup(gpo);
            System.out.println("Servicio iniciado y unido al grupo... \nComienza escucha de mensajes");
            for(;;) {
                DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
                cl.receive(p);
                System.out.println("Datagrama multicast recibido desde " + p.getAddress() + " : " + p.getPort());
                System.out.println("Mensaje: " + new String(p.getData(), 0, p.getLength()));
            }
        }catch(Exception e) { e.printStackTrace(); }
    }
}
