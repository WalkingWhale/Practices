import java.util.stream.Stream;

public class D1_InstSortedStream {
    public static void main(String[] args) {
        Stream.of("Box", "Apple", "Robot")
                .sorted()   // <- String 인스턴스는 Cmparable<String> 인터페이스를 구현
                .forEach(s -> System.out.print(s + "  ")); // Cmparable<String>를 기반으로 정렬
        System.out.println();

        Stream.of("Box", "Apple", "Robot")
                .sorted((s1,s2)-> s1.length() -s2.length()) // CmparableTO 메소드에
                .forEach(s -> System.out.print(s + "  ")); // 대한 람다식을 기반으로 정렬
        System.out.println();
    }
}
