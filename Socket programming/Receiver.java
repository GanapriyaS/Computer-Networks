import java.io.*;
import java.net.*;

public class Receiver {
    private Socket connection = null;
    private ServerSocket receiver = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Receiver(int port) {
        try {
            receiver = new ServerSocket(port);
            System.out.println("waiting for connection...");
            connection = receiver.accept();
            System.out.println("Connection established   :");
            out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            in = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
            out.writeUTF("connected");
            String line = "";
            while (!line.equals("Over")) {
                line = in.readUTF();
                System.out.println(line);
                out.writeUTF("received");
            }
            System.out.println("Closing connection");
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
        Receiver r = new Receiver(5000);
    }
}