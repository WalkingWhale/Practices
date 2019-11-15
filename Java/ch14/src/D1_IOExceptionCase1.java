import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class D1_IOExceptionCase1 {
    public static void main(String[] args) {
        Path file = Paths.get("D:\\Taejun\\Java\\test.txt");
        BufferedWriter writer = null;

        /*
        writer = Files.newBufferedWriter(file);

        writer.write('A');
        writer.write('Z');

        if (writer != null) {
            writer.close();
        }
        */

    }
}