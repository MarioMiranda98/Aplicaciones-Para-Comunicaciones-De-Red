import java.net.*;
import java.io.*;
import java.util.*;

public class Cliente implements Runnable {
    public Cliente(String nombreUsuario, ArrayList<File> misArchivos) {
        this.nombreUsuario = nombreUsuario;
        this.misArchivos = misArchivos;
        try {
            cliente = new MulticastSocket(9999);
            grupo = InetAddress.getByName("230.1.1.1");
            cliente.joinGroup(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t = new Thread(this);
        t.start();
    }

    public void run() {
        System.out.println("Hola desde el Hilo");
        recibirArchivo();
    }

    public void enviarArchivo() {
        try {
            for(File file : misArchivos) {
                DataInputStream dis = new DataInputStream(new FileInputStream(file));
                long tamanio = dis.available();
                long enviado = 0;
                int n = 0;
                int i = 0;
                while (enviado < tamanio) {
                    Datos datos = new Datos(file.getName(), file.length(), "", ++i);   
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
                    DatagramPacket paqueteEnvio = new DatagramPacket(d, d.length, grupo, 9999);
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
                Datos paqueteFinal = new Datos(file.getName(), file.length(), "", 0);
            
                paqueteFinal.setDatos(bFinal);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);

                oos.writeObject(paqueteFinal);
                oos.flush();

                byte[] mnsj = baos.toByteArray();

                DatagramPacket dp = new DatagramPacket(mnsj,mnsj.length, grupo, 9999);
                cliente.send(dp);

                System.out.println("Archivo Enviado");

                oos.close();
                baos.close();
                //cliente.close();
                dis.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }//try/catch
    }

    private void recibirArchivo() {
        try{
            DatagramPacket recibido = new DatagramPacket(new byte[6500], 6500);
            while(true) {
                cliente.receive(recibido);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(recibido.getData()));
                Datos datos = (Datos) ois.readObject();
                System.out.println("Numero de paquete: " + datos.getNp());
                if(datos.getNp() == 0) {
                    dos = new DataOutputStream(new FileOutputStream("./" + nombreUsuario + "/" + nombre));
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
                Thread.sleep(500);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }

    private ArrayList<File> misArchivos;
    private MulticastSocket cliente;
    private InetAddress grupo;
    private DataOutputStream dos;
    private ArrayList <byte[]> lista = null;
    private String nombre = "";
    private String nombreUsuario;
    private Thread t;
}