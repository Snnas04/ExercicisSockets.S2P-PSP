package Exercici6.E2;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Cleint6E2 {
    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(54321);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Client connectat " + grup);
        System.out.println("Adivina un numero entre 0 i 100");

        Scanner teclat = new Scanner(System.in);

        String cadena = "";
        while (!cadena.trim().equals("*")) {
            // enviar el paquet al servidor multicast
            System.out.print("Indica un numero: ");
            cadena = teclat.nextLine();
            DatagramPacket paquetEnviat = new DatagramPacket(cadena.getBytes(), cadena.length(), grup, 12345);
            ms.send(paquetEnviat);

            // rebre el paquet del servidor multicast
            byte[] buf = new byte[1000];
            DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
            ms.receive(paquetRebut);
            String msg = new String(buf);

            if (msg.trim().equals("Enhorabona, has guanyat!!!")) {
                System.out.println(msg.trim());
                break;
            }

            System.out.println(msg.trim());
        }

        ms.leaveGroup(grup);
        ms.close();
        System.out.println("Socket tancat ...");
        teclat.close();
    }
}
