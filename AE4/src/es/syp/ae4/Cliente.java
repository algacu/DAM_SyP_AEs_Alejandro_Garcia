package es.syp.ae4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
			System.out.println("CLIENTE >> Recibo del servidor la contrase�a plana (vac�a)");
			
			System.out.print("\nCLIENTE >> Introducir nueva contrase�a: ");
			String nuevaContrasenya = teclado.nextLine();
			
			contrasenya.setContrasenyaPlana(nuevaContrasenya);
			
			Thread.sleep(2000);
			
			System.out.println("\nCLIENTE >> Env�o al servidor la nueva contrase�a: " + contrasenya.getContrasenyaPlana());
			ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
			outObjeto.writeObject(contrasenya);
			
			Thread.sleep(2000);
		
			System.out.println("CLIENTE >> Recibo consulta sobre encriptaci�n\n");
			DataInputStream inData = new DataInputStream(cliente.getInputStream());
			System.out.print(inData.readUTF());
			String encriptado = teclado.nextLine();
			
			System.out.println("\nCLIENTE >> Env�o tipo de encriptaci�n");
			DataOutputStream outData = new DataOutputStream(cliente.getOutputStream());
			outData.writeUTF(encriptado);
			outData.flush();
			
			Thread.sleep(2000);

			Contrasenya contrasenyaCompleta = (Contrasenya) inObjeto.readObject();
			System.out.println("CLIENTE >> Recibo la contrase�a completa (plana y encriptada): " + contrasenyaCompleta.toString());
			
			outObjeto.close();
			inObjeto.close();
			cliente.close();
			teclado.close();
		}

}
