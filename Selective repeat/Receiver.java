import java.io.*;
import java.net.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    DataOutputStream out;
    DataInputStream in;
    String packet, ack, data = "";
    int i = 0, sequence = 0;

    Receiver() {
    }

    public void run() {
        try {
            receiver = new ServerSocket(8011);
            System.out.println("waiting for connection...");
            connection = receiver.accept();
            System.out.println("Connection established   :");

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(connection.getInputStream());
            out.flush();
            int frame = in.read();
            int data[] = new int[frame];
            System.out.println("No of frame is:" + frame);

            for (int i = 0; i < frame; i++) {
                data[i] = in.read();
                System.out.println(data[i]);
            }
            data[0] = -1;

            for (int i = 0; i < frame; i++) {
                System.out.println("Received frame is: " + data[i]);

            }
            for (int i = 0; i < frame; i++) {
                if (data[i] == -1) {
                    System.out.println("\nRequest to retransmit packet no " + (i + 1) + " again!!");
                    out.write(i);
                    out.flush();
                    data[i] = in.read();
                    System.out.println("Received frame is: " + data[i]);
                }

            }
            System.out.println("quiting\n");
            in.close();
            out.close();
            connection.close();
            receiver.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                connection.close();
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