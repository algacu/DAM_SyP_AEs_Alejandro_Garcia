package es.ae1.ejercicio8;

import java.util.Scanner;

public class Ejercicio8 {
	

	public static void main(String[] args) {
		numerosIntervalo();
	}
	
	public static void numerosIntervalo() {
		
		long tiempoInicio = System.currentTimeMillis();	
		
		int num1 = 0;
		int num2 = 0;
				
		Scanner teclado = new Scanner(System.in);
		
		do {
			
			System.out.print("Introduce el 1er número del intervalo: ");
			num1 = teclado.nextInt();
			
			System.out.print("Introduce el 2do número del intervalo: ");
			num2 = teclado.nextInt();
			
			if (num1 > num2) {
				System.out.println("El segundo número del intervalo no puede ser menor al primero.\n");
			} else if (num1 == num2) {
				System.out.println("Los números del intervalo no pueden ser iguales.\n");
			}
			
		} while (num1 >= num2);
		
		teclado.close();
		
		
		for (int i = num1; i <= num2; i++) {
			
			boolean comprueba = true;
			
			if (i == 0 || i == 1 || i == 4) {
				comprueba = false;
			}
			
			for (int j = 2; j < i / 2; j++) {
				
				if ( i % j == 0) {
					comprueba = false;
				}
			}
			
			if (comprueba == true) {
				System.out.println(i + " - PRIMO");
			} else {
				System.out.println(i + " - no");
			}
			
		}
		
		
		long tiempoFin = System.currentTimeMillis();
		
		double tiempo = (double)((tiempoFin - tiempoInicio)/1000);
		
		System.out.println("\nEl método ha tardado " + tiempo + " segundos en ejecutarse.");		
		
	}
	

}
