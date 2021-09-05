import java.util.*;
import java.net.*;
import java.io.*;

public class Sender {
    Socket sender;
    ObjectOutputStream out;
    ObjectInputStream in;
    String packet, ack, str, msg;
    int n, i = 0, sequence = 0;

    Sender() {
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the value of m : ");
            int m = Integer.parseInt(br.readLine());
            int x = (int) ((Math.pow(2, m)) - 1);
            System.out.print("Enter no. of frames to be sent:");
            int count = Integer.parseInt(br.readLine());
            int data[] = new int[count];
            int h = 0;
            for (int i = 0; i < count; i++) {
                System.out.print("Enter data for frame no " + h + " => ");
                data[i] = Integer.parseInt(br.readLine());
                h = (h + 1) % x;
            }
            sender = new Socket("localhost", 6262);
            ObjectInputStream in = new ObjectInputStream(sender.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(sender.getOutputStream());
            System.out.println("Connected with server.");
            boolean flag = false;
            listener listener = new listener(in, x);
            listener = new listener(in, x);
            listener.t.start();
            int strt = 0;
            h = 0;
            out.writeObject(x);
            do {
                h = strt;
                for (int i = strt; i < x; i++) {
                    System.out.print("Sending frame:" + h);
                    h = (h + 1) % x;
                    System.out.println();
                    out.writeObject(i);
                    out.writeObject(data[i]);
                    Thread.sleep(100);
                }
                listener.t.join(3500);
                if (listener.reply != x - 1) {
                    System.out.println("No reply from server in 3.5 seconds. Resending data from frame no "
                            + (listener.reply + 1));
                    System.out.println();
                    strt = listener.reply + 1;
                    flag = false;
                } else {
                    System.out.println("All elements sent successfully. Exiting...");
                    flag = true;
                }
            } while (!flag);
            out.writeObject(-1);
        }

        catch (Exception e) {
            try {
                sender.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Sender s = new Sender();
        s.run();
    }
}

class listener implements Runnable {
    Thread t;
    ObjectInputStream in;
    int reply, x;

    listener(ObjectInputStream o, int i) {
        t = new Thread(this);
        in = o;
        reply = -2;
        x = i;
    }

    @Override
    public void run() {
        try {
            int temp = 0;
            while (reply != -1) {
                reply = (Integer) in.readObject();
                if (reply != -1 && reply != temp + 1)
                    reply = temp;
                if (reply != -1) {
                    temp = reply;
                    System.out.println("Acknowledgement of frame no " + (reply % x) + " recieved.");
                    System.out.println();
                }
            }
            reply = temp;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}