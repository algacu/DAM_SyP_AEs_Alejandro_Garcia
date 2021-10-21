package es.syp.ae2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanzador {
	

	public static void main(String[] args) {
		
		long inicio, fin, tiempoTotal, tiempoMedio;
		
		int numeroNucleos = Runtime.getRuntime().availableProcessors();
		
		System.out.println("Bienvenido a N.I.C. (NEOs Impact Calculator)");
		System.out.println("Nº de nucleos disponibles: " + numeroNucleos);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("\nIniciado primera tanda de cálculos...\n");
		
		inicio = System.currentTimeMillis();
		
		Lector(numeroNucleos);
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fin = System.currentTimeMillis();
		tiempoTotal = fin - inicio;
		tiempoMedio = tiempoTotal / numeroNucleos;
		
		System.out.println("\nPrograma terminado. Tiempo total: " + (double) tiempoTotal/1000 + " seg. Tiempo medio por proceso: " + (double) tiempoMedio/1000 + " seg.");
		
		
	}
	
	
	public static void Lector(int numeroNucleos) {
		
		String nombreFichero = "NEOs.txt";
		
		File ficheroLectura = new File(nombreFichero);
		
		try {
			FileReader fr = new FileReader(ficheroLectura);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			
			String[] datosNEO;
						
			int contadorProcesos = 1;

			while (linea !=null) {
				
				if (contadorProcesos <= numeroNucleos) {
					datosNEO = linea.split(",");
					Proceso(datosNEO);
					contadorProcesos++;
				} else {
					
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					System.out.println("\nIniciado siguiente tanda de cálculos...\n");
					datosNEO = linea.split(",");
					Proceso(datosNEO);
					contadorProcesos = 1;
				}
				
				linea = br.readLine();
			}

			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void Proceso(String[] datos) {
		
		String clase = "es.syp.ae2.CalculaNEO";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = clase;
		
		List<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-cp");
		command.add(classpath);
		command.add(className);
		command.add(datos[0]);
		command.add(datos[1]);
		command.add(datos[2]);
		ProcessBuilder builder = new ProcessBuilder(command);

		try {
			builder.inheritIO().start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

}
