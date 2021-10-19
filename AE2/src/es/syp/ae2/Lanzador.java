package es.syp.ae2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanzador {

	public static void main(String[] args) {
		
		int numeroNucleos = Runtime.getRuntime().availableProcessors();
		
		String clase = "es.syp.ae2.CalculaNEO";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = clase;
		
		File ficheroLectura = new File("NEOs.txt");
		
		try {
			
			int contador = 0;
			
			FileReader fr = new FileReader(ficheroLectura);
			BufferedReader br = new BufferedReader(fr);
			
			String linea = br.readLine();
			
			String[] datosNEO;
			
			

			while (linea != null) {
				
				List<String> command = new ArrayList<>();
				datosNEO = linea.split(",");
				command.add(javaBin);
				command.add("-cp");
				command.add(classpath);
				command.add(className);
				command.add(datosNEO[0]);
				command.add(datosNEO[1]);
				command.add(datosNEO[2]);
				ProcessBuilder builder = new ProcessBuilder(command);
				
				
				if (contador <= numeroNucleos) {

					try {
						builder.inheritIO().start();
					} catch (IOException e) {
						e.printStackTrace();
					}

					contador++;
					
				} else {
					
					System.out.println("Todos los núcleos están ocupados con procesos. Reiniciando contador de procesos.");

					try {
						builder.inheritIO().start();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					contador = 0;
					
				}
				
				linea = br.readLine();

			}
			
			br.close();
			
			System.out.println("PROGRAMA FINALIZADO.");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

}
