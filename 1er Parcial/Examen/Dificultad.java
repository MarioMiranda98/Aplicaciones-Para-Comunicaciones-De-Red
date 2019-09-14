import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dificultad extends JFrame{

	//constructor
	public Dificultad (){
		initForm();
		initComponents();
		setVisible(true);
	}//fin constructor Dificultad

	private void initForm(){
		//ventana
		setTitle("Eleccion de dificultad");
		setSize(400,110);
		setResizable(false);
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
	}//termina initForm

	private void initComponents(){
		//creación de componentes
		panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botonPrincipiantes = new JButton("Para Noobs");
		botonNormal= new JButton("Para no tan Noobs");
		botonDificil = new JButton("Para los que no son Noobs");
		mensaje = new JLabel("Elige tu dificultad");

		//accion de boton Principiantes
		botonPrincipiantes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				/*crea un juego de buscaminas de dimencion 9x9 con 10 minas*/
				//new buscaminas(9,9,10);
				miCliente = new Cliente("1");
			}
		});//termina accion boton Principiantes

		//accion de boton Normal
		botonNormal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				/*Crea un juego de buscaminas con dimension 16x16 con 40 minas*/
				//new buscaminas(16,16,40);
				miCliente = new Cliente("2");
			}
		});//termina accion boton Normal

		//accion del boton Dificil
		botonDificil.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				/*Crea juego de buscaminas con dimension 16x30 con 99 minas*/
				//new buscaminas(16,30,99);
				miCliente = new Cliente("3");
			}
		});//termina accion boton Dificil

		//añadir elementos
		panelBotones.add(botonPrincipiantes);
		panelBotones.add(botonNormal);
		panelBotones.add(botonDificil);
		add(panelBotones, BorderLayout.CENTER);

	}//termina initComponentesn

	public static void main(String args[]){
		new Dificultad();
	}

	//declaracion de componentes
	private JPanel panelBotones;
	private JButton botonPrincipiantes;
	private JButton botonNormal;
	private JButton botonDificil;
	private JLabel mensaje;
	private Cliente miCliente;

}//fin clase Dificultad