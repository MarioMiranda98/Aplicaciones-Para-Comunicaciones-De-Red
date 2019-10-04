import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 2L;

    public Interfaz(String host, int puerto, String nombre) {
        // -----------------Recibiendo Parametros------------------//
        this.host = host;
        this.puerto = puerto;
        this.nombre = nombre;
        // ----------------Creando Interfaz-------------------------//
        setBounds(325, 100, 800, 500);
        setTitle("Practica 3: " + nombre);
        setResizable(false);

        panelPrincipal = new JPanel();
        panelCentral = new JPanel();
        panelInferior = new JPanel();
        panelEmojis = new JPanel();
        panelFunciones = new JPanel();
        panelUsuarios = new JPanel();
        panelCombo = new JPanel();
        editor = new JEditorPane("text/html", null);
        editor.setEditable(false);
        areaMensaje = new JTextArea();
        areaMensaje.setLineWrap(true);
        botonesEmojis = new JButton[textoBotonesEmojis.length];
        enviar = new JButton("Enviar");
        archivo = new JButton("Archivo");
        desconectar = new JButton("Desconectar");
        seleccion = new JButton("Seleccionar");
        usuariosConectados = new JLabel("    Usuarios Conectados   ");
        escuchaEmojis = new ManejoEmojis();
        usuarioConectado = new JComboBox<>();
        usuarioConectado.addItem("Todos");

        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                miCliente.enviar(new Mensaje("[" + nombre + "]: " + areaMensaje.getText(), nombre, "", 1));
                areaMensaje.setText("");
            }
        });

        archivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.requestFocus();
                int r = jf.showOpenDialog(Interfaz.this);
                if (r == JFileChooser.APPROVE_OPTION) {
                    miCliente.enviarArchivo(jf.getSelectedFile());
                    String mensaje = "El usuario [" + nombre + "] ha compartido un archivo";
                    miCliente.enviar(new Mensaje(mensaje, nombre, "", 1));
                }
            }
        });

        seleccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                miCliente.enviar(new Mensaje("[" + nombre + "]: " + areaMensaje.getText(), nombre,
                (String) usuarioConectado.getSelectedItem(), 4));
                areaMensaje.setText("");
            }
        });

        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelCentral.setLayout(new BorderLayout(5, 5));
        panelInferior.setLayout(new BoxLayout(this.panelInferior, BoxLayout.Y_AXIS));
        panelFunciones.setLayout(new BoxLayout(this.panelFunciones, BoxLayout.X_AXIS));
        panelUsuarios.setLayout(new BorderLayout(5, 5));

        colocarBotones();
        addWindowListener(new CorreCliente());
        panelCombo.add(usuarioConectado);
        panelUsuarios.add(usuariosConectados, BorderLayout.NORTH);
        usuariosConectados.setAlignmentX(SwingConstants.CENTER);
        panelUsuarios.add(panelCombo, BorderLayout.CENTER);
        panelUsuarios.add(seleccion, BorderLayout.SOUTH);
        panelCentral.add(new JScrollPane(editor), BorderLayout.CENTER);
        panelCentral.add(panelUsuarios, BorderLayout.EAST);
        panelFunciones.add(new JScrollPane(areaMensaje));
        panelFunciones.add(enviar);
        panelFunciones.add(archivo);
        panelFunciones.add(desconectar);
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

    private class CorreCliente extends WindowAdapter {
        public void windowOpened(WindowEvent we) {
            System.out.println("Ventana abierta");
            miCliente = new Cliente(nombre, host, puerto, editor, usuarioConectado);
            miCliente.saludo(nombre);
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
    private JButton desconectar;
    public static JComboBox<String> usuarioConectado;
    private JButton seleccion;
    private JLabel usuariosConectados;
    private ActionListener escuchaEmojis;
    private Cliente miCliente;
    private JPanel panelCombo;
}