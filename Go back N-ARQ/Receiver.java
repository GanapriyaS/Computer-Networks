import java.io.*;
import java.net.*;
import java.util.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;

    Receiver() {
    }

    public void run() {
        try {
            receiver = new ServerSocket(6262);
            System.out.println("waiting for connection...");
            connection = receiver.accept();
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
            System.out.println("Connection established   :");
            int x = (Integer) in.readObject();
            int k = (Integer) in.readObject();
            int j = 0;
            int i = (Integer) in.readObject();
            boolean flag = true;
            Random r = new Random(6);
            int mod = r.nextInt(6);
            while (mod == 1 || mod == 0)
                mod = r.nextInt(6);
            while (true) {
                int c = k;
                for (int h = 0; h <= x; h++) {
                    System.out.print("|" + c + "|");
                    c = (c + 1) % x;
                }
                if (k == j) {
                    System.out.println("\nFrame " + k + " recieved" + "\n" + "Data:" + j);
                    j++;
                }

                else
                    System.out.println("\nFrames recieved not in correct order" + "\n" + " Expected farme:" + j + "\n"
                            + " Recieved frame no :" + k);
                if (j % mod == 0 && flag) {
                    System.out.println("Error found. Acknowledgement not sent. ");
                    flag = !flag;
                    j--;
                } else if (k == j - 1) {
                    out.writeObject(k);
                    System.out.println("Acknowledgement sent");
                }
                System.out.println();
                if (j % mod == 0)
                    flag = !flag;
                k = (Integer) in.readObject();
                if (k == -1)
                    break;
                i = (Integer) in.readObject();
            }
            System.out.println("Client finished sending data. Exiting...");
            out.writeObject(-1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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