package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import logica.AdministradorDeOperacionesDatagrama;
import static logica.AdministradorDeOperacionesDatagrama.BUSCAR;
import static logica.AdministradorDeOperacionesDatagrama.RESPUESTA;
import logica.AdministradorDeOperacionesMulticast;
import static logica.AdministradorDeOperacionesMulticast.FIN_ID;
import static logica.AdministradorDeOperacionesMulticast.INICIO_ID;
import logica.*;
import logica.busqueda.*;
import logica.transferencia.*;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Interfaz extends JFrame implements ActionListener, TransferenciaListener {
    private static final long serialVersionUID = 1L;

    public Interfaz() throws IOException {
        creaInterfaz();
        inicializar();
    }

    private void creaInterfaz() {
        // ------------Parametros del JFrame-----------//
        setTitle("Practica 6");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // --------------Creacion de componentes------------//
        panelPrincipal = new JPanel();
        panelSuperior = new JPanel();
        panelCentral = new JPanel();
        panelInferior = new JPanel();
        progreso = new JProgressBar();
        progresoEnvio = new JProgressBar();
        campoArchivo = new JTextField(50);
        botonBuscar = new JButton("Buscar");
        botonDescargar = new JButton("Descargar");
        areaConexiones = new JEditorPane();
        areaEstado = new JEditorPane();
        areaArchivos = new JScrollPane();
        etiquetaConexiones = new JLabel("Conexiones");
        etiquetaEstado = new JLabel("Estado");
        etiquetaArchivos = new JLabel("Archivo");

        // ---------------Propiedades de los componentes-----------//
        botonDescargar.setEnabled(false);
        areaConexiones.setContentType("text/html");
        areaEstado.setContentType("text/html");
        progreso.setMaximum(100);
        progresoEnvio.setMaximum(100);
        panelPrincipal.setLayout(new BorderLayout(5, 5));
        panelSuperior.setLayout(new GridLayout(2, 3, 5, 5));
        panelCentral.setLayout(new GridLayout(1, 3, 5, 5));
        panelInferior.setLayout(new BoxLayout(this.panelInferior, BoxLayout.Y_AXIS));

        // --------------Añadiendo Componentes----------------//
        panelSuperior.add(campoArchivo);
        panelSuperior.add(botonBuscar);
        panelSuperior.add(botonDescargar);
        panelSuperior.add(etiquetaConexiones);
        panelSuperior.add(etiquetaEstado);
        panelSuperior.add(etiquetaArchivos);
        panelCentral.add(new JScrollPane(areaConexiones));
        panelCentral.add(new JScrollPane(areaEstado));
        panelCentral.add(new JScrollPane(areaArchivos));
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelInferior.add(progreso);
        panelInferior.add(progresoEnvio);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        add(panelPrincipal);

        // ------------Propiedades del Frame------------//
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void inicializar() throws IOException {
        pedirDatos();

        puerto = Integer.parseInt(puertoCad);
        operacionesNodo = new OperacionesNodo(puerto);
        logTemporal = "";
        busqueda = new Busqueda();
        botonBuscar.addActionListener(this);
        botonDescargar.addActionListener(this);
        areaEstado.setEditable(false);
        areaConexiones.setEditable(false);
        nodos = new ArrayList<>();
        ip = InetAddress.getLocalHost().getHostAddress();

        System.out.println(ip);
        nodo = new Nodo(ip + ":" + puerto, ip, puerto);
        adopd = AdministradorDeOperacionesDatagrama.getInstance(puerto);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    cerrar();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        anunciar();
        actualizarListaDeNodosMulticast();
        recibirDatagrama();
        enviarArchivo();
        decrementarServidor();

        System.out.println(puerto);
    }

    public void actionPerformed( ActionEvent e) {
        if(e.getSource().equals(botonBuscar)) {
            if(campoArchivo.getText().length() < 1) {
                JOptionPane.showMessageDialog(Interfaz.this, "Introduce un archivo a buscar");
            } else {
                String nombre = campoArchivo.getText();
                if(operacionesNodo.buscarArchivo(nombre)) {
                    logTemporal = ("Archivo " + nombre + " encontrado<br>");
                    areaEstado.setText(logTemporal);
                    try {
                        agregarArchivoABusqueda(busqueda, operacionesNodo.getChecksum(nombre), nodo);
                    }catch(Exception ex) { ex.printStackTrace(); }
                } else {
                    logTemporal = ("Archivo No Encontrado <br>");
                    areaEstado.setText(logTemporal);
                }

                if(nodos.size() > 0) {
                    busqueda.setId(BUSCAR);
                    busqueda.setNodoOrigen(nodo.getId());
                    busqueda.setNombreArchivo(nombre);

                    try {
                        adopd.enviarMensaje(busqueda, nodoSiguiente.getIp(), nodoSiguiente.getPuerto());
                    } catch(Exception ex) { ex.printStackTrace(); }
                } else {
                    logTemporal += ("No hay mas nodos<br>");
                    areaEstado.setText(logTemporal);
                }
            }
            campoArchivo.setText("");
        }

        if(e.getSource().equals(botonDescargar)) {
            Thread h = new Thread() {
                public void run() {
                    botonDescargar.setEnabled(false);
                    short tam = (short) botones.length;
                    int pos = 0;
                    for(short i = 0; i < tam; i++) {
                        if(botones[i].isSelected()) {
                            pos = i;
                            break;
                        }
                    }

                    int size = busqueda.getArchivos().get(pos).getNodos().size();
                    Thread hilo[] = new Thread[size];

                    for(int i = 0; i <size; i++) {
                        Nodo n = busqueda.getArchivos().get(pos).getNodos().get(i);

                        try {
                            hilo[i] = recibirArchivo(n, size, i);
                        }catch(Exception ex) { ex.printStackTrace(); }

                        hilo[i].start();
                    }

                    boolean noSalir = true;
                    while(noSalir) {
                        boolean noEntro = true;
                        for(int i = 0; i < size; i++) {
                            if(hilo[i].getState() != Thread.State.TERMINATED) {
                                noEntro = false;
                            }
                        }

                        if(noEntro) {
                            noSalir = false;
                        }
                    }

                    if(size > 1) {
                        RecibirArchivo.unirArchivos(size, busqueda.getNombreArchivo(), "./Carpetas/" + puerto + "/");
                    } else {
                        File f = new File("./Carpetas/" + puerto + "/" + "(0)" + busqueda.getNombreArchivo());
                        f.renameTo(new File("./Carpetas/" + puerto + "/" + busqueda.getNombreArchivo()));
                    }

                    botones = null;
                    logTemporal += ("Archivo: " + busqueda.getNombreArchivo() + "transferido<br>");
                    areaEstado.setText(logTemporal);
                    busqueda = new Busqueda();
                    progreso.setValue(0);
                    progresoEnvio.setValue(0);
                }
            };

            h.start();
        }
    }

    private void cerrar() throws IOException {
        if (JOptionPane.showConfirmDialog(Interfaz.this, "Deseas Salir?", "Salir",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            AdministradorDeOperacionesMulticast.getInstance().salirDelAnillo(nodo.getId());
            System.exit(0);
        }
    }

    private void anunciar() {
        Thread hilo = new Thread(){
        public void run() {
            while(true) {
                try {
                    AdministradorDeOperacionesMulticast.getInstance().anunciar(nodo.getId());
                    TimeUnit.SECONDS.sleep(5);
                }catch( Exception e) { e.printStackTrace(); }
            }
        }
        };
        hilo.start();
    }

    private void actualizarListaDeNodosMulticast() {
        Thread hilo = new Thread() {
            public void run() {
                while(true) {
                    try {
                        actualizarMulticast();
                    } catch( Exception e) { e.printStackTrace(); }
                }
            }
        };
        hilo.start();
    }

    private void recibirDatagrama() {
        Thread hilo = new Thread() {
            public void run() {
                while(true) {
                    try {
                        actualizarDatagrama();
                    } catch( Exception e) { e.printStackTrace(); }
                }
            }
        };

        hilo.start();
    }

    private RecibirArchivo recibirArchivo(Nodo n, int numNodos, int nodo) throws NotBoundException, FileNotFoundException, IOException {
        return new RecibirArchivo(n.getIp(), n.getPuerto(), numNodos, nodo, busqueda.getNombreArchivo(), puerto, this);
    }

    private void actualizarDatagrama() throws IOException, Exception {
        Busqueda busqueda = adopd.recibe();
        busqueda.imprimir();
        
        switch (busqueda.getId()) {
            case BUSCAR:
                logTemporal += ("Buscando: " + busqueda.getNombreArchivo() + "<br>");
                areaEstado.setText(logTemporal);
                if (operacionesNodo.buscarArchivo(busqueda.getNombreArchivo())) {
                    String md5 = operacionesNodo.getChecksum(busqueda.getNombreArchivo());
                    logTemporal += ("Archivo: " + busqueda.getNombreArchivo() + " encontrado<br>");
                    logTemporal += ("MD5: " + md5 + "<br>");
                    areaEstado.setText(logTemporal);
                    agregarArchivoABusqueda(busqueda, md5, nodo);
                } 
                else {
                    logTemporal += ("Archivo: " + busqueda.getNombreArchivo() + " NO encontrado<br>");
                    areaEstado.setText(logTemporal);
                }
                
                if (busqueda.getNodoOrigen().equals(nodoSiguiente.getId())) {
                    busqueda.setId(RESPUESTA);
                    adopd.enviarMensaje(busqueda, nodoAnterior.getIp(), nodoAnterior.getPuerto());
                } else {
                    adopd.enviarMensaje(busqueda, nodoSiguiente.getIp(), nodoSiguiente.getPuerto());
                }
                break;
                
            case RESPUESTA:   
                if(busqueda.getArchivos().size() == 0) {
                    logTemporal += ("Archivo: " + busqueda.getNombreArchivo() + " no encontrado "
                            + "en el anillo<br>");
                    areaEstado.setText(logTemporal);
                }
                else {
                    logTemporal+= ("Archivo " + busqueda.getNombreArchivo() + " localizado en el anillo<br>");
                    areaEstado.setText(logTemporal);
                }
                
                if(busqueda.getNodoOrigen().equals(nodo.getId())) {
                    JPanel panel = new JPanel(new GridLayout(5, 1));
                    panel.setBackground(Color.WHITE);
                    this.busqueda = busqueda;
                    //Mostrar opciones de descarga
                    if(busqueda.getArchivos().size() > 0) {
                        botones = new JRadioButton[busqueda.getArchivos().size()];
                        for(int i = 0; i < busqueda.getArchivos().size(); i++) {
                            String informacion = "<html><body>";
                            informacion += busqueda.getNombreArchivo() + "<br>";
                            informacion += "md5: " + busqueda.getArchivos().get(i).getMd5() + "<br>";
                            
                            for(int j = 0; j < busqueda.getArchivos().get(i).getNodos().size(); j++) {
                                informacion += (busqueda.getArchivos().get(i).getNodos().get(j).getId() + "<br>");
                            }
                            informacion += "</body></body>";
                            botones[i] = new JRadioButton(informacion);
                            botones[i].addActionListener(new ActionListener(){
                                public void actionPerformed( ActionEvent ae) {
                                    for(short i = 0; i < botones.length; i++) {
                                        if(ae.getSource().equals(botones[i])) {
                                            botones[i].setSelected(true);      
                                        }
                                        else {
                                            botones[i].setSelected(false);
                                        }
                                    }
                                    botonDescargar.setEnabled(true);
                                }
                            });
                            panel.add(botones[i]);
                        }
                        areaArchivos.setViewportView(panel);
                    }
                }
                else
                    adopd.enviarMensaje(busqueda, nodoAnterior.getIp(), nodoAnterior.getPuerto());   
                break;
        }   
    }

    private void agregarArchivoABusqueda(Busqueda busqueda, String md5, Nodo nodo) {
        int tam = busqueda.getArchivos().size();
        boolean noEncontrado = true;
        if(tam > 0) {
            for(int i = 0; i < tam; i++ ) {
               if(busqueda.getArchivos().get(i).getMd5().equals(md5)) {
                   busqueda.getArchivos().get(i).getNodos().add(nodo);
                   noEncontrado = false;
                   break;
               }
            }
            if(noEncontrado) {
                Archivo a = new Archivo();
                a.setMd5(md5);
                a.getNodos().add(nodo);
                busqueda.getArchivos().add(a);
            }
        }
        else {
            Archivo a = new Archivo();
            a.setMd5(md5);
            a.getNodos().add(nodo);
            busqueda.getArchivos().add(a);
        }
    }

    private void enviarArchivo() {
        Thread hilo = new Thread() {
            public void run() {
                progreso.setValue(0);
                envio();
            }   
        };

        hilo.start();
    }

    private void envio() {
        try {
            EnviarArchivo ea = new EnviarArchivo(puerto, this);
        }catch (Exception e) { e.printStackTrace(); }
    }

    private void decrementarServidor() {
        Thread hilo = new Thread() {
            public void run() {
                while(true) {
                    Nodo n;
                    for(short i = 0; i < nodos.size(); i++) {
                        n = (Nodo) nodos.get(i);
                        n.temporalizador--;
                        if(n.temporalizador < 1) {
                            finalizarNodo(n.getId());
                            logTemporal += (n.getId() + " ha salido con error <br>");
                            areaEstado.setText(logTemporal);
                        }
                    }

                    try{    
                        TimeUnit.SECONDS.sleep(1);
                    }catch( Exception e) { e.printStackTrace(); }
                }
            }
        };

        hilo.start();
    }

    private void actualizarMulticast() throws IOException {
        Mensaje mensaje = AdministradorDeOperacionesMulticast.getInstance().recibe();
        if(mensaje.getNombreOrigen() != null) {
            switch(mensaje.getId()) {
                case INICIO_ID:
                    if(!mensaje.getNombreOrigen().equals(nodo.getId())) {
                        if(inicioNodo(mensaje.getNombreOrigen())) {
                            actualizarListaDeNodos();
                            nodoSiguiente = getNodoSiguiente(puerto);
                            nodoAnterior = getNodoAnterior(puerto);
                        }
                    }
                    break;
                case FIN_ID:
                    finalizarNodo(mensaje.getNombreOrigen());
                    break;
            }
        }
    }

    private void finalizarNodo( String id) {
        if(finNodo(id)) {
            actualizarListaDeNodos();
            if(nodos.size() > 0) {
                nodoSiguiente = getNodoSiguiente(puerto);
                nodoAnterior = getNodoAnterior(puerto);
            }
        }
    }

    private boolean inicioNodo( String id) {
        int tam = nodos.size();
        Nodo n;
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(n.getId().equals(id)) {
                n.temporalizador = 11;
                return false;
            }
        }

        n = new Nodo(id, getIP(id), getPuerto(id));
        n.temporalizador = 11;
        nodos.add(n);
        return true;
    }

    private boolean finNodo( String id) {
        int tam = nodos.size();
        Nodo n;
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(n.getId().equals(id)) {
                nodos.remove(i);
                return true;
            }
        }
        return false;
    }

    private void actualizarListaDeNodos() {
        nodosDisponibles = "";
        int tam = nodos.size();
        Nodo n;
        
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            nodosDisponibles += ("<br>" + n.getId());
        }

        areaConexiones.setText(nodosDisponibles);
    }

    private int getPuerto( String idNodo) {
        String puerto = "";
        char c;
        int i = 0;
        while((c = idNodo.charAt(i)) != ':') {
            i++;
        }

        puerto = idNodo.substring(i + 1);
        return Integer.parseInt(puerto);
    }

    private String getIP( String idNodo) {
        String ip = "";
        char c;
        int i = 0;

        while((c = idNodo.charAt(i)) != ':') {
            ip += c;
            i++;
        }

        return ip;
    }

    private void pedirDatos() {
        puertoCad = JOptionPane.showInputDialog(Interfaz.this, "Introduce el puerto");

        if (puertoCad.equals(""))
            pedirDatos();
        else {
            setTitle("Practica 6: " + puertoCad);
        }
    }

    private Nodo getNodoSiguiente( int idNodoPuerto) {
        Nodo n = null;
        if(esElMayor(idNodoPuerto)) {
            n = getNodoMenor();
        } else {
            int tam = nodos.size();
            int resta = 2147483647;
            int aux, j = 0;

            for(short i = 0; i < tam; i++) {
                n = (Nodo) nodos.get(i);
                aux = n.getPuerto() - idNodoPuerto;

                if(aux < resta && aux > 0) {
                    resta = aux;
                    j = i;
                }
            }

            n = (Nodo) nodos.get(j);
        }

        return n;
    }

    private Nodo getNodoAnterior( int idNodoPuerto) {
        Nodo n = null;
        if(esElMenor(idNodoPuerto)) {
            n = getNodoMayor();
        } else {
            int tam = nodos.size();
            int resta = 2147483647;
            int aux, j = 0;

            for(short i = 0; i < tam; i++) {
                n = (Nodo) nodos.get(i);
                aux = idNodoPuerto - n.getPuerto();
                
                if(aux < resta && aux > 0) {
                    resta = aux;
                    j = i;
                }
            }

            n = (Nodo) nodos.get(j);
        }

        return n;
    }

    private boolean esElMayor( int idNodo) {
        int tam = nodos.size();
        Nodo n;
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(n.getPuerto() > idNodo)
                return false;
        }

        return true;
    }

    private boolean esElMenor( int idNodo) {
        int tam = nodos.size();
        Nodo n;
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(idNodo > n.getPuerto()) 
                return false;
        }

        return true;
    }

    private Nodo getNodoMayor() {
        int tam = nodos.size();
        Nodo n = (Nodo) nodos.get(0);
        int mayor = n.getPuerto();
        int j = 0;

        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(n.getPuerto() > mayor) {
                mayor = n.getPuerto();
                j = i;
            }
        }

        return (Nodo) nodos.get(j);
    }

    private Nodo getNodoMenor() {
        int tam = nodos.size();
        Nodo n = (Nodo) nodos.get(0);
        int menor = n.getPuerto();
        int j = 0;
        for(short i = 0; i < tam; i++) {
            n = (Nodo) nodos.get(i);
            if(n.getPuerto() < menor) {
                menor = n.getPuerto();
                j = i;
            }
        }

        return (Nodo) nodos.get(j);
    }

    public void mensaje(String message) {
        
    }

    public void bytesRecibidos(int bytes) {
        this.bytesRecibidos += bytes;
        this.progreso.setValue(((int) (bytesEnviados * 100 / this.tamArchivoRecepcion)));
    }

    public void cantBytesEnviar(long bytes) {
        tamArchivoEnvio = bytes;
        logTemporal += ("Cantidad de bytes a enviar: " + bytes + "\n");
        areaEstado.setText(logTemporal);
    }

    public void cantBytesRecibir(long bytes) {
        logTemporal += ("Cantidad de bytes a recibir: " + bytes + "\n");
        areaEstado.setText(logTemporal);
        tamArchivoRecepcion = bytes;
    }

    public void bytesEnviados(int bytes) {}

    private JPanel panelPrincipal;
    private JPanel panelSuperior;
    private JPanel panelCentral;
    private JPanel panelInferior;
    private String puertoCad;
    private JProgressBar progreso;
    private JProgressBar progresoEnvio;
    private JTextField campoArchivo;
    private JButton botonBuscar;
    private JButton botonDescargar;
    private JEditorPane areaConexiones;
    private JEditorPane areaEstado;
    private JScrollPane areaArchivos;
    private JLabel etiquetaConexiones;
    private JLabel etiquetaEstado;
    private JLabel etiquetaArchivos;
    
    private int puerto;
    private String ip;
    private Nodo nodo;
    private ArrayList<Nodo> nodos;
    private Nodo nodoSiguiente;
    private Nodo nodoAnterior;
    private String nodosDisponibles;
    private String logTemporal;
    private OperacionesNodo operacionesNodo;
    private AdministradorDeOperacionesDatagrama adopd;
    private Busqueda busqueda;
    private JRadioButton botones[];
    private long tamArchivoEnvio, tamArchivoRecepcion, bytesEnviados, bytesRecibidos;
}