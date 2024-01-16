package Exercici2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorE2 {
    static boolean exit = false;

    private static int getRnd() {
        return (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        int puertoDestino = 2222;
        int key = getRnd();

        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();

            while (!exit) {
                System.out.println("Conexion recibida!");

                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();

                // Convert the string to an integer for comparison
                int guess = Integer.parseInt(linea);
                OutputStream os = server.getOutputStream();
                PrintWriter pw = new PrintWriter(os);

                if (guess > key) {
                    pw.write("El numero es mes petit\n");
                    pw.flush();
                } else if (guess < key) {
                    pw.write("El numero es mes gran\n");
                    pw.flush();
                } else {
                    pw.write("Enhorabona, has guanyat!!!\n");
                    pw.flush();
                    exit = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error Server");
            e.printStackTrace();
        }
    }
}
