package es.syp.ae2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanzador {
	
	
	// Método: main
	// Descripción: método principal. Lanza un mensaje de bienvenida y regula el tiempo entre procesos. Desde aquí se lanza el método Lector.
	// Parámetros de entrada: .
	// Parámetros de salida: .
	public static void main(String[] args) {
		
		long inicio, fin, tiempoTotal, tiempoMedio;
		
		int numeroNucleos = Runtime.getRuntime().availableProcessors();
		
		System.out.println("Bienvenido a N.I.C. (NEOs Impact Calculator)");
		System.out.println("Nº de nucleos disponibles: " + numeroNucleos);
		System.out.println("\nIniciado primera tanda de cálculos...\n");
		
		String nombreFichero = "NEOs.txt";
		
		inicio = System.currentTimeMillis();
		
		Lector(numeroNucleos, nombreFichero);
		
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
	
	
	// Método: Lector
	// Descripción: recibe el número de núcleos disponibles y el nombre del fichero de lectura a través 
	//	de la llamada en la clase main y lee las lineas del documento, llamando a la función Proceso y 
	//	pasándole los datos por parámetro. Divide el número de procesos "en lotes" según el número de núcleos.
	//	Cuando llega al número de llamadas (procesos) equivalente al número de nucleos, se realiza una 
	//	interrupción de 4 segundos para esperar a que terminen los procesos pendientes del lote anterior antes
	//	de empezar uno nuevo.
	// Parámetros de entrada: numero de núcleos, nombre del fichero a leer.
	// Parámetros de salida: .
	public static void Lector(int numeroNucleos, String nombreFichero) {
		
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
	
	
	// Método: Proceso
		// Descripción: recibe los datos pasados en el método Lector para armar un comando y ejecutarlo
		//	con proccesbuilder (apuntando al proceso de la clase CalculaNeo).
		// Parámetros de entrada: array de datos.
		// Parámetros de salida: .
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
