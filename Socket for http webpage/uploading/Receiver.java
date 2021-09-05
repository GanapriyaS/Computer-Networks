import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class Receiver {
    ServerSocket receiver = null;
    Socket connection;
    InputStream in;
    DataInputStream din;

    Receiver() {
    }

    public void run() {
        try {

            receiver = new ServerSocket(4000);
            System.out.println("Server Waiting for image");
            connection = receiver.accept();
            System.out.println("Client connected.");

            in = connection.getInputStream();
            din = new DataInputStream(in);
            int len = din.readInt();
            System.out.println("Image Size: " + len / 1024 + "KB");
            byte[] data = new byte[len];
            din.readFully(data);
            InputStream image = new ByteArrayInputStream(data);
            BufferedImage bImage = ImageIO.read(image);

            JFrame f = new JFrame("Image");
            ImageIcon icon = new ImageIcon(bImage);
            JLabel l = new JLabel();
            l.setIcon(icon);
            f.add(l);
            
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                din.close();
                in.close();
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