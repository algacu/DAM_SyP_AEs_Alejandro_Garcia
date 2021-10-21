package es.syp.ae2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CalculaNEO {

	public static void main(String[] args) {
		
		String nombre = args[0];
		double posicionNEO = Double.valueOf(args[1]);
		double velocidadNEO = Double.valueOf(args[2]);
		double posicionTierra = 1;
		double velocidadTierra = 100;
		
		for (int i = 0; i < (50 * 365 * 24 * 60 * 60); i++) {
		posicionNEO = posicionNEO + velocidadNEO * i;
		posicionTierra = posicionTierra + velocidadTierra * i;
		}
		
		double resultado = 100 * Math.random() * Math.pow( ((posicionNEO-posicionTierra)/(posicionNEO+posicionTierra)), 2);
		
		NumberFormat formateadorDecimal = new DecimalFormat("#0.00");     
		
		String resultadoString = formateadorDecimal.format(resultado);
		
		String nombreFichero = nombre + ".txt";
		File ficheroEscritura = new File(nombreFichero);
		
		try {
			FileWriter fw = new FileWriter(ficheroEscritura);
			fw.write(resultadoString + "%");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (resultado >= 10.00) {
			System.err.println("¡¡ALERTA CRÍTICA!! La probabilidad de colisión de " + nombre + " es de " + resultadoString + "%");
		} else {
			System.out.println(nombre + " - Probabilidad de colisión: " + resultadoString + "% - OK");
		}
		
	}
	
}
