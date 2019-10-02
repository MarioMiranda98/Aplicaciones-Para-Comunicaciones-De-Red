import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JEditorPane;

public class Cliente {
    public Cliente(String nombre, String host, int puerto, JEditorPane editor) {
        this.nombre = nombre;
        this.host = host;
        this.puerto = puerto;
        this.editor = editor;

        hiloEscucha = new EscuchaMensajes();
        escucha = new Thread(hiloEscucha);
        escucha.start();

        try {
            cliente = new MulticastSocket(puerto);
            grupo = InetAddress.getByName(host);
            cliente.joinGroup(grupo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class EscuchaMensajes implements Runnable {
        public void run() {
            System.out.println("Escuchando Mensajes");
            while (true) {
                DatagramPacket recibido = new DatagramPacket(new byte[6500], 6500);
                try {
                    cliente.receive(recibido);
                    editor.setText("<b>" + new String(recibido.getData(), 0, recibido.getLength()) + "</b>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class EnviaMensajes implements Runnable {
        public void run() {
            // editor.setText("<b>Enviando Mensajes</b>" + (++i));
            String msj = "Hola";
            byte b[] = msj.getBytes();
            DatagramPacket p = new DatagramPacket(b, b.length, grupo, puerto);
            try {
                cliente.send(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void enviar() {
        new Thread(new EnviaMensajes()).start();
    }

    private String nombre;
    private String host;
    private int puerto;
    private JEditorPane editor;
    private Thread escucha;
    private Runnable hiloEscucha;
    private MulticastSocket cliente;
    private InetAddress grupo;
    int i = 0;
}