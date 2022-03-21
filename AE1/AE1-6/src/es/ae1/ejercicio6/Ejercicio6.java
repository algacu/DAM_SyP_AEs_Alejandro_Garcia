package es.ae1.ejercicio6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio6 {

	public static void main(String[] args) {
		
		CincoNumeros();

	}
	
	public static void CincoNumeros() {
		
		List<Integer> listaNumeros = new ArrayList<Integer>();
		
		Scanner teclado = new Scanner(System.in);
		
		String numCadena = "";
		int numSuma = 0;
				
		for (int i = 0; i < 5; i++ ) {
			
			System.out.print("Dame un número: ");
			int numero = teclado.nextInt();
			listaNumeros.add(numero);
			
		}
		
		
		for (int i = listaNumeros.size()-1; i >= 0; i--) {
			
			numSuma += listaNumeros.get(i);
			
			String numString = String.valueOf(listaNumeros.get(i));
			numCadena += numString + " ";
			
		}
		
		System.out.println("\nLista de números mostrada en orden inverso: " + numCadena);
		System.out.println("Suma de todos los números: " + numSuma);
				
		teclado.close();
		
		
	}
}
