package Exercici6.E3;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server6E3 {
    private static void guardarDireccionEnArchivo(JSONObject json) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("direcciones.txt", true))) {
            writer.println(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
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
        String confim = "OK";

        byte[] buf = new byte[1000];
        DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
        ms.receive(paquetRebut);
        msg = new String(buf);

        // Convertir cadena a objecte JSON
        JSONObject json = new JSONObject(msg);

        // Guardar la informacio a l'arxiu i enviar "OK"
        guardarDireccionEnArchivo(json);

        // Enviar el paquet al client
        DatagramPacket returnPacket = new DatagramPacket(confim.getBytes(), confim.length(), grup, 54321);
        ms.send(returnPacket);
        ms.close();
        System.out.println("Socket tancat");
    }
}
