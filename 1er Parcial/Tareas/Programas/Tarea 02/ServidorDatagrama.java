import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServidorDatagrama {
    public static void main(String[] args) {
        try {
            DataOutputStream dos;
            DatagramSocket s = new DatagramSocket(PUERTO);
            ArrayList <byte[]> lista = null;
            String nombre = "";
            while(true) {
                DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);
                s.receive(paquete);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(paquete.getData()));
                Datos datos = (Datos) ois.readObject();
                System.out.println("Numero de paquete: " + datos.getNp());
                if(datos.getNp() == 0) {
                    dos = new DataOutputStream(new FileOutputStream(nombre));
                    for(int i = 0; i < lista.size(); i++) {
                        dos.write(lista.get(i));
                    }
                    dos.close(); 
                    lista.clear();
                } else if(datos.getNp() == 1) {
                    lista = new ArrayList<>();
                    nombre = datos.getNombre();
                    lista.add(datos.getDatos());
                } else {
                    if(nombre.equals(datos.getNombre()))
                        lista.add(datos.getDatos());
                } 
            }
            //dos.close();
        }catch(Exception e) { e.printStackTrace(); }
    }

    static final int PUERTO = 1234;
    static final String HOST = "127.0.0.1";
    static final int BUFFER = 6500;
}