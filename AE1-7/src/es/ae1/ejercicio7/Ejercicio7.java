package es.ae1.ejercicio7;

import java.util.Scanner;

public class Ejercicio7 {

	public static void main(String[] args) {
		
		clasificaDesarrollador();

	}
	
	public static void clasificaDesarrollador() {
		
		String cadenaResultado = "";
		
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Nombre del desarrollador: ");
		String nombre = teclado.next();
		
		cadenaResultado += nombre + " - ";
		
		System.out.print("Años de experiencia: ");
		float anos = teclado.nextFloat();
		
		if (anos < 1) {
			cadenaResultado += "Desarrollador Junior L1 – 15000-18000";
		} else if (anos >= 1 && anos < 3) {
			cadenaResultado += "Desarrollador Junior L2 – 18000-22000";
		} else if (anos >= 3 && anos < 5) {
			cadenaResultado += "Desarrollador Senior L1 – 22000-28000";
		} else if (anos >= 5 && anos < 8) {
			cadenaResultado += "Desarrollador Senior L2 – 28000-36000";
		} else if (anos >= 8) {
			cadenaResultado += "Analista / Arquitecto. Salario a convenir en base a rol";
		}
		
		System.out.print(cadenaResultado);
				
		teclado.close();
	}

}
