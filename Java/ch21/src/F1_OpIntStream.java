import java.util.stream.IntStream;

public class F1_OpIntStream {
    public static void main(String[] args) {
        // 합
        int sum = IntStream.of(1,3,5,7,9).sum();
        System.out.println("sum : " + sum);

        // 갯수
        long count = IntStream.of(1,3,5,7,9).count();
        System.out.println("count : " + count);

        // 평균
        IntStream.of(1,3,5,7,9)
                .average()
                .ifPresent(av -> System.out.println("avg = " + av));


        // 최소
        IntStream.of(1,3,5,7,9)
                .min()
                .ifPresent(mn -> System.out.println("min = " + mn));

        // 최대
        IntStream.of(1,3,5,7,9)
                .max()
                .ifPresent(mx -> System.out.println("max = " + mx));
    }
}
