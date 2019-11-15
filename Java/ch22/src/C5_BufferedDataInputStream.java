import java.io.*;

public class C5_BufferedDataInputStream {
    public static void main(String[] args) {
        try(DataInputStream in =
                    new DataInputStream(
                            new BufferedInputStream(
                                    new FileInputStream("data.dat")))){
            int num1 = in.readInt();
            double num2 = in.readDouble();

            System.out.println(num1);
            System.out.println(num2);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
