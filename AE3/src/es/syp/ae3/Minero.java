package es.syp.ae3;

public class Minero implements Runnable {

	int bolsa;
	int tiempoExtraccion;

	static int oroExtraido = 0;

	Minero() {
		this.bolsa = 0;
		this.tiempoExtraccion = 1000;
	}

	synchronized public void extraerRecurso(String nombre) throws InterruptedException {

//		System.out.println(nombre + " extrae 1 oro.");
//
//		while (App.mina.stock > 0) {
//			Thread.sleep(tiempoExtraccion);
//			App.mina.stock = App.mina.stock - 1;
//			bolsa++;
//			oroExtraido++;
//		}
//
//		if (App.mina.stock == 0) {
//			System.out.println(nombre + " quiere extraer oro... ¡Pero ya no queda!");
//			oroExtraido = oroExtraido - 1;
//		}
		
		if (oroExtraido <= App.mina.stock) {
			Thread.sleep(tiempoExtraccion);
			System.out.println(nombre + " extrae 1 oro.");
			App.mina.stock = App.mina.stock - 1;
			oroExtraido = oroExtraido + 1;
		} else {
			System.out.println(nombre + " quiere extraer oro... ¡Pero ya no queda!");
		}
		
	}

	@Override
	public void run() {
		String nombre = Thread.currentThread().getName();
		try {
			extraerRecurso(nombre);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
