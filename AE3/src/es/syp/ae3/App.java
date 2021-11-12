package es.syp.ae3;

public class App {

	public static Mina mina;
	public static Minero minero;

	public static void main(String[] args) {

		mina = new Mina(50);
		minero = new Minero();

		Thread hiloMinero;
		
		Ventilador ventilador = new Ventilador();

		Thread hiloVentiladorEncendido = new Thread(new Runnable() {
			@Override
			public void run() {
				ventilador.encenderVentilador();
			}
		});

		Thread hiloVentiladorApagado = new Thread(new Runnable() {
			@Override
			public void run() {
				ventilador.apagarVentilador();
			}
		});
		
		System.out.println("\n¡Ay-ho!¡Ay-ho! Es hora de currar~~~\n");
		
		hiloVentiladorEncendido.start();
		hiloVentiladorApagado.start();

		//Se generan 20 mineros (hilos de ejecución) y se ponen en marcha.
		for (int i = 0; i < 20; i++) {
			hiloMinero = new Thread(minero);
			hiloMinero.setName("Minero " + (i + 1));
			hiloMinero.start();
		}

		// Espera para mostrar el mensaje final.
		try {
			Thread.sleep(26500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\nTotal de oro extraído: " + minero.bolsa);
	}
 
}
