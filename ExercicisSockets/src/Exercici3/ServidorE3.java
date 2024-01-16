package Exercici3;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorE3 {
    static boolean exit = false;

    private static void guardarDireccionEnArchivo(JSONObject json) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("direcciones.txt", true))) {
            writer.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int puertoDestino = 2222;

        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();

            System.out.println("Conexion recibida!");

            InputStream is = server.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(isr);
            String linea = bf.readLine();

            // Convertir cadena a objecte JSON
            JSONObject json = new JSONObject(linea);

            // Guardar la informacio a l'arxiu i enviar "OK"
            guardarDireccionEnArchivo(json);

            OutputStream os = server.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("OK\n");
            pw.flush();

            server.close();

        } catch (IOException e) {
            System.out.println("Error Server");
            e.printStackTrace();
        }
    }
}
