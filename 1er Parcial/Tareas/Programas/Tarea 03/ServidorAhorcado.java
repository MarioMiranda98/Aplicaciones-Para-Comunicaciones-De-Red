import java.net.*;

public class ServidorAhorcado {
    public static void main(String[] args) {
        String palabras [][] = {
            {"perro", "hijo", "coche", "hombre"},
            {"ferrocarril", "autobus", "desgracia", "licantropo"},
            {"anita lava la tina", "ese compa ya esta muerto", "push it to the limit", "adios mundo cruel"}
        };
        try {
            while(true) {
                DatagramSocket servidor = new DatagramSocket(9999);
                DatagramPacket paquete = new DatagramPacket(new byte[1024], 1024);
                servidor.receive(paquete);
                
                int num = Integer.parseInt(new String(paquete.getData(), 0, paquete.getLength()));
                String seleccion = palabras[num - 1][numero()];
                byte[] s = seleccion.getBytes();
                DatagramPacket vuelta = new DatagramPacket(s, s.length, InetAddress.getByName("localhost"), paquete.getPort());
                servidor.send(vuelta);
                servidor.close();
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private static int numero() {
        return (int) (Math.random() * ((3 - 1) + 1) + 1);
    }
}