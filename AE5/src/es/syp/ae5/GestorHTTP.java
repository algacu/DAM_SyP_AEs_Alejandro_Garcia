package es.syp.ae5;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
			}
		}
	}
	
	
	//Configuramos gestores de peticiones
	private String handleGetRequest(HttpExchange exchange) {
		System.out.println("Recibida URI tipo GET: " + exchange.getRequestURI().toString());
		System.out.println(exchange.getRequestURI().toString().split("//?")[1]);
		return exchange.getRequestURI().toString().split("//?")[1];
	}
	
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
	private void handleGetResponse(HttpExchange exchange, String requestParamValue) throws IOException {
		OutputStream outputStream = exchange.getResponseBody();
		
		String htmlResponse = "";
		
		if (requestParamValue.equals("estufa?temperaturaActual")) {
			
			String sensacion = "";
			String color = "";
			String ned = "";
			
			if (temperaturaActual <= 0) {
				sensacion = "¡¡WINTER IS COMING!! ¡¡ENCHUFAD EL TERMOSTATO, INSENSATOS!!";
				color = "blue";
			} else if (temperaturaActual > 0 && temperaturaActual <= 10) {
				sensacion = "Hace biruji. ¿Y si subimos el termostato?";
				color = "blue";
			} else if (temperaturaActual > 10 && temperaturaActual <= 20) {
				sensacion = "Not bad. Ni frío ni calor.";
				color = "green";
				ned = "https://i.imgflip.com/15rg6x.jpg";
			} else if (temperaturaActual > 20 && temperaturaActual <= 30) {
				sensacion = "Esto va cogiendo temperatura.";
				color = "yellow";
			} else if (temperaturaActual > 30 && temperaturaActual <= 40) {
				sensacion = "Empieza a hacer calor.";
				color = "orange";
			} else if (temperaturaActual > 40) {
				sensacion = "¡Ase kaló! ¡¡Esto parece Dorne!!";
				color = "red";
			}
			
			
			htmlResponse = "<html>"
					+ "<head><title>El tiempo en Invernalia</title></head>"
					+ "<body style=\"background-image: url('https://www.wallpapertip.com/wmimgs/47-477666_winterfell-aerial-view.png');color:white\">"
					+ "<div style=\"display: block; background-color: rgba(0, 0, 0, .5);padding:10px\">"
					+ "<h1 style=\"text-align:center;font-size:60px;\">El tiempo en Invernalia</h1>"
					+ "<h2 style=\"text-align:center;font-size:30px;\">Temperatura actual: " + temperaturaActual + " ºC</h2>"
					+ "<h2 style=\"text-align:center;font-size:30px;\">Temperatura termostato: " + temperaturaTermostato + " ºC</h2>"
					+ "</div>"
					+ "<div style=\"display: inline-block; margin-left:42.5%\">"
					+ "<h2 style=\"text-align:center;font-size:25px;background-color:" + color + ";\">" + sensacion + "</h2>"
					+ "<img src=" + ned  + " alt=\"Ned Stark smiling\" width=\"275\" height=\"300\">"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		} else {
			htmlResponse = "<html><body><h1>Parámetro de consulta erróneo.</h1></body></html>";
		}
		
		exchange.sendResponseHeaders(200, htmlResponse.length());
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
		System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	}
	
	private void handlePostResponse(HttpExchange exchange, String requestParamValue) throws IOException, InterruptedException {
		
		String htmlResponse = "";
		String nuevaTemperatura = "";
		
		if (requestParamValue.split("=")[0].equals("setTemperatura")) {
			nuevaTemperatura = requestParamValue.split("=")[1];
            temperaturaTermostato = Integer.parseInt(nuevaTemperatura);
            regularTemperatura();
		}
		
		OutputStream outputStream = exchange.getResponseBody();
		htmlResponse = "Temperatura actualizada a: " + nuevaTemperatura + "º C";
		exchange.sendResponseHeaders(200, htmlResponse.length());		
		outputStream.write(htmlResponse.getBytes());
		outputStream.flush();
		outputStream.close();
		System.out.println("Devuelve respuesta HTML: " + htmlResponse);
	}
	
	private void regularTemperatura() throws InterruptedException {
		while (temperaturaActual < temperaturaTermostato) {
			temperaturaActual++;
			Thread.sleep(5000);
		}
		while (temperaturaActual > temperaturaTermostato) {
			temperaturaActual--;
			Thread.sleep(5000);
		}
	}

}
