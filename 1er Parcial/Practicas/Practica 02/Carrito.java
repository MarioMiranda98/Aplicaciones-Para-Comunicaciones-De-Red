import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Carrito extends JFrame {
    private static final long serialVersionUID = 4L;  

    public Carrito(ArrayList<Producto> miCarrito) {
        setTitle("Carrito");
        setBounds(450, 150, 400, 500);
        setResizable(false);

        //Creacion Componentes
        panelPrincipal = new JPanel();
        panelBotones = new JPanel();
        panelCentral = new JPanel();
        refrescar = new JButton("Refrescar");
        comprar = new JButton("Comprar");
        regresar = new JButton("Regresar");
        costo = new JLabel("Costo Total: $");
        manejoChecks = new gestionChecks();

        this.miCarrito = miCarrito;

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
                setVisible(false);
            }
        });

        panelPrincipal.setLayout(new BorderLayout(1, 1));
        panelCentral.setLayout(new BoxLayout(this.panelCentral, BoxLayout.Y_AXIS));

        panelBotones.add(refrescar);
        panelBotones.add(comprar);
        panelBotones.add(regresar);
        panelPrincipal.add(costo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }//Constructor

    public void crearCarrito() {
        for(Producto p : miCarrito) {   
            JCheckBox miCheck = new JCheckBox("" + p.getNombre() + " Desc: " + p.getDescripcion(), true);
            panelCentral.add(miCheck);
            miCheck.addActionListener(manejoChecks);
            calcularPrecio(p);
        }
    }//crearCarrito

    private void calcularPrecio(Producto producto) {
        precio += producto.getPrecio();
        costo.setText("Costo Total: $" + precio);
    }

    private class gestionChecks implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Probando Checks");
        }
    }

    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JPanel panelCentral;
    private JButton refrescar;
    private JButton comprar;
    private JButton regresar;
    private JLabel costo;
    private double precio = 0;
    private ActionListener manejoChecks;
    private Cliente miCliente;
    private final int PUERTO = 9999;
    private final String HOST = "127.0.0.1";
    private ArrayList<Producto> miCarrito;
}//clase