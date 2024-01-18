package Exercici6.E3;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Cleint6E3 {
    public static void main(String[] args) throws Exception {
//Sino agafa IPv6!
        System.setProperty("java.net.preferIPv4Stack", "true");
        MulticastSocket ms = new MulticastSocket(54321);
        InetAddress grup = InetAddress.getByName("225.0.0.1");
        ms.joinGroup(grup);
        System.out.println("Client connectat " + grup);

        Scanner teclat = new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // enviar el paquet al servidor multicast
        System.out.println("Introdueix les dades d'una direccio");
        System.out.println("Carrer: ");
        String carrer = reader.readLine();
        System.out.println("Codi Postal: ");
        String cp = reader.readLine();
        System.out.println("Pais: ");
        String pais = reader.readLine();
        System.out.println("Nombre Casa: ");
        String numCasa = reader.readLine();

        // Crear un objeto JSON amb informacio
        JSONObject json = new JSONObject();
        json.put("Carrer", carrer);
        json.put("CodiPostal", cp);
        json.put("Pais", pais);
        json.put("NombreCasa", numCasa);

        String jsonToSend = json.toString();

        DatagramPacket paquetEnviat = new DatagramPacket(jsonToSend.getBytes(), jsonToSend.length(), grup, 12345);
        ms.send(paquetEnviat);

        // rebre el paquet del servidor multicast
        byte[] buf = new byte[1000];
        DatagramPacket paquetRebut = new DatagramPacket(buf, 1000);
        ms.receive(paquetRebut);
        String msg = new String(buf);
        System.out.println(msg.trim());

        ms.leaveGroup(grup);
        ms.close();
        System.out.println("Socket tancat ...");
        teclat.close();
    }
}
