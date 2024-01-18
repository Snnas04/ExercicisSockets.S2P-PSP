package Exercici6.E1;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Server6E1 {
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
            msg = new String(buf).toUpperCase();

            // Enviar el paquet al client
            DatagramPacket returnPacket = new DatagramPacket(msg.getBytes(), msg.length(), grup, 54321);
            ms.send(returnPacket);
        }
        ms.close();
        System.out.println("Socket tancat");
    }
}
