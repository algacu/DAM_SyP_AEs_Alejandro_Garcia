package es.syp.ae5;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class GestorHTTP implements HttpHandler {

	int temperaturaActual = 15;
	int temperaturaTermostato = 15;
	
	//Manejador de peticiones
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String requestParamValue = null;
		
		if ("GET".equals(exchange.getRequestMethod())) {
			requestParamValue = handleGetRequest(exchange);
			handleGetResponse(exchange, requestParamValue);
		} else if ("POST".equals(exchange.getRequestMethod())) {
			requestParamValue = handlePostRequest(exchange);
			try {
				handlePostResponse(exchange, requestParamValue);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//Configuramos gestores de peticiones
	
	//Nombre: hadleGetRequest
		//Param. entrada: objeto de tipo HttpExchange.
		//Param. salida: trozo de la petición URI convertida a string.
		//Descripción: recibe un objeto de tipo HttpExchange, extrae la petición URI (GET, en este caso) y la trocea para obtener la petición.
	private String handleGetRequest(HttpExchange exchange) {
		System.out.println("Recibida URI tipo GET: " + exchange.getRequestURI().toString());
		System.out.println(exchange.getRequestURI().toString().split("//?")[1]);
		return exchange.getRequestURI().toString().split("//?")[1];
	}
	
	//Nombre: hadlePostRequest
			//Param. entrada: objeto de tipo HttpExchange.
			//Param. salida: trozo de la petición URI convertida a string.
			//Descripción: recibe un objeto de tipo HttpExchange, extrae la petición de tipo POST, lee su contenido y lo guarda en una variable de tipo stringBuilder.
	private String handlePostRequest(HttpExchange exchange) {
		System.out.println("Recibida URI tipo POST: " + exchange.getRequestBody().toString());
		InputStream input = exchange.getRequestBody();
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder stringBuilder = new StringBuilder();
		String linea;
		try {
			while ((linea = bufferedReader.readLine()) != null) {
				stringBuilder.append(linea);
			}
			bufferedReader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return stringBuilder.toString();		
	}
	
	
	//Configuramos gestores de respuestas
	
	//Nombre: hadleGetResponse
		//Param. entrada: objeto de tipo HttpExchange y string con una petición procesada por handleGestRequest.
		//Param. salida: no.
		//Descripción: comprueba la petición recibida (de tipo GET) y devuelve una respuesta html al usuario y otra de tipo OutputStream al servidor.
	private void handleGetResponse(HttpExchange exchange, String requestParamValue) throws IOException {
		
		String htmlResponse = "";
		
		if (requestParamValue.equals("estufa?temperaturaActual")) {
			
			String sensacion = "";
			String color = "";
			String ned = "";
			
			if (temperaturaActual <= 0) {
				sensacion = "¡¡WINTER IS COMING!! ¡¡ENCHUFAD EL TERMOSTATO, INSENSATOS!!";
				color = "blue";
				ned = "http://images5.fanpop.com/image/photos/30000000/Eddard-Stark-Lord-Snow-1-03-lord-eddard-ned-stark-30085142-1280-720.jpg";
			} else if (temperaturaActual > 0 && temperaturaActual < 10) {
				sensacion = "Hace biruji. ¿Y si subimos el termostato?";
				color = "blue";
				ned = "https://i.pinimg.com/originals/b4/73/62/b47362fea0e74af04a30a750ab6b532e.jpg";
			} else if (temperaturaActual >= 10 && temperaturaActual < 20) {
				sensacion = "Not bad. Ni frío ni calor.";
				color = "green";
				ned = "https://i.pinimg.com/originals/3f/d5/43/3fd543c7c3d176bd3078ea204bd60a78.png";
			} else if (temperaturaActual >= 20 && temperaturaActual < 30) {
				sensacion = "Esto va cogiendo temperatura.";
				color = "orange";
				ned = "https://i.imgflip.com/15rg6x.jpg";
			} else if (temperaturaActual >= 30 && temperaturaActual < 40) {
				sensacion = "Empieza a hacer calor.";
				color = "orange";
				ned = "http://images4.fanpop.com/image/photos/24500000/Eddard-Stark-house-stark-24506349-500-333.jpg";
			} else if (temperaturaActual >= 40) {
				sensacion = "¡Ase kaló! ¡¡Esto parece Dorne!!";
				color = "red";
				ned = "http://images5.fanpop.com/image/photos/30000000/Eddard-Stark-Lord-Snow-1-03-lord-eddard-ned-stark-30085142-1280-720.jpg";
			}
			
			htmlResponse = "<html>"
					+ "<head><title>El tiempo en Invernalia</title></head>"
					+ "<body style=\"background-image: url('https://www.wallpapertip.com/wmimgs/47-477666_winterfell-aerial-view.png');color:white\">"
					+ "<div style=\"display: block; background-color: rgba(0, 0, 0, .5);padding:10px\">"
					+ "<h1 style=\"text-align:center;font-size:60px;\">El tiempo en Invernalia</h1>"
					+ "<h2 style=\"text-align:center;font-size:40px;\">Temperatura actual: " + temperaturaActual + " ºC</h2>"
					+ "<h2 style=\"text-align:center;font-size:40px;\">Temperatura termostato: " + temperaturaTermostato + " ºC</h2>"
					+ "</div>"
					+ "<h2 style=\"text-align:center;font-size:30px;background-color:" + color + ";\">" + sensacion + "</h2>"
					+ "<div style=\"display: inline-block; margin-left:42.5%\">"
					+ "<img src=" + ned  + " alt=\"Ned Stark\" width=\"275\" height=\"350\">"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		} else {
			htmlResponse = "<html><body><h1>Parámetro de consulta erróneo.</h1></body></html>";
		}
		
		OutputStream outputStream = exchange.getResponseBody();
		exchange.sendResponseHeaders(200, htmlResponse.length());
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
		
		System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	}
	
	
	//Nombre: hadlePostResponse
			//Param. entrada: objeto de tipo HttpExchange y string con una petición procesada por handlePostRequest.
			//Param. salida: no.
			//Descripción: comprueba la petición recibida (de tipo POST) y ejecuta las instrucciones pertinentes. Devuelve una respuesta htmly otra de tipo OutputStream a usuario y servidor.	
	private void handlePostResponse(HttpExchange exchange, String requestParamValue) throws IOException, InterruptedException, AddressException, MessagingException {
		
		String htmlResponse = "";
		String nuevaTemperatura = "";
		String email = "";
		String emailPass = "";
		
		if (requestParamValue.split("=")[0].equals("setTemperatura")) {
			nuevaTemperatura = requestParamValue.split("=")[1];
            temperaturaTermostato = Integer.parseInt(nuevaTemperatura);
            regularTemperatura();
            htmlResponse = "Temperatura actualizada a: " + temperaturaActual + "º C";
		} else if (requestParamValue.split(":")[0].equals("notificarAveria")) {
			email = requestParamValue.split("=")[1].split(";")[0];
			emailPass = requestParamValue.split("=")[2];
			System.out.println(email + " " + emailPass);
			Servidor.enviaEmail(email, emailPass);
			htmlResponse = "Correo enviado correctamente.";
		}
		
		OutputStream outputStream = exchange.getResponseBody();
		exchange.sendResponseHeaders(200, htmlResponse.length());		
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
		
		System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	}
	
	
	//Nombre: hadlePostResponse
		//Param. entrada: no.
		//Param. salida: no.
		//Descripción: Actualiza la variable 'temperaturaActual' (atributo de clase), incrementando o decrementando una unidad cada 5 segundos.
	private void regularTemperatura() throws InterruptedException {
		System.out.println("\nAtualizando temperatura...");
		while (temperaturaActual < temperaturaTermostato) {
			Thread.sleep(5000);
			temperaturaActual++;
			System.out.println("Temperatura actual: " + temperaturaActual + "º C");
		}
		while (temperaturaActual > temperaturaTermostato) {
			Thread.sleep(5000);
			temperaturaActual--;
			System.out.println("Temperatura actual: " + temperaturaActual + "º C");
		}
	}

}
