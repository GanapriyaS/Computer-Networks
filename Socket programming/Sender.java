import java.io.*;
import java.net.*;

public class Sender {
    private Socket sender = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    String str;

    public Sender(String address, int port) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Waiting for Connection....");
            sender = new Socket(address, port);
            out = new DataOutputStream(sender.getOutputStream());
            out.flush();
            in = new DataInputStream(sender.getInputStream());
            str = in.readUTF();
            System.out.println("receiver     > " + str);
            String line = "";
            while (!line.equals("Over")) {
                System.out.print("Enter the string :");
                line = br.readLine();
                out.writeUTF(line);
                System.out.println("receiver     > " + in.readUTF());
            }
            System.out.println("Quiting....");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                sender.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Sender s = new Sender("127.0.0.1", 5000);
    }
}