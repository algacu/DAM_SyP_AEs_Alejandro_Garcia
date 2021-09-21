package es.ae1.ejercicio1;

public class Ejercicio1 {

	public static void main(String[] args) {
		
		sayHello(); //Llamo al m�todo de la misma clase.
		
		App otraClase = new App(); //Genero objeto de otra clase.
		
		otraClase.sayHello2(); //Llamo al m�todo de otra clase.

	}
	
	public static void sayHello() { //M�todo indicado dentro de la misma clase principal.
		System.out.println("Hola Mundo");
	}

}


/* COMENTARIOS DEL EJERCICIO:
El enunciado no me debaja claro si ten�a que hacer el m�todo en una �nica 
clase o si ten�a que desarrollarlo en una clase a parte y llamarlo desde 
la clase principal. As� que lo he hecho de las dos maneras.
*/
