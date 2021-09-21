package es.ae1.ejercicio2;

import java.util.*;

public class Ejercicio2 {

	public static void main(String[] args) {
			
			System.out.println("\n---Array---\n");
			
			//PRIMERA PARTE DEL EJERCICIO. Creamos un array de 6 elementos
			String[] arrayCompis = new String[] {"Alejandro", "Jose", "Antonio", "David", "Guillermo", "Manuel"};
			
			//Recorremos el array para imprimir por pantalla sus elementos en líneas consecutivas				
			for (int i = 0; i < arrayCompis.length; i++) {
				
				System.out.println("Compañero " + (i+1) + ": " + arrayCompis[i]);
			}
			
			
			System.out.println("\n---Lista---\n");
			
			//SEGUNDA PARTE DEL EJERCICIO. Creamos una lista			
			List<String> listaCompis = new ArrayList<String>();
			
			//Añadimos elementos a la lista	
			listaCompis.add("Alejandro");
			listaCompis.add("Jose");
			listaCompis.add("Antonio");
			listaCompis.add("David");
			listaCompis.add("Guillermo");
			listaCompis.add("Manuel");
			
			//Recorremos la lista (en este caso con un bucle de tipo foreach) para imprimir sus elementos por pantalla
			for(String compi:listaCompis) {
				System.out.println(compi);
			}
			
	
	}

}