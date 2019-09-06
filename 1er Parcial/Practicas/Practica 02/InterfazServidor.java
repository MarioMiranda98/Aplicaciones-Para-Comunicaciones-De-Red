import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InterfazServidor extends JFrame {
    private static final long serialVersionUID = 2L;

    public InterfazServidor() {
        setBounds(550, 150, 350, 150);
        setTitle("Practica 2: Servidor");
        setResizable(false);

        // Creacion de componentes
        panelPrincipal = new JPanel();
        panelInferior = new JPanel();
        cargarProductos = new JButton("Cargar Productos");
        online = new JButton("Online");
        online.setVisible(false);
        servidor = new JLabel("Servidor");
        misProductos = new Producto[10];
        productos = new GestionProductosServidor(misProductos);

        // Propiedades de los componentes
        panelPrincipal.setLayout(new BorderLayout(5, 5));

        // Colocando a la escucha
        cargarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                misProductos = productos.generarProductos();
                online.setVisible(true);
                cargarProductos.setVisible(false);
                
                System.out.println("Productos Cargados");
                for(int i = 0; i < misProductos.length; i += 1)
                    System.out.println(misProductos[i].getNombre() +" " +
                    misProductos[i].getExistencias());
            }
        });

        online.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                miServidor = new Servidor(PUERTO, misProductos);
                try {
                    miServidor.online();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        //Añadiendo componentes
        panelInferior.add(cargarProductos);
        panelInferior.add(online);
        panelPrincipal.add(servidor, BorderLayout.NORTH);
        //panelPrincipal.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }//Constructor

    public static void main(String[] args) {
        new InterfazServidor();
    }//main

    //Bloque de instancias
    private JPanel panelPrincipal;
    private JPanel panelInferior;
    private JButton cargarProductos;
    private JButton online;
    private JLabel servidor;
    private GestionProductosServidor productos;
    private Producto[] misProductos;
    private final int PUERTO = 9999;
    private Servidor miServidor;
}//clase