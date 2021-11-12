package es.syp.ae3;

public class Mina {
	
	int stock;
	
	Mina(int stock) {
		this.stock = stock;
	}
	
	synchronized public int getStock() {
		return this.stock;
	}
	
	synchronized public void setStock(int nuevoStock) {
		this.stock = nuevoStock;
	}

}
