package es.ae1.ejercicio2;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio2 {

	public static void main(String[] args) {
			
			System.out.println("\n---Array---\n");
			
			String[] arrayCompis = new String[] {"Alejandro", "Jose", "Antonio", "David", "Guillermo", "Manuel"};
			
			for (int i = 0; i < arrayCompis.length; i++) {
				
				System.out.println("Compañero " + (i+1) + ": " + arrayCompis[i]);
			}
			
			
			System.out.println("\n---Lista---\n");
			
			List<String> listaCompis = new ArrayList<String>();
			
			listaCompis.add("Alejandro");
			listaCompis.add("Jose");
			listaCompis.add("Antonio");
			listaCompis.add("David");
			listaCompis.add("Guillermo");
			listaCompis.add("Manuel");
			
			int contador = 1;
			
			for(String compi:listaCompis) {
				System.out.println("Compañero " + contador++ + ": " + compi);
			}
			
	
	}

}