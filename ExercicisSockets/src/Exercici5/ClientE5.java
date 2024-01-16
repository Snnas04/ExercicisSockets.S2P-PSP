package Exercici5;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientE5 {
    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        return bfr;
    }

    public static void main(String[] args) {
        String destino = "localhost";
        int puertoDestino = 1500;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(destino, puertoDestino);
        try {
            socket.connect(direccion);
            System.out.println("Introdueix el nom del fitxer que vols visualitzar:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String file = reader.readLine();
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print(file + "\n");
            pw.flush();
            BufferedReader bfr = ClientE5.getFlujo(socket.getInputStream());
            System.out.println(bfr.readLine());
            socket.close();
        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }
}
