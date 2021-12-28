package es.syp.ae4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
			
			int numeroPuerto = 1222;
			
			ServerSocket servidor = new ServerSocket(numeroPuerto);
			
			System.err.println("SERVIDOR >> Escuchando...");
			
			Socket cliente = servidor.accept();
			
			Thread.sleep(2000);
			
			ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
			Contrasenya contrasenya = new Contrasenya("", "");
			outObjeto.writeObject(contrasenya);
			System.err.println("SERVIDOR >> Envio a cliente la contraseña plana (vacía): " + contrasenya.getContrasenyaPlana());
			
			
			Thread.sleep(2000);
			
			ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
			Contrasenya contrasenyaModificada = (Contrasenya) inObjeto.readObject();
			System.err.println("SERVIDOR >> Recibo de cliente la contraseña modifiada: " + contrasenyaModificada.getContrasenyaPlana());
			
			
			Thread.sleep(1000);
			
			System.err.println("SERVIDOR >> Comienza la encriptación de la contraseña...");
			contrasenyaModificada.setContrasenyaEncriptada(Encriptar(contrasenyaModificada.getContrasenyaPlana()));
			
			outObjeto.writeObject(contrasenyaModificada);
			System.err.println("SERVIDOR >> Envio a cliente contraseña completada: " + contrasenyaModificada.getContrasenyaPlana() + " - " + contrasenyaModificada.getContrasenyaEncriptada());
	
			outObjeto.close();
			inObjeto.close();
			cliente.close();
			servidor.close();
		}
	
	
	public static String Encriptar(String contrasenya) {
		
		String encriptada = contrasenya + "_encriptada";
		
		/*for (int i = 0; i < contrasenya.length(); i++) {
			encriptada += contrasenya.codePointAt(i+1);
		}*/
		
		return encriptada;
	}



}
