import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

class Download {
    InputStream in;
    OutputStream out;

    Download() {
    }

    public void run() {
        try {
            String fileName = "download.jpg";
            String website = "https://media.istockphoto.com/photos/green-leaf-with-dew-on-dark-nature-background-picture-id1050634172?s=612x612";
            System.out.println("Downloading File From: " + website);
            URL url = new URL(website);
            in = url.openStream();
            out = new FileOutputStream(fileName);
            byte[] buffer = new byte[2048];

            int length = 0;
            while ((length = in.read(buffer)) != -1) {
                System.out.println("Buffer Read of length: " + length);
                out.write(buffer, 0, length);
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Download d = new Download();
        d.run();
    }

}
