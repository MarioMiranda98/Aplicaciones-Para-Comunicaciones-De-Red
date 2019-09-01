import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazCliente extends JFrame{
    private static final long serialVersionUID = 1L;
    public InterfazCliente() {
        setBounds(450, 150, 500, 500);
        setTitle("Practica 2: Cliente");
        setResizable(false);

        //Creacion de componentes
        panelPrincipal = new JPanel();
        panelInferior = new JPanel();
        panelSuperior = new JPanel();
        botonAnterior = new JButton("<<");
        botonSiguiente = new JButton(">>");
        agregarCarrito = new JButton("Agregar al carrito");
        verCarrito = new JButton("Ver carrito");
        pedirCatalogo = new JButton("Catalogo");
        producto = new JLabel("");
        cliente = new JLabel("Productos");

        //Propiedades de los componentes
        panelPrincipal.setLayout(new BorderLayout(5, 5));

        //Adicion de los componentes
        panelInferior.add(pedirCatalogo);
        panelInferior.add(agregarCarrito);
        panelInferior.add(verCarrito);
        panelSuperior.add(cliente);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(botonAnterior, BorderLayout.WEST);
        panelPrincipal.add(botonSiguiente, BorderLayout.EAST);
        panelPrincipal.add(producto, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(3);
        setVisible(true);
    }//Constructor

    public static void main(String[] args) {
        new InterfazCliente();
    }//main

    //Bloque de instancias
    private JPanel panelPrincipal;
    private JPanel panelInferior;
    private JPanel panelSuperior;
    private JButton botonSiguiente;
    private JButton botonAnterior;
    private JButton agregarCarrito;
    private JButton verCarrito;
    private JButton pedirCatalogo;
    private JLabel producto;
    private JLabel cliente;
}//Clase 