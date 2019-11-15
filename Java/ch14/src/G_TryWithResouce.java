import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class G_TryWithResouce {
    public static void main(String[] args) {
        Path file = Paths.get("D:\\Taejun\\Java\\test04.txt");
        try(BufferedWriter writer = Files.newBufferedWriter(file)){
            writer.write('A');      // IOException 발생 가능
            writer.write('Z');      // IOException 발생 가능
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
