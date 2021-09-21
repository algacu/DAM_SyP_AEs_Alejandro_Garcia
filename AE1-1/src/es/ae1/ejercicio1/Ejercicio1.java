package es.ae1.ejercicio1;

public class Ejercicio1 {

	public static void main(String[] args) {
		
		sayHello(); //Llamo al método de la misma clase.
		
		App otraClase = new App(); //Genero objeto de otra clase.
		
		otraClase.sayHello2(); //Llamo al método de otra clase.

	}
	
	public static void sayHello() { //Método indicado dentro de la misma clase principal.
		System.out.println("Hola Mundo");
	}

}


/* COMENTARIOS DEL EJERCICIO:
El enunciado no me debaja claro si tenía que hacer el método en una única 
clase o si tenía que desarrollarlo en una clase a parte y llamarlo desde 
la clase principal. Así que lo he hecho de las dos maneras.
*/
