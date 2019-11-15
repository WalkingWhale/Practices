import java.util.Arrays;
import java.util.List;

public class C3_MapToInt2 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Box", "Robot", "Simple");

        list.stream()
                .mapToInt(s -> s.length())
                .forEach(n -> System.out.print(n + "\t"));

        System.out.println();
    }
}