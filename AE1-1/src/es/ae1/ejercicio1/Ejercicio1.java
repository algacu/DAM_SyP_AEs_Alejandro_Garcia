package es.ae1.ejercicio1;

public class Ejercicio1 {

	public static void main(String[] args) {
		
		SayHello(); 
		
		App otraClase = new App(); 
		
		otraClase.SayHello2(); 

	}
	
	public static void SayHello() { 
		System.out.println("Hola Mundo");
	}

}


/* COMENTARIOS DEL EJERCICIO:
El enunciado no me debaja claro si tenía que hacer el método en una única 
clase o si tenía que desarrollarlo en una clase a parte y llamarlo desde 
la clase principal. Así que lo he hecho de las dos maneras.
*/
