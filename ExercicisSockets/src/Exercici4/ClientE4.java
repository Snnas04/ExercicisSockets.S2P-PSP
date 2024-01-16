package Exercici4;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientE4 {
    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        return bfr;
    }

    public static void main(String[] args) {
        String destino = "localhost";
        int puertoDestino = 2000;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(destino, puertoDestino);

        try {
            socket.connect(direccion);

            System.out.println("Indica el primer numero:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String num1 = reader.readLine();
            System.out.println("Indica el segon numero:");
            String num2 = reader.readLine();
            System.out.println("Indica l'operacio que vols realitzar:\n" +
                    "   -> Suma +\n" +
                    "   -> Resta -\n" +
                    "   -> Multiplicacio *\n" +
                    "   -> Divisio /");
            String op = reader.readLine();

            // Enviar les dades
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print(num1 + "," + num2 + "," + op + "\n");
            pw.flush();

            // Rebre el resultat
            BufferedReader bfr = ClientE4.getFlujo(socket.getInputStream());
            System.out.println("Resultat de l'operació: " + bfr.readLine());

            socket.close();

        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }
}
