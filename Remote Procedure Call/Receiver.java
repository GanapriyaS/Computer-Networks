import java.io.*;
import java.net.*;

public class Receiver {
    ServerSocket receiver;
    Socket connection = null;
    OutputStream out;
    InputStream in;
    String operation;
    BufferedReader br;
    PrintWriter pw;

    Receiver() {
    }

    public void run() {
        try {
            receiver = new ServerSocket(3000);
            System.out.println("Waiting for connection");
            connection = receiver.accept();
            System.out.println("Connection established   :");
            out = connection.getOutputStream();
            pw = new PrintWriter(out, true);
            in = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));

            int a, b, c;
            while (true) {
                operation = br.readLine();
                if (operation != null)
                    System.out.println("Operation : " + operation);
                a = Integer.parseInt(br.readLine());
                System.out.println("Parameter 1 : " + a);
                b = Integer.parseInt(br.readLine());
                System.out.println("Parameter 2 : " + b);
                if (operation.compareTo("add") == 0) {
                    c = a + b;
                    System.out.println("Addition = " + c);
                    pw.println("Addition = " + c);
                }
                if (operation.compareTo("sub") == 0) {
                    c = a - b;
                    System.out.println("Substraction = " + c);
                    pw.println("Substraction = " + c);
                }
                if (operation.compareTo("mul") == 0) {
                    c = a * b;
                    System.out.println("Multiplication = " + c);
                    pw.println("Multiplication = " + c);
                }
                if (operation.compareTo("div") == 0) {
                    c = a / b;
                    System.out.println("Division = " + c);
                    pw.println("Division = " + c);
                }
                System.out.flush();
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
