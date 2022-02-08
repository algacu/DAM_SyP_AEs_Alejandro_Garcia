package es.syp.ae5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpServer;

public class Servidor {
	
	//Servidor en escucha. 
	//Una vez ejecutado, accedemos a http://localhost:7777/test?name=ale

	public static void main(String[] args) throws IOException {
		
		//Configuramos los parámetros básicos del servidor.
		String host = "localhost"; // IP local: 127.0.0.1
		int puerto = 7777;
		InetSocketAddress direccionTCPIP = new InetSocketAddress(host, puerto);
		int backlog = 0; //número de conexiones pendientes que el servidor puede mantener en espera.
		HttpServer servidor = HttpServer.create(direccionTCPIP, backlog);
		
		//Implementamos el gestor de consultas (GETs, POSTs, etc.).
		GestorHTTP gestorHTTP = new GestorHTTP();
		//Ruta en la que el servidor aceptará peticiones.
		String ruta = "/estufa";
		//Asociamos la ruta de peticiones al gestor HTTP.
		servidor.createContext(ruta, gestorHTTP);
		
		//Opción no multihilo
		//servidor.setExecutor(null);
		
		//Opción multihilo (ThreadPoolExecutor)
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		servidor.setExecutor(threadPoolExecutor);
		
		servidor.start();
		System.out.println("Servidor HTPP en escucha. Puerto: " + puerto);

	}

}
