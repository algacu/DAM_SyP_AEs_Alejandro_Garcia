package es.syp.ae3;

public class Minero implements Runnable {

	int bolsa;
	int tiempoExtraccion = 500;

	Minero() {
		this.bolsa = 0;
	}

	
	// Método: extraerRecurso
		// Descripción: Bucle que itera mientras la mina tenga oro. Se implementa un Thread.sleep a modo de
		// 	"tiempo de extracción" para establecer un ritmo de proceso. En cada iteración resta 1 unidad al stock (cantidad de oro)
		// 	de la mina y suma 1 unidad a la bolsa de los mineros. Al final, se ejecuta un wait de 0,2 segundos para alternar el proceso de los diferentes hilos en el bucle.
		// Parámetros de entrada: string.
		// Parámetros de salida: no.
	synchronized public void extraerRecurso(String nombre) throws InterruptedException {
		while (App.mina.getStock() > 0) {
				Thread.sleep(tiempoExtraccion);
				System.out.println(nombre + " extrae 1 oro.");
				App.mina.setStock(App.mina.getStock() - 1);
				App.minero.bolsa++;
				wait(200);
		}
	}

	
	// Método: run
		// Descripción: puesta en marcha de los hilos instanciados en la clase principal.
		//	Obtiene el nombre (string) de cada hilo y lo pasa como parámetro al método extraerRecurso() para su ejecución.
		// Parámetros de entrada: no.
		// Parámetros de salida: no.
	@Override
	synchronized public void run() {
		String nombre = Thread.currentThread().getName();

		try {
			extraerRecurso(nombre);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	
}
