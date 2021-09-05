import java.io.*;
import java.net.*;

public class Sender {
    Socket sender;
    BufferedReader br;
    BufferedReader br1;
    OutputStream out;
    PrintWriter pw;

    Sender() {
    }

    public void run() {
        try {
            sender = new Socket("127.0.0.1", 3000);
            br = new BufferedReader(new InputStreamReader(System.in));
            out = sender.getOutputStream();
            pw = new PrintWriter(out, true);
            InputStream istream = sender.getInputStream();
            br1 = new BufferedReader(new InputStreamReader(istream));
            System.out.println("Client ready, type and press Enter key");
            String receiveMsg, sendMsg, temp;
            while (true) {
                System.out.println("\nEnter operation to perform(add,sub,mul,div) :");
                temp = br.readLine();
                sendMsg = temp.toLowerCase();
                pw.println(sendMsg);
                System.out.println("Enter first parameter :");
                sendMsg = br.readLine();
                pw.println(sendMsg);
                System.out.println("Enter second parameter : ");
                sendMsg = br.readLine();
                pw.println(sendMsg);
                System.out.flush();
                if ((receiveMsg = br1.readLine()) != null)
                    System.out.println(receiveMsg);
            }
        } catch (Exception e) {
        } finally {
            try {
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
