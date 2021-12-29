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
			
			Socket cliente = new Socket(host, puerto);
			System.out.println("\nCLIENTE >> Arranca cliente --> Esperando mensaje del servidor...");
			
			ObjectInputStream inObjeto = new ObjectInputStream(cliente.getInputStream());
			Contrasenya contrasenya = (Contrasenya) inObjeto.readObject();
			System.out.println("\nCLIENTE >> Recibo del servidor la contraseña plana (vacía)");
			
			System.out.print("CLIENTE >> Introducir nueva contraseña: ");
			String nuevaContrasenya = teclado.nextLine();
			contrasenya.setContrasenyaPlana(nuevaContrasenya);
			System.out.println("CLIENTE >> Envío al servidor la nueva contraseña: " + contrasenya.getContrasenyaPlana());
			ObjectOutputStream outObjeto = new ObjectOutputStream(cliente.getOutputStream());
			outObjeto.writeObject(contrasenya);
			
			Thread.sleep(2000);
		
			System.out.println("\nCLIENTE >> Recibo consulta sobre encriptación");
			DataInputStream inData = new DataInputStream(cliente.getInputStream());
			System.out.print(inData.readUTF());
			String encriptado = teclado.nextLine();
			encriptado = encriptado.toLowerCase();
			
			System.out.println("CLIENTE >> Envío tipo de encriptación");
			DataOutputStream outData = new DataOutputStream(cliente.getOutputStream());
			outData.writeUTF(encriptado);
			outData.flush();
			
			Thread.sleep(2000);

			Contrasenya contrasenyaCompleta = (Contrasenya) inObjeto.readObject();
			
			if (!contrasenyaCompleta.getContrasenyaEncriptada().equals("")) {
				System.out.println("\nCLIENTE >> Recibo contraseña completa --> " + contrasenyaCompleta.toString());
			} else {
				System.out.println("\nERROR en la encriptación. El tipo de encriptado solicitado no es válido");
			}
			
			outObjeto.close();
			inObjeto.close();
			cliente.close();
			teclado.close();
		}

}
