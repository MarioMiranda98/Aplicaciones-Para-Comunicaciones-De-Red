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
        misProductos = new Producto[10];
        productos = new GestionProductosServidor(misProductos);

        //Propiedades de los componentes
        panelPrincipal.setLayout(new BorderLayout(5, 5));

        //Colocando a la escucha
        cargarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                misProductos = productos.generarProductos();
                
                /*for(int i = 0; i < misProductos.length; i += 1)
                    System.out.println(misProductos[i].getID() +" " + misProductos[i].getExistencias() + " " + misProductos[i].getDescripcion() + " " + misProductos[i].getImagen());*/
            }
        });
        
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
    private GestionProductosServidor productos;
    private Producto[] misProductos;
}//clase