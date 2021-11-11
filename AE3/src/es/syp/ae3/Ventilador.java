package es.syp.ae3;

public class Ventilador {

	boolean encendido;
	int tiempo;
	
	Ventilador(){
		encendido = true;
		tiempo = 2000;
	}

	public void encenderVentilador() {
		while(true) {
			synchronized(this) {
				try {
					while (encendido == true) wait();
					System.err.println("Ventilador ENCENDIDO durante " + tiempo / 1000 + " segundos.");
					Thread.sleep(tiempo);
					encendido = false;
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void apagarVentilador() {
		while(true) {
			synchronized(this) {
				try {
					while (encendido == false) wait();
					System.err.println("Ventilador APAGADO durante " + tiempo / 1000 + " segundos.");
					Thread.sleep(tiempo);
					encendido = true;
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

}
