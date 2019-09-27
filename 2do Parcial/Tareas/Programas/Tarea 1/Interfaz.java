import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 1L;
    public Interfaz() {
        setBounds(450, 150, 300, 300);
        setTitle("Archivos Multicast");
        setResizable(false);

        panelPrincipal = new JPanel();
        panelBotones = new JPanel();
        seleccionar = new JButton("Seleccionar");
        enviar = new JButton("Enviar");
        texto = new JLabel("Archivos Multicast");
        tabla = new JTable();
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Archivos");
        misArchivos = new ArrayList<>();

        seleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.requestFocus();
                int r = jf.showOpenDialog(Interfaz.this);
                if(r == JFileChooser.APPROVE_OPTION) {
                    File f = jf.getSelectedFile();
                    misArchivos.add(f);
                    modelo.addRow(new Object[] { f.getName() });
                }
            }
        });

        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                miCliente.enviarArchivo();
                modelo.setRowCount(0);
            }
        });

        panelPrincipal.setLayout(new BorderLayout(5, 5));

        panelBotones.add(seleccionar);
        panelBotones.add(enviar);
        texto.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(texto, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(3);
        setVisible(true);

        nombreUsuario = JOptionPane.showInputDialog(Interfaz.this, "Introduce tu nombre");
        carpeta();
    }

    public void carpeta() {
        File carpeta = new File("./" + nombreUsuario);
        if(!carpeta.exists()) {
            try {
                if(carpeta.mkdir()) 
                    System.out.println("Se creo la carpeta");
                else 
                    System.out.println("No se creo la carpeta");
            }catch(SecurityException se) { 
                se.printStackTrace();
            }
        }

        texto.setText(texto.getText() + ":" + nombreUsuario);
    }

    public static void main(String[] args) {
        new Interfaz();
        miCliente = new Cliente(nombreUsuario, misArchivos);
    }

    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JButton seleccionar;
    private JButton enviar;
    private JLabel texto;
    private JTable tabla;
    private DefaultTableModel modelo;
    private static ArrayList<File> misArchivos;
    private static Cliente miCliente;
    private static String nombreUsuario;
}