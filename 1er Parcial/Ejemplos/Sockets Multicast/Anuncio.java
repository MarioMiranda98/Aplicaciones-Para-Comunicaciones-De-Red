import java.net.*;
import java.io.*;

public class Anuncio {
    public static void main(String[] args) {
        try {
            MulticastSocket s = new MulticastSocket(7777);
            String dir = "229.1.2.3";
            InetAddress gpo = null;
            try {
                gpo = InetAddress.getByName(dir);
            }catch(UnknownHostException e) { 
                System.err.println("La direccion no es valida");
                s.close();
                System.exit(1);
            }
            s.setReuseAddress(true);
            s.setTimeToLive(255);
            s.joinGroup(gpo);
            System.out.println("Socket unido a la direcccion del grupo " + dir + "\nComienza el envio de anuncios");
            String mensaje = "Un mensaje via multicast";
            byte[] b = mensaje.getBytes();
            DatagramPacket p = new DatagramPacket(b, b.length, gpo, 7778);
            for(;;) {
                System.out.println("Enviando mensaje a la direccion de grupo: " + dir);
                s.send(p);
                try {
                    Thread.sleep(5000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e) { e.printStackTrace(); }
    } 
}