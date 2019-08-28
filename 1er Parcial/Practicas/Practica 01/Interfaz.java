import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 01L;
    public Interfaz() {
        //-----------------Medidas iniciales------------------//
        setTitle("Practica 1");
        setBounds(300, 100, 900, 600);
        setResizable(false);

        //----------------Creando Componentes-----------------//
        panelPrincipal = new JPanel();
        panelCliente = new JPanel();
        panelServidor = new JPanel();
        panelClienteBotones = new JPanel();
        panelServidorBotones = new JPanel();
        cliente = new JLabel("Cliente");
        servidor = new JLabel("Servidor");
        tablaCliente = new JTable();
        modelo = (DefaultTableModel) tablaCliente.getModel();
        modelo.addColumn("Archivo");
        botonSubir = new JButton("Subir");
        elegirArchivo = new JButton("Elegir Archivo");
        elegirArchivoServidor = new JButton("Elegir Archivo");
        elegirCarperta = new JButton("Elegir Carpeta");
        elegirCarpetaServidor = new JButton("Elegir Carpeta");
        raiz = new DefaultMutableTreeNode("./");
        arbol = new JTree(raiz);
        mArchivos = new ArrayList<>();
        //------------------Propiedades componente------------//
        panelPrincipal.setLayout(new GridLayout(1, 1, 5, 5));
        panelCliente.setLayout(new BorderLayout());
        panelServidor.setLayout(new BorderLayout());


        //----------------Añadiendo Componentes----------------//
        panelClienteBotones.add(botonSubir);
        panelClienteBotones.add(elegirArchivo);
        panelClienteBotones.add(elegirCarperta);
        panelCliente.add(cliente, BorderLayout.NORTH);
        panelCliente.add(new JScrollPane(tablaCliente), BorderLayout.CENTER);
        panelCliente.add(panelClienteBotones, BorderLayout.SOUTH);

        panelServidorBotones.add(elegirArchivoServidor);
        panelServidorBotones.add(elegirCarpetaServidor);
        panelServidor.add(servidor, BorderLayout.NORTH);
        panelServidor.add(arbol, BorderLayout.CENTER);
        panelServidor.add(panelServidorBotones, BorderLayout.SOUTH);

        panelPrincipal.add(panelCliente, BorderLayout.WEST);
        panelPrincipal.add(panelServidor, BorderLayout.EAST);
        add(panelPrincipal);
        //----------------Poniendo a la escucha--------------------//
        elegirArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                eleccionArchivo();
            }
        });

        elegirCarperta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    eleccionCarperta();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        botonSubir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miCliente = new SocketCliente(PUERTO, HOST);
                miCliente.enviarArchivo(mArchivos, "");
                modelo.setRowCount(0);
            }
        });

        //---------------Operaciones del JFrame---------------//
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); 

        elegirArchivoServidor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                eleccionArchivoServidor();
            }
        });
    }

    public void eleccionArchivo() {
        JFileChooser jf = new JFileChooser();
        jf.requestFocus();
        int r = jf.showOpenDialog(Interfaz.this);
        if (r == JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            modelo.addRow(new Object[]{ f.getName()});
            mArchivos.add(new Archivo(f.getName(), f.length(), f.getAbsolutePath()));
        }
    }

    public void eleccionArchivoServidor() {
        JFileChooser jf = new JFileChooser("./ArchivosServidor/");
        jf.requestFocus();
        int r = jf.showOpenDialog(Interfaz.this);
        if (r == JFileChooser.APPROVE_OPTION) {
            File f = jf.getSelectedFile();
            Archivo a = new Archivo(f.getName(), f.length(), f.getAbsolutePath());
            miCliente = new SocketCliente(PUERTO, HOST);
            miCliente.peticionArchivo(a);
        }
    }

    public void eleccionCarperta() throws FileNotFoundException {
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jf.requestFocus();
        int r = jf.showOpenDialog(Interfaz.this);
        if (r == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = jf.getSelectedFile();
            modelo.addRow(new Object[]{"Carpeta: " + archivoSeleccionado.getName()});
            modelo.setRowCount(0);
            miCliente = new SocketCliente(PUERTO, HOST);
            miCliente.carpetas(archivoSeleccionado, "", mArchivos);
        }
    }

    private JPanel panelPrincipal;
    private JPanel panelCliente;
    private JPanel panelClienteBotones;
    private JPanel panelServidor;
    private JPanel panelServidorBotones;
    private JLabel cliente;
    private JLabel servidor;
    private JTable tablaCliente;
    private DefaultTableModel modelo;
    private JButton botonSubir;
    private JButton elegirArchivo;
    private JButton elegirArchivoServidor;
    private JButton elegirCarperta;
    private JButton elegirCarpetaServidor;
    private JTree arbol;
    private DefaultMutableTreeNode raiz;
    private ArrayList <Archivo> mArchivos;
    private final int PUERTO = 9000;
    private final String HOST = "127.0.0.1";
    private SocketCliente miCliente;
}