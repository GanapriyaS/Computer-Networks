import java.io.*;
import java.net.*;

public class Sender {
    Socket sender;
    DataOutputStream out;
    DataInputStream in;
    String packet, ack, str, msg;
    int n, i = 0, sequence = 0;

    Sender() {
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            sender = new Socket("localhost", 8011);
            out = new DataOutputStream(sender.getOutputStream());
            out.flush();
            in = new DataInputStream(sender.getInputStream());
            System.out.print("Enter the number of frames : ");
            int frame = Integer.parseInt(br.readLine());
            int data[] = new int[frame];
            for (int i = 0; i < frame; i++) {
                System.out.print("Enter data for frame no " + i + ": ");
                data[i] = Integer.parseInt(br.readLine());
            }
            System.out.println("The number of packets sent is:" + frame);
            out.write(frame);
            out.flush();

            for (int i = 0; i < frame; i++) {
                out.write(data[i]);
                out.flush();
            }

            int k = in.read();

            out.write(data[k]);
            out.flush();

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                in.close();
                out.close();
                sender.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    public static void main(String args[]) {
        Sender s = new Sender();
        s.run();
    }
}