package es.ae1.ejercicio3;

import java.util.*;

public class Ejercicio3 {
	

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Dame un número: ");
		int numero = reader.nextInt();
		
		System.out.println("La suma de los números pares hasta " + numero + " da " + sumaPares(numero));
		
		reader.close();
	}
	
	
	public static int sumaPares(int num) {
		
		int sumaPar = 0;
		
		for (int i = 1; i <= num; i++) {
			
			if (i % 2 == 0) {
				
				sumaPar += i;
				
			}
		}
		
		return sumaPar;
		
	}

}
