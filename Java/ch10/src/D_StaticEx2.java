import java.util.Random;

public class D_StaticEx2 {
    static int num;

    static {
        Random ran = new Random();
        num = ran.nextInt(100);
    }

    public static void main(String[] args) {
        System.out.println(num);
    }
}
