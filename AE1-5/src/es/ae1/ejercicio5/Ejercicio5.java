package es.ae1.ejercicio5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ejercicio5 {

	public static void main(String[] args) {
		
		List<Integer> listaNumeros = new ArrayList<Integer>();
		
		listaNumeros.add(68);
		listaNumeros.add(1);
		listaNumeros.add(34);
		listaNumeros.add(15);
		listaNumeros.add(84);
		listaNumeros.add(53);
		listaNumeros.add(28);
		listaNumeros.add(97);
		listaNumeros.add(72);
		listaNumeros.add(45);
		
		System.out.println(listaNumeros);
		
		System.out.println("\nEl número mayor de la lista es: " + DevuelveMayor(listaNumeros));
		
		System.out.println("(Sacado de otra forma) El número mayor de la lista es: " + DevuelveMayorOtraForma(listaNumeros));

	}
	
	
	public static int DevuelveMayor(List<Integer> lista) {
		
		int numeroMayor = Collections.max(lista);
		
		return numeroMayor;
		
	}
	
	
	public static int DevuelveMayorOtraForma(List<Integer> lista) {
		
		int numeroMayor = 0;
		
		for (int i = 0; i < lista.size()-1; i++) {

            if (lista.get(i) > numeroMayor){
                numeroMayor = lista.get(i);
            }
        }
			
		return numeroMayor;
			
	}

}
