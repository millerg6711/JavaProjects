import java.io.*;

public class Files {
    public static void main(String[] args) {
        try (
            FileInputStream in = new FileInputStream(new File(""));
            FileOutputStream out = new FileOutputStream(new File(""));
        ) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
