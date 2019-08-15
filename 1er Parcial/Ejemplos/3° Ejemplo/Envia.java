import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class Envia {
    public static void main(String[] args) {
        try {
            final int PUERTO = 9000;
            String host = "127.0.0.1";
            Socket cl = new Socket(host, PUERTO);
            System.out.println("Conexion establecida, mostrando caja de dialogo");
            JFileChooser jf = new JFileChooser();
            int r = jf.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = jf.getSelectedFile();
                String nombre = f.getName();
                long tam = f.length();
                String path = f.getAbsolutePath();
                System.out.println("Preparando para enviar archivo" + path + " de " + tam + "bytes");
                long e = 0;
                int n = 0; 
                int porcentaje = 0;
                DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(path));

                while (e < tam) {
                    byte[] b = new byte[2000];
                    n = dis.read(b);
                    e += n;
                    dos.write(b, 0, n);
                    dos.flush();
                    porcentaje = (int) ((e*100)/tam);
                    System.out.print("\rEnviando el " + porcentaje + "%");
                }
                System.out.println("\nArchivo Enviado");
                dis.close();
                dos.close();
                cl.close();
            }
        } catch(Exception e) {  
            e.printStackTrace();
        }
    }
}