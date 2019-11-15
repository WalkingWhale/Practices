import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class F1_FinallyCase1 {

    public static void main(String[] args) {
        Path file = Paths.get("D:\\Taejun\\Java\\test02.txt");

        BufferedWriter writer = null;

        try {
            //아래 문장에서 IOExceotion 발생가능
            writer = Files.newBufferedWriter(file);

            writer.write('A');  //IOExceotion 발생가능
            writer.write('Z');  //IOExceotion 발생가능

//            writer.close();       //IOExceotion 발생가능
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
//            if(writer != null){
//            writer.close();       //IOExceotion 발생가능
//            }
        }
    }

}
