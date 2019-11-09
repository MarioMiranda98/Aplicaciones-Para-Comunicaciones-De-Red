import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Base64;

public class Manejador extends Thread {
    protected Socket cl;
    protected DataOutputStream dos;
    protected Mime mime;
    protected DataInputStream dis;
    
    public Manejador(Socket cl) throws Exception {
        this.cl = cl;
        this.dos = new DataOutputStream(this.cl.getOutputStream());
        this.mime = new Mime();
        this.dis = new DataInputStream(this.cl.getInputStream());
    }

    public void eliminarRecurso(String arg, String headers){
        try {
            System.out.println(arg);
            File f = new File(arg);

            if(f.exists()) {
                if (f.delete()) {
                    System.out.println("------> Archivo " + arg + " eliminado exitosamente\n");

                    String deleteOK = headers +
                                      "<html><head><meta charset='UTF-8'><title>202 OK Recurso eliminado</title></head>" +
                                      "<body><h1>202 OK Recurso eliminado exitosamente.</h1>" +
                                      "<p>El recurso " + arg + " ha sido eliminado permanentemente del servidor." + 
                                      "Ya no se podra acceder más a él.</p>" +
                                      "</body></html>";

                    dos.write(deleteOK.getBytes());
                    dos.flush();
                    System.out.println("Respuesta DELETE: \n" + deleteOK);
                }
                else {
                    System.out.println("El archivo " + arg + " no pudo ser borrado\n");

                    String error404 = "HTTP/1.1 404 Not Found\n" +
                                      "Date: " + new Date() + " \n" +
                                      "Server: EnrikeAbi Server/1.0 \n" +
                                      "Content-Type: text/html \n\n" +

                                      "<html><head><meta charset='UTF-8'><title>404 Not found</title></head>" +
                                      "<body><h1>404 Not found</h1>" +
                                      "<p>Archivo " + arg + " no encontrado.</p>" +
                                      "</body></html>";

                    dos.write(error404.getBytes());
                    dos.flush();
                    System.out.println("Respuesta DELETE - ERROR 404: \n" + error404);
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enviarRecurso(String arg, int bandera) {
        try {
        	File f = new File(arg);
        	String sb = "HTTP/1.1 200 OK\n";

        	if(!f.exists()) {
        		arg = "404.html"; // Recurso no encontrado
        		sb = "HTTP/1.1 404 Not Found\n";
        	}
        	else if(f.isDirectory()) {
        		arg = "403.html"; // Recurso privado
        		sb = "HTTP/1.1 403 Forbidden\n";
        	}

    		DataInputStream dis2 = new DataInputStream(new FileInputStream(arg)); 
    		int tam = dis2.available();

    		// Obtenemos extension para saber el tipo de recurso
            int pos = arg.indexOf(".");
            String extension = arg.substring(pos + 1, arg.length());

            // Enviamos las cabeceras de la respuesta HTTP - METODO HEAD
            sb = sb + "Date: " + new Date() + " \n" +
		              "Server: EnrikeAbi Server/1.0 \n" +
		              //Distintos tipos MIME para distintos tipos de archivos
		              "Content-Type: " + mime.get(extension) + " \n" + 
		              "Content-Length: " + tam + " \n\n";

            dos.write(sb.getBytes());
            dos.flush();

            String metodo = "HEAD";
            if (bandera == 1) {
	            metodo = "GET";
	            // Respuesta GET, enviamos el archivo solicitado
	            byte[] b = new byte[1024];
	            long enviados = 0;
	            int n = 0;
	            
	            while(enviados < tam) {
	                n = dis2.read(b);
	                dos.write(b, 0, n);
	                dos.flush();
	                enviados += n;
	            }
            }
            System.out.println("Respuesta " + metodo + ": \n" + sb);
            dis2.close(); 	
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    public String obtenerNombreRecurso(String line) {
    	// Obtiene el nombre del recurso de la peticion HTTP
        int i = line.indexOf("/");
        int f = line.indexOf(" ", i);
        String resourceName = line.substring(i + 1, f);

        // Si es vacio, entonces se trata del index
        if(resourceName.compareTo("") == 0)
            resourceName = "index.html";

        return resourceName;
    }

    public String obtenerParametros(String line, String headers, int bandera) {
        String metodo = "POST";
        String request2 = line;

        if(bandera == 0) {
            metodo = "GET";
        	// Line: GET /?Nombre=&Direccion=&Telefono=&Comentarios= HTTP/1.1
        	// Separamos los parametros de "GET"
            System.out.println(line);
        	StringTokenizer tokens = new StringTokenizer(line, "?");
            String request = tokens.nextToken();
            request = tokens.nextToken();

            // Separamos los parametros de "HTTP/1.1"
            StringTokenizer tokens2 = new StringTokenizer(request, " ");
            request2 = tokens2.nextToken();
        }

        System.out.println(request2);
        // Separamos los parametros junto a su valor uno del otro
        StringTokenizer paramsTokens = new StringTokenizer(request2, "&");

        String html = headers +
					  "<html><head><meta charset='UTF-8'><title>Metodo " + metodo + "\n" +
                      "</title></head><body bgcolor='#AACCFF'><center><h2>Parametros obtenidos por medio de " + metodo + "</h2><br>\n" +
                      "<table border='2'><tr><th>Parametro</th><th>Valor</th></tr>";

      	// Se recorren todos los parametros, mientras existan
        while(paramsTokens.hasMoreTokens()) {
        	String parametros = paramsTokens.nextToken();
        	// Separamos el nombre del parametro de su valor
        	StringTokenizer paramValue = new StringTokenizer(parametros, "=");
        	String param = ""; //Nombre del parametro
        	String value = ""; //Valor del parametro

        	// Hay que revisar si existen o si se enviaron parametros vacios
        	if(paramValue.hasMoreTokens())
        		param = paramValue.nextToken();

        	if(paramValue.hasMoreTokens())
        		value = paramValue.nextToken();

        	html = html + "<tr><td><b>" + param + "</b></td><td>" + value + "</td></tr>\n";
        }
        html = html + "</table></center></body></html>";
        return html;
    }

    @Override
    public void run() {
    	// Cabeceras de respuestas HTTP
    	String headers = "HTTP/1.1 200 OK\n" +
            			 "Date: " + new Date() + " \n" +
          				 "Server: Wicho Server/1.0 \n" +
          				 "Content-Type: text/html \n\n";
        try {
            String line = dis.readLine(); // Lee primera linea DEPRECIADO !!!!
            // Linea vacia
            if(line == null) {
                String vacia = "<html><head><title>Servidor WEB</title><body bgcolor='#AACCFF'>Linea Vacia</body></html>";
                dos.write(vacia.getBytes());
                dos.flush();
            }
            else {
                System.out.println("\n------> Cliente Conectado desde: " + cl.getInetAddress());
                System.out.println("Por el puerto: " + cl.getPort());
                System.out.println("Datos: " + line + "\r\n\r\n");

                // Metodo GET
                if(line.toUpperCase().startsWith("GET")) {
                	if(line.indexOf("?") == -1) {
	                    // Solicita un archivo
				        String fileName = obtenerNombreRecurso(line);
				        // Bandera HEAD = 0, GET = 1
	                    enviarRecurso(fileName, 1);
	                }
	                else {
	                    // Envia parametros desde un formulario
                        // Bandera GET = 0, POST = 1
	                    String respuesta = obtenerParametros(line, headers, 0);
	                    // Respuesta GET, devolvemos un HTML con los parametros del formulario
	                    dos.write(respuesta.getBytes());
	                    dos.flush();
	                    System.out.println("Respuesta GET: \n" + respuesta);
	                }
                } // Metodo HEAD
                else if(line.toUpperCase().startsWith("HEAD")) {
                	if(line.indexOf("?") == -1) {
                		// Solicita archivo, unicamente enviamos tipo mime y longitud
                		String fileName = obtenerNombreRecurso(line);
                		// Bandera HEAD = 0, GET = 1
                        enviarRecurso(fileName, 0);
                	}
                	else {
                		// Respuesta HEAD, devolvemos unicamente las cabeceras HTTP
                		dos.write(headers.getBytes());
	                	dos.flush();
	                	System.out.println("Respuesta HEAD: \n" + headers);       
                	}
                } // Metodo POST
                else if(line.toUpperCase().startsWith("POST")) {
                    // Leemos el flujo de entrada
                    int tam = dis.available();
                    byte[] b = new byte[tam];

                    dis.read(b);
                    //Creamos un string con los bytes leidos
                    String request = new String(b, 0, tam);
                   
                    // Separamos los parametros del resto de los encabezados HTTP
                    String[] reqLineas = request.split("\n");
                    //Ultima linea del request
                    int ult = reqLineas.length - 1;

                    // Bandera GET = 0, POST = 1
                    String respuesta = obtenerParametros(reqLineas[ult], headers, 1);

                    // Respuesta POST, devolvemos un HTML con los parametros del formulario
                    dos.write(respuesta.getBytes());
                    dos.flush();
                    System.out.println("Respuesta POST: \n" + respuesta);
                } // Metodo DELETE
                else if(line.toUpperCase().startsWith("DELETE")) {      
                    String fileName = obtenerNombreRecurso(line);
                    eliminarRecurso(fileName, headers);
                }
                else {
                	//Metodos no implementados en el servidor
                    String error501 = "HTTP/1.1 501 Not Implemented\n" +
                    				  "Date: " + new Date() + " \n" +
		              				  "Server: EnrikeAbi Server/1.0 \n" +
		              				  "Content-Type: text/html \n\n" +

	        						  "<html><head><meta charset='UTF-8'><title>Error 501</title></head>" +
	        						  "<body><h1>Error 501: No implementado.</h1>" +
	        						  "<p>El método HTTP o funcionalidad solicitada no está implementada en el servidor.</p>" +
	        						  "</body></html>";

                    dos.write(error501.getBytes());
                    dos.flush();
                    System.out.println("Respuesta ERROR 501: \n" + error501);
                }
            }
            dis.close();
            dos.close();
            cl.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}