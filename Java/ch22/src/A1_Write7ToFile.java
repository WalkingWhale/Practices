import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class A1_Write7ToFile {
    public static void main(String[] args) throws IOException {
        OutputStream out = new FileOutputStream("data.dat");
        out.write(65);      // ASCII 코드 65 = 'A'
        out.close();
    }
}
