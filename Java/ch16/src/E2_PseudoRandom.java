import java.util.Random;

public class E2_PseudoRandom {
    public static void main(String[] args) {
        Random ran = new Random(12);

        for(int i = 0 ; i < 7 ; i++){
            System.out.println(ran.nextInt(10));
        }
    }
}

// 실행할 때마다 같은 결과를 보인다.