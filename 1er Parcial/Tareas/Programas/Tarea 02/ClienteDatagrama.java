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
            long tamanio = dis.available();
            long enviado = 0;
            int n = 0;
            int i = 0;
            while (enviado < tamanio) {
                Datos datos = new Datos(file.getName(), file.length(), destino, ++i);   
                ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(baos));
                oos.flush();
                byte[] b = new byte[4000];
                n = dis.read(b);
                byte[] b2 = new byte[n];
                System.arraycopy(b, 0, b2, 0, n);
                datos.setDatos(b2); 
                datos.setBytesEnviados(n);
                oos.writeObject(datos);
                oos.flush();
                byte[] d = baos.toByteArray();
                DatagramPacket paqueteEnvio = new DatagramPacket(d, d.length, InetAddress.getByName(host), puerto);
                cliente.send(paqueteEnvio);
                try {
                    Thread.sleep(500);
                }catch (Exception e) { e.printStackTrace(); }
                System.out.println("Numero paquete:" + i);
                enviado += n;
                oos.close();
                baos.close();            
            }
            byte[] bFinal = {0x02};
            Datos paqueteFinal = new Datos(file.getName(), file.length(), destino, 0);
           
            paqueteFinal.setDatos(bFinal);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(paqueteFinal);
            oos.flush();

            byte[] mnsj = baos.toByteArray();

            DatagramPacket dp = new DatagramPacket(mnsj,mnsj.length,InetAddress.getByName(""),puerto);
            cliente.send(dp);

            System.out.println("Archivo Enviado");
            oos.close();
            baos.close();
            cliente.close();
            dis.close();
        } catch(Exception e) {
            e.printStackTrace();
        }//try/catch
    }

   private int puerto;
   private String host;
   private DatagramSocket cliente;
}