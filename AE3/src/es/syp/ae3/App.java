package es.syp.ae3;

public class App {

	public static Mina mina;

	public static void main(String[] args) {

		mina = new Mina(20);

		Minero minero = new Minero();
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

		hiloVentiladorEncendido.start();
		hiloVentiladorApagado.start();
		
		for (int i = 0; i < 10; i++) {
			hiloMinero = new Thread(minero);
			hiloMinero.setName("Minero " + (i + 1));
			hiloMinero.start();
		}

		// Espera para mostrar el mensaje final.
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.print("Total de oro extraído: " + Minero.oroExtraido);

	}

}
