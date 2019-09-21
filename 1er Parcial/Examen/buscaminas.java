import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class buscaminas extends JFrame /*implements ActionListener */{
	//	Atributos
	Botones botones [][];
	int matrizMinas [][];
    //	Cajas de texto
    JTextField txtMinas=new JTextField(3);
    JTextField txtTiempo=new JTextField(3);
    //	Etiquetas
    JLabel lMinas=new JLabel("Minas restantes:");
    JLabel lTiempo=new JLabel("Tiempo transcurrido:");
    
	//	Imagenes de minas
	ImageIcon imagenesMinas []=new ImageIcon [12];
	ImageIcon imagenBandera; 
	//	Dimencion
	int filas,columnas;
	int totalMinas;
	int totalBanderas;
	int casillas;
	//int i, j;
	
	//	Clase del tiempo
	Tiempo tp;
	
	buscaminas (int filas, int columnas, int totalMinas){
		totalBanderas = totalMinas;
		botones=new Botones [filas][columnas];
		matrizMinas=new int [filas][columnas];
		casillas = (filas * columnas) - totalMinas;
		//	Cargar Imágenes
		for(int i=0;i<12;i++)
			imagenesMinas[i]=new ImageIcon(i+".jpg");
            imagenBandera = new ImageIcon("gay.jpeg");
		//	Panel Superior
		JPanel panelSup=new JPanel();
		panelSup.add(lMinas);
		panelSup.add(txtMinas);
		panelSup.add(lTiempo);
		panelSup.add(txtTiempo);
		add(panelSup,"North");
		txtMinas.setEditable(false);
		txtTiempo.setEditable(false);
		txtMinas.setText(Integer.toString(casillas));
		
		//	Panel de los botones
		JPanel panelMedio=new JPanel(new GridLayout(filas,columnas));
		//	Crear y colocar botones
		for(int i=0;i<filas;i++)
			for(int j=0;j<columnas;j++)
				{
					//	Crear boton
					botones [i][j]=new Botones();
					botones[i][j].setDestapado(false);
					//	Colocar en el panel
					panelMedio.add(botones[i][j]);
					//	Action Listener
					botones[i][j].addMouseListener(new MouseListener(){
						@Override
       					public void mouseReleased(MouseEvent e) {}

        				@Override
						public void mousePressed(MouseEvent e) {}

        				@Override
        				public void mouseExited(MouseEvent e) {}

        				@Override
       				 	public void mouseEntered(MouseEvent e) {}
       				 	
						@Override
						public void mouseClicked(MouseEvent ae){
							if(ae.getButton() == MouseEvent.BUTTON1) {
							for(int i=0;i<filas;i++)
								for(int j=0;j<columnas;j++)
								{
									if(ae.getSource()==botones[i][j] && botones[i][j].getIcon()==null && botones[i][j].getBackground()!=Color.WHITE && botones[i][j].getDestapado() == false)
									{
										botones[i][j].setBackground(Color.WHITE);
    					                if(matrizMinas[i][j]==1){
    					                    boom(filas,columnas, totalMinas);
    					                }
    					                else
    					                {
											int v = pulsarVacio(filas,columnas,totalMinas,i,j);
											if(v == 0)
												destapa(i, j, filas, columnas);
											else {
												botones[i][j].setDestapado(true);
												casillas -= 1;
												txtMinas.setText(Integer.toString(casillas));
												int cerca = minasCerca(filas,columnas,i,j);
       		 									botones[i][j].setText(Integer.toString(cerca)); //Cuantas Minas cerca
												//System.out.println("" + casillas);
												if(casillas==0)
													ganar(filas, columnas,totalMinas);
											}
										}
									}	
	
								}

							} else if(ae.getButton() == MouseEvent.BUTTON3) {
								for(int i=0;i<filas;i++)
								for(int j=0;j<columnas;j++)
								{
								if(ae.getSource()==botones[i][j] && botones[i][j].getBackground()!=Color.WHITE)
									{
											colocarBandera(i,j);
								}
								}
							}
						}
					});

				}
		this.add(panelMedio,"Center");	
		colocarMinas(filas,columnas,totalMinas);
        //	Propiedades de la ventana
        
        //	Comenzar Tiempo
        tp= new Tiempo(this);
        tp.start();
        
	    setTitle("Buscaminas");	
	    setResizable(true);
	    setSize(900,600);
		setVisible(true);
	}

	void destapa(int i, int j, int filas, int columnas) {
		int cerca = minasCerca(filas,columnas,i,j);

        if(botones[i][j].getDestapado() || matrizMinas[i][j] == 1 || botones[i][j].getIcon() != null) {
			//System.out.println(botones[i][j].getDestapado() + "," + i + "," + j + " Casillas: " + casillas);
			return;
		}
		
		casillas -= 1;
		//System.out.println(i + "," + j + " Casillas: " + casillas);
		txtMinas.setText(Integer.toString(casillas));
		botones[i][j].setDestapado(true);
		botones[i][j].setBackground(Color.WHITE);
		botones[i][j].setText(Integer.toString(cerca)); //Cuantas Minas cerca

		if(casillas==0)
			ganar(filas, columnas,totalMinas);

		if (cerca > 0) {
			return;
		}

		if (i > 0) {
			destapa(i - 1, j, filas, columnas);
        }
        if (j > 0) {
			destapa(i, j - 1, filas, columnas);
        }
        if (j != columnas - 1) {
			destapa(i, j + 1, filas, columnas);
		}
        if (i != filas - 1) {
            destapa(i + 1, j, filas, columnas);
        }
        if (i > 0 && j > 0) {
			destapa(i - 1, j - 1, filas, columnas);
        }
        if (j != columnas - 1 && i != filas - 1) {
			destapa(i + 1, j + 1, filas, columnas);
        }
        if (j != columnas - 1 && i > 0) {
			destapa(i - 1, j + 1, filas, columnas);
        }
        if (i != filas - 1 && j > 0) {
			destapa(i + 1, j - 1, filas, columnas);
        }
    }

	void colocarMinas(int filas, int columnas, int minas)
	{
		System.out.println("Colocando Minas... \n");
		for(int i=0;i<minas;i++)
		{
		//	Coordenadas
		int x,y=0;
		double x1,y1=0;
		
		/*	Leyenda de matrizMinas
		 *	1 Existe Mina
		 *	0 No existe Mina
		 */
			//Colocar mina aleatoria
			do
			{
             //Generar posiciones aleatorias
             x1=Math.random()*filas;
		 	 y1=Math.random()*columnas;
		 	 x=(int)x1;
		 	 y=(int)y1;	
			}
			while(matrizMinas[x][y]!=0);
			matrizMinas[x][y]=1; //	Poner mina
		}
      //	Visualizar Tablero de minas.
	  for(int i=0;i<filas;i++)
	  {
	  	System.out.println("");
	  	for(int j=0;j<columnas;j++)
	  		System.out.print(" "+matrizMinas[i][j]);
	  }

	}
	
	/*public static void main(String []args){
		new buscaminas(9,9,10);
	}*/

	void colocarBandera(int i, int j) 
	{
		if (botones[i][j].getIcon() != null || totalBanderas == 0 || totalBanderas < 10) { 
			totalBanderas++;
			botones[i][j].setIcon(null);
		} else if (botones[i][j].getIcon() == null || totalBanderas > 0) {
			botones[i][j].setIcon(imagenBandera);
			totalBanderas--;
		}
	}

	int pulsarVacio(int filas, int columnas,int totalMinas, int i, int j)
	{
		//	Al pulsar en una zona vaciá
		//casillas -= 1;
		txtMinas.setText(Integer.toString(casillas));
		int cerca = minasCerca(filas,columnas,i,j);
        botones[i][j].setText(Integer.toString(cerca)); //Cuantas Minas cerca
        if(casillas==0)
			ganar(filas, columnas,totalMinas);
		return cerca;
	}
	
	void volverEmpezar(int filas, int columnas,int totalMinas)
	{
		//	Volver al estado inicial
		for(int i=0;i<filas;i++)
			for(int j=0;j<columnas;j++)
			{
				matrizMinas[i][j]=0;
				botones[i][j].setText("");
				botones[i][j].setBackground(null);
				botones[i][j].setIcon(null);
			}
		colocarMinas(filas, columnas, totalMinas);
		casillas=filas*columnas-totalMinas;
		txtMinas.setText(Integer.toString(casillas));
		tp= new Tiempo(this);
        tp.start();
	}
	
	void ganar(int filas, int columnas, int totalMinas)
	{
		//	Al ganar la partida
		tp.stop(); //	parar el tiempo
		//tp.interrupt();
		JOptionPane.showMessageDialog(this,"Has ganado. Tu tiempo es de: "+txtTiempo.getText());
		setVisible(false);
		//volverEmpezar(filas, columnas, totalMinas);	
	}
	
	void boom(int filas, int columnas, int totalMinas)
	{
	//	Al perder la partida
	tp.stop(); //	parar el tiempo
	//tp.interrupt();
	for(int i=0;i<filas;i++)
		for(int j=0;j<columnas;j++)
		{
		if(matrizMinas[i][j]==1)
			{
			//	Imagen aleatoria de las minas
			double y1=Math.random()*12;
			int y=(int)y1;	
			botones[i][j].setIcon(imagenesMinas[y]);
			}
		}
		JOptionPane.showMessageDialog(this,"Boom!!! Has perdido.");
		setVisible(false);
		//volverEmpezar(filas,columnas,totalMinas);
	}
	
	int minasCerca(int filas, int columnas, int x,int y)
	{
			/*	
			 *	x Coordenada filas
			 *	y Coordenada columnas
			 *
			 *	numeroMinas: devuelve el número de minas de la posición
			 */
			 
            int numeroMinas=0;
            for(int i=y-1;i<=y+1;i++){
            	//En horizontal
                if(i>-1 && i<filas){
	                if(matrizMinas[x][i]==1){
	             		numeroMinas++;
	                }
                }
            }
            //	En vertical
                for(int j=x-1;j<=x+1;j++){
	                		if(j>-1 && j<columnas)
	                			if(matrizMinas[j][y]==1){
	                			numeroMinas++;
	                			}
	                	}
	        //	En diagonal
	        //	Posición de la esquina superior izquierda
	        int x1=x;
	        int y1=y;
	        x1--;
	        y1--;
	        	for(int i=x1;i<x1+3;i++)
	        		{
	        			if(i>-1 && i<filas && y1>-1 && y1<columnas)
	        			 if(matrizMinas[i][y1]==1){
	             		numeroMinas++;
	                	}
	                	y1++;
	        		}
	        //	Posición de la esquina superior derecha
	        x1=x;
	        y1=y;
	        x1--;
	        y1++;
	        	for(int i=x1;i<x1+3;i++)
	        		{
	        			if(i>-1 && i<filas && y1>-1 && y1<columnas)
	        			 if(matrizMinas[i][y1]==1){
	             		numeroMinas++;
	                	}
	                	y1--;
	        		}
            return numeroMinas;
	}
}