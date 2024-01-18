package Exercici6.E1;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Cleint6E1 {
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
            System.out.print("Dades per enviar al grup: ");
            cadena = teclat.nextLine();
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
