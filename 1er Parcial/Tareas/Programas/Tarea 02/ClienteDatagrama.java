import java.net.*;
import java.io.*;

public class ClienteDatagrama {
   public ClienteDatagrama(int puerto, String host) {
        this.puerto = puerto;
        this.host = host;
   }

    public void enviarArchivo(File file, String destino) {
        try {
            cliente = new DatagramSocket();
            System.out.println("Cliente iniciado en el puerto " + cliente.getLocalPort());
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            long tamanio = file.length();
            long enviado = 0;
            int n = 0;
            int i = 0;
            while (enviado < tamanio) {
                byte[] buf = new byte[datagrama];
                n = dis.read(buf);
                byte[] buf2 = new byte[n];
                System.arraycopy(buf, 0, buf2, 0, n);
                Datos datos = new Datos(file.getName(), n, destino, ++i, buf2);
                enviandoDatagrama(datos, puerto);
                try {
                    Thread.sleep(5000);
                } catch(Exception e) { e.printStackTrace(); }
                enviado += n;
            }
            byte[] b = {0x02};
            Datos d2 = new Datos(file.getName(), b.length, destino, -1, b);
            enviandoDatagrama(d2, puerto);
        } catch(Exception e) {
            e.printStackTrace();
        }//try/catch
    }

    private void enviandoDatagrama(Datos misDatos, int puerto) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(misDatos);
            oos.flush();
            byte[] mensaje = baos.toByteArray();
            DatagramPacket paquete = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName(host), puerto);
            cliente.send(paquete);
        }catch(Exception e) { e.printStackTrace(); }
    }

   private int puerto;
   private String host;
   private int datagrama = 60000;
   private DatagramSocket cliente;
}