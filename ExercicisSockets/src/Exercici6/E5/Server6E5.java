package Exercici6.E5;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Server6E5 {
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

    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(12345);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Servidor connectat " + grup);

        String msg = "";
        while (!msg.trim().equals("*")) {
            // Rebre el paquet del servidor multicast
            byte[] buf = new byte[1000];
            DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
            ms.receive(paquetRebut);
            msg = new String(buf);

            msg = msg.trim();

            System.out.println("El cliente ha solicitado el archivo: " + msg);

            String fileContent = getFile(msg);

            // Enviar el paquet al client
            DatagramPacket returnPacket = new DatagramPacket(fileContent.getBytes(), fileContent.length(), grup, 54321);
            ms.send(returnPacket);

            System.out.println("Enviando archivo al cliente... " + fileContent);
        }
        ms.close();
        System.out.println("Socket tancat");
    }
}
