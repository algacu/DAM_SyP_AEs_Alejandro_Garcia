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
	
    // Método: run
 		// Descripción: ejecuta los procesos del hilo.
 		// Parámetros de entrada: no.
 		// Parámetros de salida: no.
	@Override
	public void run() {
		
		try {
			
			Thread.sleep(2000);
			
			ObjectOutputStream outObjeto = new ObjectOutputStream(socket.getOutputStream());
			String nombreHilo = Thread.currentThread().getName();
			
			Contrasenya contrasenya = new Contrasenya("", "");
			outObjeto.writeObject(contrasenya);
			
			System.err.println(nombreHilo + " - SERVIDOR >> Envio a cliente la contraseña plana (vacía) para rellenar");
			
			Thread.sleep(2000);
			
			ObjectInputStream inObjeto = new ObjectInputStream(socket.getInputStream());
			Contrasenya contrasenyaModificada = (Contrasenya) inObjeto.readObject();
			System.err.println(nombreHilo + " - SERVIDOR >> Recibo la contraseña plana modificada por el cliente: " + contrasenyaModificada.getContrasenyaPlana());
			
			Thread.sleep(2000);
			
			DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
			outData.writeUTF("Elige el tipo de encriptación (ascii o md5): ");
			outData.flush();
			System.err.println(nombreHilo + " - SERVIDOR >> Envío consulta al cliente sobre tipo de encriptación");
			
			DataInputStream inData = new DataInputStream(socket.getInputStream());
			String encriptado = inData.readUTF();
			System.err.println(nombreHilo + " - SERVIDOR >> Recibo el tipo de encriptación solicitada: " + encriptado);
			
			Thread.sleep(2000);
			
			if (encriptado.equals("ascii")) {
				
				System.err.println(Thread.currentThread().getName() + "SERVIDOR >> Comienza la encriptación de la contraseña en ASCII...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoASCII(contrasenyaModificada.getContrasenyaPlana()));
			
			} else if (encriptado.equals("md5")) {
				
				System.err.println(nombreHilo + " - SERVIDOR >> Comienza la encriptación de la contraseña en MD5...");
				contrasenyaModificada.setContrasenyaEncriptada(EncriptadoMD5(contrasenyaModificada.getContrasenyaPlana()));
			
			} else {
				System.err.println(nombreHilo + " - SERVIDOR >> Tipo de encriptación no válido");
			}
			
			outObjeto.writeObject(contrasenyaModificada);
			
			if (!contrasenyaModificada.getContrasenyaEncriptada().equals("")) {
				System.err.println(nombreHilo + " - SERVIDOR >> Envio a cliente la contraseña completa (plana y encriptada): " + contrasenyaModificada.getContrasenyaPlana() + " - " + contrasenyaModificada.getContrasenyaEncriptada());
			} else {
				System.err.println("ERROR en la encriptación. El tipo de encriptado solicitado por el cliente no es válido");
			}
			
			outObjeto.close();
			inObjeto.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	// Método: EncriptadoASCII
	 		// Descripción: recibe un string y lo "encripta" cambiando cada caracter por su sucesivo en la tabla ASCII. Si el caracter resultante fuera de tipo 'no imprimible', lo sustituye por un asterisco.
	 		// Parámetros de entrada: string.
	 		// Parámetros de salida: string.
	public static String EncriptadoASCII(String password) {
		
		String encriptada = "";
		
		for (int i=0; i < password.length(); i++) {
			char caracter = password.charAt(i);
			int ascii = caracter + 1;
			
			if (ascii >= 32 && ascii <= 126 ) {
				char caracterDos = (char) ascii;
				encriptada += caracterDos;
			} else {
				encriptada += "*";
			}
		}

		return encriptada;
	}
	
	
	// Método: EncriptadoMD5
		// Descripción: recibe un string y lo encripta con cifrado MD5.
		// Parámetros de entrada: string.
		// Parámetros de salida: string.	
	public static String EncriptadoMD5(String password) {
		MessageDigest md;
		   try {
		    // Genera un resumen de cálculo cifrado MD5
		    md = MessageDigest.getInstance("MD5");
		        // Calcula la función MD5
		    md.update(password.getBytes());
		        // digest () finalmente determina devolver el valor hash md5, y el valor devuelto es 8 como una cadena. Debido a que el valor hash md5 es un valor hexadecimal de 16 bits, en realidad es un carácter de 8 bits
		        // La función BigInteger convierte una cadena de 8 bits en un valor hexadecimal de 16 bits, que está representado por una cadena; el valor hash se obtiene como una cadena
		    String pwd = new BigInteger(1, md.digest()).toString(16);
		    return pwd;
		   } catch (Exception e) {
		    e.printStackTrace();
		   }
		   return password;
		}

}
