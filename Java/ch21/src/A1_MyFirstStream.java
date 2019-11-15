import java.util.Arrays;
import java.util.stream.IntStream;

public class A1_MyFirstStream {
    public static void main(String[] args) {
        int[] ar = {1, 2, 3, 4, 5};

        // 스트림 생성
        IntStream stm1 = Arrays.stream(ar);

        // 중간 파이프 구성        // 홀수인 1, 3, 5를 stm2에 반환해서 stream 생성
        IntStream stm2 = stm1.filter(n -> n%2 == 1);

        // 최종 파이프 구성        // 중간파이프를 통해 만들어진 stream을 모두 더함
        int sum = stm2.sum();     //  sum = 1 + 3 + 5

        System.out.println(sum);
    }
}
