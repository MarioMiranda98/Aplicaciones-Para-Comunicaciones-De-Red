import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfazServidor extends JFrame{
    private static final long serialVersionUID = 2L;
    public InterfazServidor() {
        setBounds(600, 100, 100, 100);
        setTitle("Practica 1: Servidor");
        setResizable(false);

        panelPrincipal = new JPanel();
        servidor = new JLabel("Servidor");
        conectar = new JButton("Al aire");

        conectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                 miServidor = new Servidor(PUERTO);
                 miServidor.conectar();
            }
        });

        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(servidor, BorderLayout.NORTH);
        panelPrincipal.add(conectar, BorderLayout.SOUTH);
        add(panelPrincipal);

        setDefaultCloseOperation(3);
        setVisible(true);
    }

    public static void main(String[] args) {
        new InterfazServidor();
    }

    private JPanel panelPrincipal;
    private JLabel servidor;
    private JButton conectar;
    private Servidor miServidor;
    private final int PUERTO = 9000;
}