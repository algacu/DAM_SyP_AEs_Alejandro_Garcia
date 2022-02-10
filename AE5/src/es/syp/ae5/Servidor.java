package es.syp.ae5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.net.httpserver.HttpServer;

public class Servidor {
	
	//Servidor en escucha. 
	//Una vez ejecutado, accedemos a http://localhost:7777/estufa

	public static void main(String[] args) throws IOException {
		
		//Configuramos los parámetros básicos del servidor.
		String host = "localhost"; // IP local: 127.0.0.1
		int puerto = 7777;
		InetSocketAddress direccionTCPIP = new InetSocketAddress(host, puerto);
		int backlog = 0; //número de conexiones pendientes que el servidor puede mantener en espera.
		HttpServer servidor = HttpServer.create(direccionTCPIP, backlog);
		
		//Implementamos el gestor de consultas (GETs, POSTs, etc.).
		GestorHTTP gestorHTTP = new GestorHTTP();
		//Ruta en la que el servidor aceptará peticiones.
		String ruta = "/estufa";
		//Asociamos la ruta de peticiones al gestor HTTP.
		servidor.createContext(ruta, gestorHTTP);
		
		//Opción no multihilo
		//servidor.setExecutor(null);
		
		//Opción multihilo (ThreadPoolExecutor)
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		servidor.setExecutor(threadPoolExecutor);
		servidor.start();
		System.out.println("Servidor HTPP en escucha. Puerto: " + puerto);
	}
	
	
	//Nombre: envimaEmail.
		//Param. entrada: strings (email y contraseña).
		//Param. salida: ninguno.
		//Descripción: Recibe la dirección de correo y contraseña del remitente y configura y realiza el envío de un email a destinatarios.
	public static void enviaEmail(String email, String pass) throws AddressException, MessagingException {
		
		String hostEmail = "smtp.gmail.com";
		String remitente = email;
		String remitentePass = pass;
		String puertoEmail = "587";
		
		String[] destinatarios = new String[]{"invernaliaholidays@gmail.com","algacu01@floridauniversitaria.es", "mantenimientoinvernalia@gmail.com", "megustaelfresquito@gmail.com"}; 
		String asunto = "AVERÍA";
		String mensaje = "CREO QUE LE PASA ALGO A LA ESTUFA.";	
		String[] anexos = new String[]{"estufa_rota.jpg","stark_winterfell.pdf"}; 
		
		System.out.println("Envio de correo");
		System.out.println(" > Remitente: " + email);
		
		String destinos = ""; //Para sacarlos por pantalla
		for (int i=0; i < destinatarios.length; i++) {
			destinos += destinatarios[i] + ", ";
		}
		System.out.println(" > Destino: " + destinos);
		
		System.out.println(" > Asunto: " + asunto);
		System.out.println(" > Mensaje: " + mensaje);
		
		//Configuramos el envío del email mediante Session
		
		//1 añadimos parámetros a variable de tipo Properties.
		Properties props = System.getProperties();
		props.put("mail.smtp.host", hostEmail);
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", remitentePass);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", puertoEmail); // TLS --> puerto 587

		//2 montamos la session pasándole las properties anteriores.
		Session session = Session.getDefaultInstance(props);
		
		//3 Creamos un objeto de tipo MimeMessage y añadimos direcciones y asunto.
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(remitente));
		for (int i = 0; i < destinatarios.length; i++) {
			message.addRecipients(Message.RecipientType.TO, destinatarios[i]);
		}
		message.setSubject(asunto);
		
		//Agregamos el cuerpo del mensaje y adjuntos mediante multipart.
		Multipart multipart = new MimeMultipart();
		BodyPart bodyPartCuerpo = new MimeBodyPart();
		bodyPartCuerpo.setText(mensaje);
		multipart.addBodyPart(bodyPartCuerpo);
		for (int i = 0; i < anexos.length; i++) {
			BodyPart bodyPartAnexos = new MimeBodyPart();
			DataSource src = new FileDataSource(anexos[i]);
			bodyPartAnexos.setDataHandler(new DataHandler(src));
			bodyPartAnexos.setFileName(anexos[i]);
			multipart.addBodyPart(bodyPartAnexos);
		}		

		//Añadimos el objeto multipart al objeto message, como contenido.
		message.setContent(multipart);

		//4 Enviamos el mensaje mediante un objeto de tipo Transport sobre la session creada.
		Transport transport = session.getTransport("smtp");
		transport.connect(hostEmail, remitente, remitentePass);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
	}

}
