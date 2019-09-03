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
        panelCentral = new JPanel();
        panelCentralCentral = new JPanel();
        botonAnterior = new JButton("<<");
        botonSiguiente = new JButton(">>");
        agregarCarrito = new JButton("Agregar al carrito");
        agregarCarrito.setVisible(false);
        verCarrito = new JButton("Ver carrito");
        verCarrito.setVisible(false);
        pedirCatalogo = new JButton("Catalogo");
        cliente = new JLabel("Productos");
        imagen = new JLabel("");
        nombre = new JLabel("");
        precio = new JLabel("$");
        existencia = new JLabel("");
        descripcion = new JLabel("");

        //Colocando a la escucha
        pedirCatalogo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedirCatalogo.setVisible(false);
                verCarrito.setVisible(true);
                agregarCarrito.setVisible(true);
                miCliente = new Cliente(PUERTO, HOST);
                misProductos = miCliente.recibirCatalogo();
                botonAnterior.setEnabled(false);
                dibujo(misProductos[0]);
            }
        });

        botonSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(contador == 9)
                    botonSiguiente.setEnabled(false);
                else {
                    dibujo(misProductos[++contador]);
                    botonSiguiente.setEnabled(true);
                    botonAnterior.setEnabled(true);
                }
            }
        });

        botonAnterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (contador > 0) {
                    botonAnterior.setEnabled(true);
                    botonSiguiente.setEnabled(true);
                    dibujo(misProductos[--contador]);
                } else if(contador == 9){
                    botonSiguiente.setEnabled(false);
                } else {
                    botonAnterior.setEnabled(false);
                }
            }
        });

        //Propiedades de los componentes
        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelCentral.setLayout(new BorderLayout(3, 3));
        panelCentralCentral.setLayout(new BoxLayout(this.panelCentralCentral, BoxLayout.Y_AXIS));

        //Adicion de los componentes
        panelCentralCentral.add(nombre);
        panelCentralCentral.add(precio);
        panelCentralCentral.add(existencia);
        panelCentralCentral.add(descripcion);
        panelCentral.add(imagen, BorderLayout.NORTH);
        panelCentral.add(panelCentralCentral, BorderLayout.NORTH);
        panelInferior.add(pedirCatalogo);
        panelInferior.add(agregarCarrito);
        panelInferior.add(verCarrito);
        panelSuperior.add(cliente);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(botonAnterior, BorderLayout.WEST);
        panelPrincipal.add(botonSiguiente, BorderLayout.EAST);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(3);
        setVisible(true);
    }//Constructor

    private void dibujo(Producto producto) {
        imagen.setText(producto.getImagen());
        nombre.setText("Nombre: " + producto.getNombre());
        precio.setText("$" + producto.getPrecio());
        existencia.setText("Existencias: " + producto.getExistencias());
        descripcion.setText("Descripcion: " + producto.getDescripcion());
    }//dibujo

    public static void main(String[] args) {
        new InterfazCliente();
    }//main

    //Bloque de instancias
    private JPanel panelPrincipal;
    private JPanel panelInferior;
    private JPanel panelSuperior;
    private JPanel panelCentral;
    private JPanel panelCentralCentral;
    private JButton botonSiguiente;
    private JButton botonAnterior;
    private JButton agregarCarrito;
    private JButton verCarrito;
    private JButton pedirCatalogo;
    private JLabel cliente;
    private final int PUERTO = 9999;
    private final String HOST = "127.0.0.1";
    private Cliente miCliente;
    private Producto[] misProductos;
    private JLabel imagen, nombre, precio, existencia, descripcion;
    private int contador = 0;
}//Clase 