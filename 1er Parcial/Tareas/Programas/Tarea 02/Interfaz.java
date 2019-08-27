import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class Interfaz extends JFrame {
    private static final long serialVersionUID = 1L;
    public Interfaz() {
        setBounds(450, 150, 500, 300);
        setTitle("Sockets De Datagrama");
        setResizable(false);

        panelPrincipal = new JPanel();
        panelInferior = new JPanel();
        enviar = new JButton("Enviar");
        seleccionar = new JButton("Seleccionar");
        archivos = new JTable();
        misDatos = new ArrayList<>();
        modelo = (DefaultTableModel) archivos.getModel();
        modelo.addColumn("Archivo");

        seleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.requestFocus();
                int r = jf.showOpenDialog(Interfaz.this);
                if(r == JFileChooser.APPROVE_OPTION) {
                    File f = jf.getSelectedFile();
                    misDatos.add(f);
                    modelo.addRow(new Object[]{ f.getName() });
                }     
            }
        });

        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                ClienteDatagrama cliente = new ClienteDatagrama(PUERTO, HOST);
                for (File file : misDatos) {
                    cliente.enviarArchivo(file, "");
                }
                modelo.setRowCount(0);
                misDatos.clear();
            }
        });
        
        panelInferior.add(enviar);
        panelInferior.add(seleccionar);
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(new JScrollPane(archivos), BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Interfaz();
    }

    private JPanel panelPrincipal;
    private JPanel panelInferior;
    private JButton enviar;
    private JButton seleccionar;
    private JTable archivos;
    private DefaultTableModel modelo;
    private ArrayList<File> misDatos;
    private final int PUERTO = 1234;
    private final String HOST = "127.0.0.1";
}