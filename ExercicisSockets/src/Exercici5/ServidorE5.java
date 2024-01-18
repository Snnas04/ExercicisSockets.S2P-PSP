package Exercici5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServidorE5 {
    static boolean exit = false;

    private static String getFile(String file) {
        Path path = Paths.get(file);

        try {
            if (Files.exists(path)) {
                StringBuilder contenido = new StringBuilder();
                Files.lines(path).forEach(linea -> contenido.append(linea).append("\n"));
                return contenido.toString();
//                return new String(Files.readAllBytes(path));
            } else {
                return "Error: No s'ha trobat l'arxiu\n" + "Pensa ha indicar l'extensió del fitxer si no ho has fet";
            }
        } catch (IOException e) {
            return "No s'ha pogut llegir l'arxiu";
        }
    }

    public static void main(String[] args) {
        int puertoDestino = 1500;
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
                System.out.println("El cliente ha solicitado el archivo: " + linea);

                // Obtenir contigut del fitxer
                String fileContent = getFile(linea);

                System.out.println("Enviando archivo al cliente... " + fileContent);

                OutputStream os = server.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.write(fileContent + "\n");
                pw.flush();
            }
        } catch (IOException e) {
            System.out.println("Error Server");
        }
    }
}
