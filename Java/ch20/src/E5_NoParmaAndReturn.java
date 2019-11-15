import java.util.Random;

interface Generator {
    int rand();
}

public class E5_NoParmaAndReturn {
    public static void main(String[] args) {
        Generator gen = () -> {
                Random rand = new Random();
                return rand.nextInt(50);
        };

        System.out.println(gen.rand());
    }
}
