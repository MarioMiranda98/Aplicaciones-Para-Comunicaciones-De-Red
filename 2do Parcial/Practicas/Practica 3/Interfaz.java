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
        setBounds(325, 100, 800, 500);
        setTitle("Practica 3");
        setResizable(false);

        panelPrincipal = new JPanel();
        panelCentral = new JPanel();
        panelInferior = new JPanel();
        panelEmojis = new JPanel();
        panelFunciones = new JPanel();
        panelUsuarios = new JPanel();
        editor = new JEditorPane();
        editor.setContentType("text/html");
        areaMensaje = new JTextArea();
        areaMensaje.setLineWrap(true);
        botonesEmojis = new JButton[textoBotonesEmojis.length];
        enviar = new JButton("Enviar");
        archivo = new JButton("Archivo");
        usuariosConectados = new JLabel("    Usuarios Conectados   ");
        escuchaEmojis = new ManejoEmojis();

        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelCentral.setLayout(new BorderLayout(5, 5));
        panelInferior.setLayout(new BoxLayout(this.panelInferior, BoxLayout.Y_AXIS));
        panelFunciones.setLayout(new BoxLayout(this.panelFunciones, BoxLayout.X_AXIS));
        panelUsuarios.setLayout(new BoxLayout(this.panelUsuarios, BoxLayout.Y_AXIS));

        colocarBotones();
        panelUsuarios.add(usuariosConectados);
        panelCentral.add(new JScrollPane(editor), BorderLayout.CENTER);
        panelCentral.add(new JScrollPane(panelUsuarios, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.EAST);
        panelFunciones.add(new JScrollPane(areaMensaje));
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
            botonesEmojis[i].addActionListener(escuchaEmojis);
            panelEmojis.add(botonesEmojis[i]);
        }
    }

    private class ManejoEmojis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            areaMensaje.append(" " + e.getActionCommand() + " ");
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
    private JPanel panelUsuarios; 
    private JEditorPane editor;
    private JButton[] botonesEmojis;
    private String[] textoBotonesEmojis = {
        ":)",
        ":(",
        ":'(",
        ":')",
        ":v",
        "{:v",
        "<3",
        "°<:{v",
        "7u7",
        "UwU"
    };
    private JTextArea areaMensaje;
    private JButton enviar;
    private JButton archivo;
    private JLabel usuariosConectados;
    private ActionListener escuchaEmojis;
}