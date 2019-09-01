import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class InterfazServidor extends JFrame {
    private static final long serialVersionUID = 2L;
    public InterfazServidor() {
        setBounds(550, 150, 350, 350);
        setTitle("Practica 2: Servidor");
        setResizable(false);

        //Creacion de componentes
        panelPrincipal = new JPanel();
        cargarProductos = new JButton("Cargar Productos");
        servidor = new JLabel("Servidor");
        tablaProductos = new JTable();
        modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Existencia");

        //Propiedades de los componentes
        panelPrincipal.setLayout(new BorderLayout(5, 5));

        //Colocando a la escucha
        
        //Añadiendo componentes
        panelPrincipal.add(servidor, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panelPrincipal.add(cargarProductos, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }//Constructor

    public static void main(String[] args) {
        new InterfazServidor();
    }//main

    //Bloque de instancias
    private JPanel panelPrincipal;
    private DefaultTableModel modelo;
    private JTable tablaProductos;
    private JButton cargarProductos;
    private JLabel servidor;
}//clase