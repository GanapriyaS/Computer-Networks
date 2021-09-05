import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.MatchResult;

public class Sender {
    Socket sender;
    DataInputStream in;
    DataOutputStream out;
    String str;

    Sender() {
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Waiting for Connection....");
            sender = new Socket("localhost", 5604);
            in = new DataInputStream(sender.getInputStream());
            out = new DataOutputStream(sender.getOutputStream());
            out.flush();
            str = in.readLine();
            System.out.println("receiver     > " + str);
            System.out.println("Enter the  Physical address(MAC):");
            String mac = br.readLine();
            out.writeBytes(mac + '\n');
            String ip = in.readLine();
            System.out.println("The Logical Address(IP) is: " + ip);

        } catch (Exception e) {
        } finally {
            try {
                in.close();
                out.close();
                sender.close();
            } catch (Exception e) {
            }
        }
    }

    public static void main(String args[]) {
        Sender s = new Sender();
        s.run();
    }
}