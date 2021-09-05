import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Sender {
    BufferedImage img = null;
    Socket sender;
    OutputStream out;
    DataOutputStream dout;
    ByteArrayOutputStream baos;

    Sender() {
    }

    public void run() {

        try {
            sender = new Socket("localhost", 4000);
            System.out.println("Client is running. ");
            System.out.println("Reading image from disk. ");
            img = ImageIO.read(new File("img.jpg"));

            baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();

            byte[] bytes = baos.toByteArray();

            System.out.println("Sending image to server. ");
            out = sender.getOutputStream();
            dout = new DataOutputStream(out);

            dout.writeInt(bytes.length);
            dout.write(bytes, 0, bytes.length);
            System.out.println("Image sent to server. ");

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                baos.close();
                out.close();
                sender.close();
                dout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Sender s = new Sender();
        s.run();
    }

}