package Exercici6.E4;

import java.io.*;
import java.net.*;

public class Server6E4 {
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

    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(12345);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Servidor connectat " + grup);

        String msg = "";
        while (!exit) {
            // Rebre el paquet del servidor multicast
            byte[] buf = new byte[1000];
            DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
            ms.receive(paquetRebut);
            msg = new String(buf);

            String linea = msg.trim();
            // separar les dades
            String[] dades = linea.split(",");

            double resultado = realizarOperacion(dades);

            // Enviar el paquet al client
            DatagramPacket returnPacket = new DatagramPacket(msg.getBytes(), msg.length(), grup, 54321);
            ms.send(returnPacket);
        }
        ms.close();
        System.out.println("Socket tancat");
    }
}
