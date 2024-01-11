package Exercici1;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    static boolean exit = false;
    public static void main(String[] args) {
        int puertoDestino = 2222;
        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();
            while (!exit) {
                System.out.println("Conexion recibida!");
                //Read From Stream
                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();
                OutputStream os = server.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write(linea.toUpperCase() + "\n");
                pw.flush();
            }
        } catch (IOException e) {
            System.out.println("Error Server");
        }
    }
}
