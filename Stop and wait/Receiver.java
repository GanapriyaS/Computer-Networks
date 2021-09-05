import java.io.*;
import java.net.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String packet, ack, data = "";
    int i = 0, sequence = 0;

    Receiver() {
    }

    public void run() {
        try {
            receiver = new ServerSocket(2005, 10);
            System.out.println("waiting for connection...");
            connection = receiver.accept();
            sequence = 0;
            System.out.println("Connection established   :");
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            out.writeObject("connected    .");

            do {
                try {
                    packet = (String) in.readObject();
                    if (Integer.valueOf(packet.substring(0, 1)) == sequence) {
                        data += packet.substring(1);
                        sequence = (sequence == 0) ? 1 : 0;
                        System.out.println("\nreceiver         >" + packet);
                    } else {
                        System.out.println("\nreceiver         >" + packet + "   duplicate data");
                    }
                    out.writeObject(String.valueOf(sequence));
                } catch (Exception e) {
                }
            } while (!packet.equals("end"));

            System.out.println("Data received=" + data);
            out.writeObject("connection ended    .");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                receiver.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Receiver s = new Receiver();
        while (true) {
            s.run();
        }
    }
}