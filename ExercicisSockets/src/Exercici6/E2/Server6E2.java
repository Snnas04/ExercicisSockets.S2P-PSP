package Exercici6.E2;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server6E2 {
    static boolean exit = false;

    private static int getRnd() {
        return (int) (Math.random() * 100);
    }

    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(12345);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Servidor connectat " + grup);

        int key = getRnd();
        int guess;
        String petit = "El numero es mes petit\n";
        String gran = "El numero es mes gran\n";
        String igual = "Enhorabona, has guanyat!!!\n";
        String msg = "";

        System.out.println(key);
        while (!exit) {
            // Rebre el paquet del servidor multicast
            byte[] buf = new byte[1000];
            DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
            ms.receive(paquetRebut);
            msg = new String(buf);

            guess = Integer.parseInt(msg.trim());
            DatagramPacket mesPetit = new DatagramPacket(petit.getBytes(), petit.length(), grup, 54321);
            DatagramPacket mesGran = new DatagramPacket(gran.getBytes(), gran.length(), grup, 54321);
            DatagramPacket guanyat = new DatagramPacket(igual.getBytes(), igual.length(), grup, 54321);

            if (guess > key) {
                ms.send(mesPetit);
            } else if (guess < key) {
                ms.send(mesGran);
            } else {
                ms.send(guanyat);
                exit = true;
            }
        }
        ms.close();
        System.out.println("Socket tancat");
    }
}
