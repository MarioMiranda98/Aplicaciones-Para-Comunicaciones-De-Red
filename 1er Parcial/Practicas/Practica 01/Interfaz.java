import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 01L;
    public Interfaz() {
        //-----------------Medidas iniciales------------------//
        setTitle("Practica 1");
        setBounds(400, 100, 700, 600);
        setResizable(false);

        //----------------Creando Componentes-----------------//
        panelPrincipal = new JPanel();
        panelCliente = new JPanel();
        panelServidor = new JPanel();
        panelClienteBotones = new JPanel();
        cliente = new JLabel("Cliente");
        servidor = new JLabel("Servidor");
        areaCliente = new JTextArea(50, 50);
        areaServidor = new JTextArea(50, 50);
        botonSubir = new JButton("Subir");
        botonBajar = new JButton("Descargar todo");
        elegirArchivo = new JButton("Elegir Archivo");
        elegirCarperta = new JButton("Elegir Carpeta");

        //------------------Propiedades componente------------//
        panelPrincipal.setLayout(new GridLayout(1, 1, 5, 5));
        panelCliente.setLayout(new BorderLayout());
        panelServidor.setLayout(new BorderLayout());

        //----------------Añadiendo Componentes----------------//
        panelClienteBotones.add(botonSubir);
        panelClienteBotones.add(elegirArchivo);
        panelClienteBotones.add(elegirCarperta);
        panelCliente.add(cliente, BorderLayout.NORTH);
        panelCliente.add(areaCliente, BorderLayout.CENTER);
        panelCliente.add(panelClienteBotones, BorderLayout.SOUTH);

        panelServidor.add(servidor, BorderLayout.NORTH);
        panelServidor.add(areaServidor, BorderLayout.CENTER);
        panelServidor.add(botonBajar, BorderLayout.SOUTH);

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
    private JLabel cliente;
    private JLabel servidor;
    private JTextArea areaCliente;
    private JTextArea areaServidor;
    private JButton botonSubir;
    private JButton botonBajar;
    private JButton elegirArchivo;
    private JButton elegirCarperta;
}