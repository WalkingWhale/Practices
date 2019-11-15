import java.util.Arrays;
import java.util.List;

public class C2_MapToInt {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Box", "Robot", "Simple");

        list.stream()
                .map(s -> s.length())
                .forEach(n -> System.out.print(n + "\t"));

        System.out.println();
    }
}
