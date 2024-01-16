package Exercici3;

import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientE3 {
    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        return bfr;
    }

    public static void main(String[] args) {
        String destino = "localhost";
        int puertoDestino = 2222;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(destino, puertoDestino);

        try {
            socket.connect(direccion);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Introdueix les dades d'una direccio");
            System.out.println("Carrer: ");
            String carrer = reader.readLine();
            System.out.println("Codi Postal: ");
            String cp = reader.readLine();
            System.out.println("Pais: ");
            String pais = reader.readLine();
            System.out.println("Nombre Casa: ");
            String numCasa = reader.readLine();


            // Crear un objeto JSON amb informacio
            JSONObject json = new JSONObject();
            json.put("Carrer", carrer);
            json.put("CodiPostal", cp);
            json.put("Pais", pais);
            json.put("NombreCasa", numCasa);

            // Convertir es JSON a una cadena i enviarlo al servidor
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print(json.toString() + "\n");
            pw.flush();

            // Esperar la resosta del servidor
            BufferedReader bfr = ClientE3.getFlujo(socket.getInputStream());
            System.out.println(bfr.readLine());

            socket.close();
        } catch (IOException e) {
            System.out.println("Error Client");
            e.printStackTrace();
        }
    }
}
