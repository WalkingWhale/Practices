import java.util.Arrays;

public class A2_MyFirstStream {

    public static void main(String[] args) {
        int[] ar = {1,2,3,4,5};

        int sum = Arrays.stream(ar)         //닷(.)체인 문법
                        .filter(n -> n%2 ==1)
                        .sum();

        System.out.println(sum);
    }
}