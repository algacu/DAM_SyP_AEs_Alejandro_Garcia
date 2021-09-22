package es.ae1.ejercicio4;

public class Ejercicio4 {

	public static void main(String[] args) {
		
		long factorial = 1;
		
		for (int i = 2; i <= 15; i++) {
			factorial *= i;
		}
		
		System.out.println("El factorial del número " + Integer.toString(15) +  " es " + Long.toString(factorial));

	}

}
