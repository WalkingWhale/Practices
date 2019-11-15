import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class A5_Write7ToFile3 {
    public static void main(String[] args) {
        try(OutputStream out = new FileOutputStream("data.dat")){
            out.write(67);      // ASCII 코드 67 = 'C'
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
