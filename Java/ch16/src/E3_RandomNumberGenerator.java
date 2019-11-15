import java.util.Random;

public class E3_RandomNumberGenerator {
    public static void main(String[] args) {
        Random ran = new Random();

        for(int i = 0 ; i < 7 ; i++){
            System.out.println(ran.nextInt(10));
        }
    }
}
