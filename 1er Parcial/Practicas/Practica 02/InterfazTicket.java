import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class InterfazTicket extends JFrame {
    private static final long serialVersionUID = 11L;
    public InterfazTicket() {
        setTitle("Ticket");
        setBounds(450, 150, 500, 500);
        setResizable(false);

        panelPrincipal = new JPanel();
        panelInferior = new JPanel();
        panelCostoFinal = new JPanel();
        aceptar = new JButton("Aceptar");
        tabla = new JTable();
        costo = new JLabel("Costo Final: $");
        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Articulo");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");

        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelo.setRowCount(0);
                costo.setText("Costo Final: $");
                setVisible(false);
            }
        });

        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelCostoFinal.setLayout(new BorderLayout(3, 3));
        panelInferior.add(aceptar);
        panelCostoFinal.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelCostoFinal.add(costo, BorderLayout.SOUTH);
        panelPrincipal.add(panelCostoFinal, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void crear(Ticket t) { 
        ArrayList <Producto> miProducto = new ArrayList<>();
        miProducto = t.getProductos();
        for(Producto p : miProducto) {
            modelo.addRow(new Object[]{p.getNombre(), p.getCantidad(), p.getPrecio()});
        }

        costo.setText("Costo Final: $" + t.getPrecio());
    }

    private JPanel panelPrincipal;
    private JPanel panelInferior;
    private JPanel panelCostoFinal;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton aceptar;
    private JLabel costo;
}