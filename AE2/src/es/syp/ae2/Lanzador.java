package es.syp.ae2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lanzador {

	public static void main(String[] args) throws IOException {
		
		int numeroProcesadores = Runtime.getRuntime().availableProcessors();
		
		String clase = "es.syp.ae2.CalculaNEO";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = clase;
		
		List<String> command = new ArrayList<>();
		
		File fichero = new File(args[0]);

		FileReader fr = new FileReader(fichero);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();

		int contador = 0;

		while (linea != null) {

			List<String> datosNEO = new ArrayList<String>(Arrays.asList(linea.split(",")));

			if (contador <= numeroProcesadores) {
				command.add(javaBin);
				command.add("-cp");
				command.add(classpath);
				command.add(className);
				command.add(String.valueOf(datosNEO.get(0)));
				command.add(String.valueOf(datosNEO.get(1)));
				command.add(String.valueOf(datosNEO.get(2)));

				ProcessBuilder builder = new ProcessBuilder(command);

				try {
					builder.inheritIO().start();
				} catch (IOException e) {
					e.printStackTrace();
				}

				contador++;

			} else {
				contador = 0;
			}

			br.readLine();

		}
		
		br.close();
		
		System.out.println("PROGRAMA FINALIZADO.");
		
	}

}
