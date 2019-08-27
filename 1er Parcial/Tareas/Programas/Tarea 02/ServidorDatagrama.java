import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorDatagrama {
    public static void main(String[] args) {
        int vuelta = 0;
        String nombre = "";
        try {
            ArrayList <byte[]> lista = null;
            DataOutputStream dos = null;
            DatagramSocket s = new DatagramSocket(PUERTO);

            while(true) {
                DatagramPacket paquete = new DatagramPacket(new byte[BUFFER], BUFFER);
                s.receive(paquete);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(paquete.getData()));
                Datos d = (Datos) ois.readObject();
                
                if(d.getNp() == -1){
                    vuelta = 0;
                    System.out.println("Archivo terminado de recibir");
                    dos = new DataOutputStream(new FileOutputStream(d.getNombre()));
                    for(int i = 0; i < lista.size(); ++i)
                        dos.write(lista.get(i));
                    dos.close();
                }

                if(vuelta == 0){
                    lista = new ArrayList<>();
                    nombre = d.getNombre();
                    lista.add(d.getDatos());
                    vuelta += 1;
                    
                }else{
                    if(nombre.equals(d.getNombre())){
                        lista.add(d.getDatos());   
                    }                        
                }
            }
        }catch(Exception e) { e.printStackTrace(); }
    }

    static final int PUERTO = 1234;
    static final String HOST = "127.0.0.1";
    static final int BUFFER = 65535;
}