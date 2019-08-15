import java.io.*;
import java.net.*;

public class CECO {
    public static void main(String[] args) {
        try {
            final int PUERTO = 1234;
            String localHost = "localhost";
            Socket cl = new Socket(localHost, PUERTO);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br1 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            while(true) {
                System.out.println("Escribe una cadena, <Enter> para enviar, \n<Salir> para terminar");
                String datos = br.readLine();
                if(datos.compareToIgnoreCase("salir") == 0) {
                    pw.println(datos);
                    pw.flush();
                    br.close();
                    br1.close();
                    pw.close();
                    cl.close();
                    System.exit(0);
                } else {
                    pw.println(datos);
                    pw.flush();
                    String eco = br1.readLine();
                    System.out.println("Eco recibido " + eco);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }//try/catch
    }//main
}//class