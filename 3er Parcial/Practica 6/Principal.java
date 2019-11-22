import java.io.IOException;
import interfaz.Interfaz;

public class Principal {
    public static void main(String[] args) {
        try {
            new Interfaz();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}