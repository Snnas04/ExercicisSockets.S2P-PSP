package Exercici1;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientE1 {
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
            while (true) {
                System.out.println("Client text:");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String txt = reader.readLine();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.print(txt + "\n");
                pw.flush();
                BufferedReader bfr = ClientE1.getFlujo(socket.getInputStream());
                System.out.println("Resposta del servidor: " + bfr.readLine());
                //socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }
}
