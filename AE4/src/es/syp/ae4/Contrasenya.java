package es.syp.ae4;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Contrasenya implements Serializable{
	String contrasenyaPlana, contrasenyaEncriptada;
	
	public Contrasenya() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Contrasenya(String contrasenyaPlana, String contrasenyaEncriptada) {
		super();
		this.contrasenyaPlana = contrasenyaPlana;
		this.contrasenyaEncriptada = contrasenyaEncriptada;
	}

	public String getContrasenyaPlana() {
		return contrasenyaPlana;
	}

	public void setContrasenyaPlana(String contrasenyaPlana) {
		this.contrasenyaPlana = contrasenyaPlana;
	}

	public String getContrasenyaEncriptada() {
		return contrasenyaEncriptada;
	}

	public void setContrasenyaEncriptada(String contrasenyaEncriptada) {
		this.contrasenyaEncriptada = contrasenyaEncriptada;
	}

	@Override
	public String toString() {
		return "Contraseña plana: " + contrasenyaPlana + " - Contraseña encriptada: " + contrasenyaEncriptada;
	}
	
}
