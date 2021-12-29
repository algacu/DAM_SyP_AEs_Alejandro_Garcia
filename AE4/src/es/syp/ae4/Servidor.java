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
			System.err.println("SERVIDOR >> Envio a cliente la contraseña plana (vacía) para rellenar" );
			
			
			Thread.sleep(2000);
			
			ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
			Contrasenya contrasenyaModificada = (Contrasenya) inObjeto.readObject();
			System.err.println("SERVIDOR >> Recibo la contraseña plana modificada por el cliente: " + contrasenyaModificada.getContrasenyaPlana());
			
			Thread.sleep(2000);
			
			DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
			outData.writeUTF("Elige el tipo de encriptación (ascii o md5): ");
			outData.flush();
			System.err.println("SERVIDOR >> Envío consulta al cliente sobre tipo de encriptación");
			
			
			DataInputStream inData = new DataInputStream(socket.getInputStream());
			String encriptado = inData.readUTF();
			System.err.println("SERVIDOR >> Recibo el tipo de encriptación solicitada: " + encriptado);
			
			Thread.sleep(2000);
			
			if (encriptado.equals("ascii")) {
				
				System.err.println("SERVIDOR >> Comienza la encriptación de la contraseña en ASCII...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoASCII(contrasenyaModificada.getContrasenyaPlana()));
			
			} else if (encriptado.equals("md5")) {
				
				System.err.println("SERVIDOR >> Comienza la encriptación de la contraseña en MD5...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoMD5(contrasenyaModificada.getContrasenyaPlana()));
			
			} else {
				System.err.println("SERVIDOR >> Tipo de encriptación no válido");
			}
			
			outObjeto.writeObject(contrasenyaModificada);
			System.err.println("SERVIDOR >> Envio a cliente la contraseña completa (plana y encriptada): " + contrasenyaModificada.getContrasenyaPlana() + " - " + contrasenyaModificada.getContrasenyaEncriptada());
	
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
		    // Genera un resumen de cálculo cifrado MD5
		    md = MessageDigest.getInstance("MD5");
		        // Calcular la función md5
		    md.update(password.getBytes());
		        // digest () finalmente determina devolver el valor hash md5, y el valor devuelto es 8 como una cadena. Debido a que el valor hash md5 es un valor hexadecimal de 16 bits, en realidad es un carácter de 8 bits
		        // La función BigInteger convierte una cadena de 8 bits en un valor hexadecimal de 16 bits, que está representado por una cadena; el valor hash se obtiene como una cadena
		    String pwd = new BigInteger(1, md.digest()).toString(16);
		    System.err.println(pwd);
		    return pwd;
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		   return password;
		}

}
