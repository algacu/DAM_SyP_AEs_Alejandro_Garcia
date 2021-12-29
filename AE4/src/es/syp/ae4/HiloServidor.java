package es.syp.ae4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HiloServidor {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {

        int puerto = 1234;

        System.err.println("SERVIDOR >> Arranca el servidor. Escuchando...");
        ServerSocket socket = null;

        try {
            socket = new ServerSocket(puerto);
        } catch (IOException e) {
            System.err.println("SERVIDOR >> Error");
            return;
        }

        while (true) {
            Socket conexion = socket.accept();
            System.err.println("\nSERVIDOR >> Cliente recibido --> Inicio hilo");
            Servidor servidor = new Servidor(conexion);
            Thread hilo = new Thread(servidor);
            hilo.start();
        }
    }
}
