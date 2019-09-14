import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Cliente extends JFrame{
	
	public Cliente (String dificultad){//constructor
		this.dificultad = dificultad;
		initForm();
		initComponents();
		setVisible(true);
	}//fin constructor cliente

	private void initForm(){//ventana

		setTitle("Juego de buscaminas");
		setSize(400,130);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}//fin InitForm

	private void initComponents(){
		//creación de componentes
		panelPrincipal=new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelBotones=new JPanel(new FlowLayout(FlowLayout.TRAILING));
		regresar = new JButton("Volver");
		conectar=new JButton("Conectar");
		ingresaPuerto=new JLabel("Ingresa el puerto del servidor");
		ingresaIP=new JLabel("Ingresa la dirección IP");
		PUERTO=new JTextField(10);
		IP=new JTextField(10);

		//accion del boton conectar
		conectar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				//rescata los valores ingresados por el usuario
				final int PTO=Integer.parseInt(PUERTO.getText());
				final String HOST=IP.getText().toString();

				conectarCliente(HOST,PTO, dificultad);
			}
		});//termina accion boton conectar

		regresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		//agregar los elementos
		panelBotones.add(conectar);
		panelBotones.add(regresar);
		panelPrincipal.add(ingresaIP);
		panelPrincipal.add(IP);
		panelPrincipal.add(ingresaPuerto);
		panelPrincipal.add(PUERTO);
		panelPrincipal.add(panelBotones);
		getContentPane().add(panelPrincipal,BorderLayout.CENTER);
	
	}//termina initComponents

	/*public static void main(String s[]){
		new Cliente();
	}*/

	//Método que conecta con el servidor
	void conectarCliente(final String HOST, final int PTO, String dificultad){
		try{
			Socket cl=new Socket(HOST,PTO);
			System.out.println("Conectado con exito");
			
			//new buscaminas();
			//new Dificultad();

			PrintWriter pw = new PrintWriter(cl.getOutputStream());
			pw.write(dificultad);
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
			String recibido = br.readLine();

			System.out.println(recibido);

			String Sfilas, Scolumnas, Sminas;

			if(recibido.length() == 6) {
				Sfilas = recibido.substring(0, 1);
				Scolumnas = recibido.substring(2, 3);
				Sminas = recibido.substring(4, recibido.length());
			} else {
				Sfilas = recibido.substring(0, 2);
				Scolumnas = recibido.substring(3, 5);
				Sminas = recibido.substring(6, recibido.length());
			}

			System.out.println(Sfilas + "," + Scolumnas + "," + Sminas);

			int filas = Integer.parseInt(Sfilas);
			int columnas = Integer.parseInt(Scolumnas);
			int minas = Integer.parseInt(Sminas);

			new buscaminas(filas, columnas, minas);

			pw.close();
			br.close();
			cl.close();
			setVisible(false);
		}catch (Exception e){ e.printStackTrace();}
	}

	//Declaración de componentes para ser usados
	private JPanel panelPrincipal;
	private JPanel panelBotones;
	private JButton conectar;
	private JLabel ingresaPuerto;
	private JLabel ingresaIP;
	private JTextField PUERTO;
	private JTextField IP;
	private String dificultad;
	private JButton regresar;
}//fin class