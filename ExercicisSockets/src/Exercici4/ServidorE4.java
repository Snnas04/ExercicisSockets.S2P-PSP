package Exercici4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorE4 {
    static boolean exit = false;

    private static double realizarOperacion(String[] dades) {
        double num1 = Double.parseDouble(dades[0]);
        double num2 = Double.parseDouble(dades[1]);
        String operacion = dades[2];

        switch (operacion) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        int puertoDestino = 2000;

        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();

            while (!exit) {
                System.out.println("Conexion recibida!");

                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();

                // separar les dades
                String[] dades = linea.split(",");

                // Realizar la operacio
                double resultado = realizarOperacion(dades);

                // Enviar el resultat
                OutputStream os = server.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write(String.valueOf(resultado) + "\n");
                pw.flush();
            }
        } catch (IOException e) {
            System.out.println("Error Server");
        }
    }
}
