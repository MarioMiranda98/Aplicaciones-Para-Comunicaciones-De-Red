import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;

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
        tablaCliente = new JTable(new Modelo());
        botonSubir = new JButton("Subir");
        botonBajar = new JButton("Descargar todo");
        elegirArchivo = new JButton("Elegir Archivo");
        elegirArchivoServidor = new JButton("Elegir Archivo");
        elegirCarperta = new JButton("Elegir Carpeta");
        elegirCarpetaServidor = new JButton("Elegir Carpeta");
        raiz = new DefaultMutableTreeNode("./");
        arbol = new JTree(raiz);

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

        panelServidorBotones.add(botonBajar);
        panelServidorBotones.add(elegirArchivoServidor);
        panelServidorBotones.add(elegirCarpetaServidor);
        panelServidor.add(servidor, BorderLayout.NORTH);
        panelServidor.add(arbol, BorderLayout.CENTER);
        panelServidor.add(panelServidorBotones, BorderLayout.SOUTH);

        panelPrincipal.add(panelCliente, BorderLayout.WEST);
        panelPrincipal.add(panelServidor, BorderLayout.EAST);
        add(panelPrincipal);
        //----------------Añadiendo Paneles--------------------//

        //---------------Operaciones del JFrame---------------//
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); 
    }

    private JPanel panelPrincipal;
    private JPanel panelCliente;
    private JPanel panelClienteBotones;
    private JPanel panelServidor;
    private JPanel panelServidorBotones;
    private JLabel cliente;
    private JLabel servidor;
    private JTable tablaCliente;
    private JButton botonSubir;
    private JButton botonBajar;
    private JButton elegirArchivo;
    private JButton elegirArchivoServidor;
    private JButton elegirCarperta;
    private JButton elegirCarpetaServidor;
    private JTree arbol;
    private DefaultMutableTreeNode raiz;
}

@SuppressWarnings("serial")
class Modelo extends AbstractTableModel {
    public int getColumnCount() {
        return 2;
    }

    public int getRowCount() {
        return 0;
    }

    public Object getValueAt(int arg0, int arg1) {
        return null;
    }

    public String getColumnName(int c) {
        return nombres[c++];
    }

    private String[] nombres = {"Archivo", "Enviar"};
}