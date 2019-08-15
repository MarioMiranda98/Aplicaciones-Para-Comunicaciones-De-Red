import java.net.*;
import java.io.*;

public class SECO {
    public static void main(String[] args) {
        try {
            final int PUERTO = 1234;
            ServerSocket s = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO + "\nEsperando Cliente");

            for(;;) {
                Socket cl = s.accept();
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));

                while (true) {
                    String msj = br.readLine();
                    if (msj.compareToIgnoreCase("salir") == 0) {
                        System.out.println("Termina Cliente");
                        br.close();
                        pw.close();
                        cl.close();
                        break;
                    } else {
                        System.out.println("Mensaje Recibido " + msj + "\nDevolviendo Eco");
                        pw.println(msj);
                        pw.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }//try/catch
    }//main
}//class