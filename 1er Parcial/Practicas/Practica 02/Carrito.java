import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;


public class Carrito extends JFrame {
    private static final long serialVersionUID = 4L;  

    public Carrito() {
        setTitle("Carrito");
        setBounds(450, 150, 500, 500);
        setResizable(false);

        //Creacion Componentes
        panelPrincipal = new JPanel();
        panelBotones = new JPanel();
        comprar = new JButton("Comprar");
        regresar = new JButton("Regresar");
        remover = new JButton("Remover");
        costo = new JLabel("Costo Total: $");
        miCarrito = new ArrayList<>();
        tablaProductos = new JTable();
        modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.addColumn("Id");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");

        regresar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            } 
        });

        comprar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                miCliente = new Cliente(PUERTO, HOST);
                miCliente.hacerCompra(miCarrito);
                miCarrito.clear();
                modelo.setRowCount(0);
                costo.setText("Costo Total: $");
                setVisible(false);
            }
        });

        remover.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idFila = Integer.parseInt(JOptionPane.showInputDialog(panelPrincipal, "Eliminar por ID"));
                double precioRestar = (double) modelo.getValueAt(idFila - 1, 3);
                //System.out.println(precioRestar);
                costo.setText("Costo Total: $" + (precio - precioRestar));
                miCarrito.remove(idFila - 1);
                modelo.removeRow(idFila - 1);
            }
        });

        panelPrincipal.setLayout(new BorderLayout(1, 1));

        panelBotones.add(comprar);
        panelBotones.add(regresar);
        panelBotones.add(remover);
        panelPrincipal.add(costo, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(tablaProductos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }//Constructor

    public void crearCarrito(Producto p, int i) {
       modelo.addRow(new Object[]{i, p.getNombre(), p.getCantidad(), (p.getCantidad() * p.getPrecio())});
       calcularPrecio(p);
       miCarrito.add(p);
    }//crearCarrito

    private void calcularPrecio(Producto producto) {
        precio += (producto.getPrecio() * producto.getCantidad());
        costo.setText("Costo Total: $" + precio);
    }


    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JButton comprar;
    private JButton regresar;
    private JButton remover;
    private JLabel costo;
    private double precio = 0;
    private Cliente miCliente;
    private final int PUERTO = 9999;
    private final String HOST = "127.0.0.1";
    private ArrayList<Producto> miCarrito;
    private DefaultTableModel modelo;
    private JTable tablaProductos;
}//clase