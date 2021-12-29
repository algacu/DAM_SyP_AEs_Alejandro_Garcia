package es.syp.ae4;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;

public class Servidor implements Runnable {
	
	BufferedReader bfr;
    PrintWriter pw;
    Socket socket;

    public Servidor(Socket socket) {
        this.socket = socket;
    }
	

	@Override
	public void run() {
		
		try {
			Thread.sleep(2000);
			
			ObjectOutputStream outObjeto = new ObjectOutputStream(socket.getOutputStream());
			
			
			Contrasenya contrasenya = new Contrasenya("", "");
			outObjeto.writeObject(contrasenya);
			System.err.println("SERVIDOR >> Envio a cliente la contrase�a plana (vac�a) para rellenar" );
			
			
			Thread.sleep(2000);
			
			ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
			Contrasenya contrasenyaModificada = (Contrasenya) inObjeto.readObject();
			System.err.println("SERVIDOR >> Recibo la contrase�a plana modificada por el cliente: " + contrasenyaModificada.getContrasenyaPlana());
			
			Thread.sleep(2000);
			
			DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
			outData.writeUTF("Elige el tipo de encriptaci�n (ascii o md5): ");
			outData.flush();
			System.err.println("SERVIDOR >> Env�o consulta al cliente sobre tipo de encriptaci�n");
			
			
			DataInputStream inData = new DataInputStream(socket.getInputStream());
			String encriptado = inData.readUTF();
			System.err.println("SERVIDOR >> Recibo el tipo de encriptaci�n solicitada: " + encriptado);
			
			Thread.sleep(2000);
			
			if (encriptado.equals("ascii")) {
				
				System.err.println("SERVIDOR >> Comienza la encriptaci�n de la contrase�a en ASCII...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoASCII(contrasenyaModificada.getContrasenyaPlana()));
			
			} else if (encriptado.equals("md5")) {
				
				System.err.println("SERVIDOR >> Comienza la encriptaci�n de la contrase�a en MD5...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoMD5(contrasenyaModificada.getContrasenyaPlana()));
			
			} else {
				System.err.println("SERVIDOR >> Tipo de encriptaci�n no v�lido");
			}
			
			outObjeto.writeObject(contrasenyaModificada);
			System.err.println("SERVIDOR >> Envio a cliente la contrase�a completa (plana y encriptada): " + contrasenyaModificada.getContrasenyaPlana() + " - " + contrasenyaModificada.getContrasenyaEncriptada());
	
			outObjeto.close();
			inObjeto.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static String EncriptadoASCII(String contrasenya) {
		
		String encriptada = "";
		
		for (int i=0; i < contrasenya.length(); i++) {
			char caracter = contrasenya.charAt(i);
			int ascii = caracter + 1;
			char caracterDos = (char) ascii;
			encriptada += caracterDos;
		}
		
		encriptada.replaceAll("\\P{Print}", "*");
		
		return encriptada;
	}
	
	public static String EncriptadoMD5(String password) {
		MessageDigest md;
		   try {
		    // Genera un resumen de c�lculo cifrado MD5
		    md = MessageDigest.getInstance("MD5");
		        // Calcular la funci�n md5
		    md.update(password.getBytes());
		        // digest () finalmente determina devolver el valor hash md5, y el valor devuelto es 8 como una cadena. Debido a que el valor hash md5 es un valor hexadecimal de 16 bits, en realidad es un car�cter de 8 bits
		        // La funci�n BigInteger convierte una cadena de 8 bits en un valor hexadecimal de 16 bits, que est� representado por una cadena; el valor hash se obtiene como una cadena
		    String pwd = new BigInteger(1, md.digest()).toString(16);
		    System.err.println(pwd);
		    return pwd;
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		   return password;
		}

}
