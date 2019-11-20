package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 1L;
    public Interfaz() {
        //------------Parametros del JFrame-----------//
        setTitle("Practica 6");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        //--------------Creacion de componentes------------//
        panelPrincipal = new JPanel();
        panelSuperior = new JPanel();
        panelCentral = new JPanel();
        panelBotones = new JPanel();
        miProgreso = new JProgressBar();
        campoArchivo = new JTextField(50);
        botonBuscar = new JButton("Buscar");
        botonDescargar = new JButton("Descargar");
        areaConexiones = new JTextArea();
        areaEstado = new JTextArea();
        etiquetaConexiones = new JLabel("Conexiones");
        etiquetaEstado = new JLabel("Estado");

        //---------------Propiedades de los componentes-----------//
        miProgreso.setMaximum(100);
        campoArchivo.setSize(50, 15);
        panelBotones.setLayout(new BoxLayout(this.panelBotones, BoxLayout.Y_AXIS));
        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelSuperior.setLayout(new GridLayout(2, 2, 5, 5));
        panelCentral.setLayout(new GridLayout(1, 2, 5, 5));

        //--------------Añadiendo Componentes----------------//
        panelSuperior.add(campoArchivo);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonDescargar);
        panelSuperior.add(panelBotones);
        panelSuperior.add(etiquetaConexiones);
        panelSuperior.add(etiquetaEstado);
        panelCentral.add(areaConexiones);
        panelCentral.add(areaEstado);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(miProgreso, BorderLayout.SOUTH);
        add(panelPrincipal);

        //------------Propiedades del Frame------------//
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //------------Inicio--------------//
        pedirDatos();
    }

    private void pedirDatos() {
        puerto = JOptionPane.showInputDialog(Interfaz.this, "Introduce el puerto");

        if(puerto.equals(""))
            pedirDatos();
        else {
            setTitle("Practica 6: " + puerto);
        }
    }

    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JPanel panelBotones;
    private JPanel panelCentral;
    private String puerto;
    private JProgressBar miProgreso;
    private JTextField campoArchivo;
    private JButton botonBuscar;
    private JButton botonDescargar;
    private JTextArea areaConexiones;
    private JTextArea areaEstado;
    private JLabel etiquetaConexiones;
    private JLabel etiquetaEstado;
}