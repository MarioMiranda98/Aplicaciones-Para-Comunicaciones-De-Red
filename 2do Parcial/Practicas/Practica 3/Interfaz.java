import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 2L;
    public Interfaz(String host, int puerto, String nombre) {
        //-----------------Recibiendo Parametros------------------//
        this.host = host;
        this.puerto = puerto;
        this.nombre = nombre;
        //----------------Creando Interfaz-------------------------//
        setBounds(325, 100, 800, 400);
        setTitle("Practica 3");
        setResizable(false);

        panelPrincipal = new JPanel();
        panelCentral = new JPanel();
        panelInferior = new JPanel();
        panelEmojis = new JPanel();
        panelFunciones = new JPanel();
        editor = new JEditorPane();
        editor.setContentType("text/html");
        usuariosConectados = new JComboBox<>();
        areaMensaje = new JTextField(200);
        botonesEmojis = new JButton[6];
        enviar = new JButton("Enviar");
        archivo = new JButton("Archivo");

        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelCentral.setLayout(new GridLayout(1, 2, 3, 3));
        panelInferior.setLayout(new BoxLayout(this.panelInferior, BoxLayout.Y_AXIS));
        panelFunciones.setLayout(new BoxLayout(this.panelFunciones, BoxLayout.X_AXIS));

        colocarBotones();
        panelCentral.add(editor);
        panelCentral.add(usuariosConectados);
        panelFunciones.add(areaMensaje);
        panelFunciones.add(enviar);
        panelFunciones.add(archivo);
        panelInferior.add(panelEmojis);
        panelInferior.add(panelFunciones);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void colocarBotones() {
        for(int i = 0; i < botonesEmojis.length; i += 1) {
            botonesEmojis[i] = new JButton("" + textoBotonesEmojis[i]);
            panelEmojis.add(botonesEmojis[i]);
        }
    }

    private String host;
    private int puerto;
    private String nombre;
    private JPanel panelPrincipal;
    private JPanel panelCentral;
    private JPanel panelInferior;
    private JPanel panelEmojis;
    private JPanel panelFunciones;
    private JEditorPane editor;
    private JComboBox<String> usuariosConectados;
    private JButton[] botonesEmojis;
    private String[] textoBotonesEmojis = {
        ":)",
        ":(",
        ":'(",
        ":')",
        ":v",
        "<{:v"
    };
    private JTextField areaMensaje;
    private JButton enviar;
    private JButton archivo;
}