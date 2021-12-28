package es.syp.ae4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
			
			Scanner teclado = new Scanner(System.in);
			
			System.out.print("Introducir IP: ");
			String host = teclado.nextLine();
			System.out.print("Introducir puerto: ");
			int puerto = Integer.parseInt(teclado.nextLine());
			
			System.out.println("CLIENTE >> Arranca cliente -> esperando mensaje del servidor...");
			
			Socket cliente = new Socket(host, puerto);
			
			ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
			Contrasenya contrasenya = (Contrasenya) inObjeto.readObject();
			System.out.println("CLIENTE >> Recibo del servidor: " + contrasenya.getContrasenyaPlana());
			
			System.out.println("CLIENTE >> Actualizar información del objeto...");
			System.out.print("Introducir nueva contraseña: ");
			String nuevaContrasenya = teclado.nextLine();
			
			contrasenya.setContrasenyaPlana(nuevaContrasenya);
			
			Thread.sleep(2000);
			
			System.out.println("CLIENTE >> Envio al servidor la nueva contrseña: " + contrasenya.getContrasenyaPlana());
			ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
			outObjeto.writeObject(contrasenya);
			
			//inObjeto = new ObjectInputStream(cliente.getInputStream());
			Contrasenya contrasenyaCompleta = (Contrasenya) inObjeto.readObject();
			System.out.println("CLIENTE >> Recibo la contraseña encriptada: " + contrasenyaCompleta.toString());
			
			outObjeto.close();
			inObjeto.close();
			cliente.close();
			teclado.close();
		}

}
