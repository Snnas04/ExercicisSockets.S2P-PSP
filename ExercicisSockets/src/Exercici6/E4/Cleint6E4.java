package Exercici6.E4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cleint6E4 {
    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(54321);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Client connectat " + grup);

        Scanner teclat = new Scanner(System.in);

        String cadena = "";
        while (!cadena.trim().equals("*")) {
            // enviar el paquet al servidor multicast
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

            cadena = num1 + "," + num2 + "," + op + "\n";

            DatagramPacket paquetEnviat = new DatagramPacket(cadena.getBytes(), cadena.length(), grup, 12345);
            ms.send(paquetEnviat);

            // rebre el paquet del servidor multicast
            byte[] buf = new byte[1000];
            DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
            ms.receive(paquetRebut);
            String msg = new String(buf);
            System.out.println("Rebut de " + paquetRebut.getAddress() + ":" + paquetRebut.getPort() + ": " + msg.trim());
        }

        ms.leaveGroup(grup);
        ms.close();
        System.out.println("Socket tancat ...");
        teclat.close();
    }
}
