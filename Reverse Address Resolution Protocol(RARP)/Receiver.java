import java.io.*;
import java.net.*;
import java.util.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    DataOutputStream out;
    DataInputStream in;

    Receiver() {
    }

    public void run() {
        try {
            receiver = new ServerSocket(5604);
            System.out.println("Waiting for connection...");
            connection = receiver.accept();
            System.out.println("Connection established   ");
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            out.writeBytes("connected    .\n");
            String macaddr = in.readLine();
            String ip[] = { "165.165.80.80", "165.165.79.1" };
            String mac[] = { "6A:08:AA:C2", "8A:BC:E3:FA" };
            for (int i = 0; i < ip.length; i++) {
                if (macaddr.equals(mac[i])) {
                    out.writeBytes(ip[i] + '\n');
                    break;
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                in.close();
                out.close();
                receiver.close();
            } catch (Exception e) {
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