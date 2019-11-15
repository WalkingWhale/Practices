interface PrintableD{
    void prints(String s);
}
public class E1_OneParamNoReturn {
    public static void main(String[] args) {
        PrintableD p;

        p = (String s) -> { System.out.println(s); };   // 줄임 없는 표현
        p.prints("Lambda exp on.");

        p = (String s) -> System.out.println(s);        // 중괄호 생략
        p.prints("Lambda exp two.");

        p = (s) -> System.out.println(s);               // 매개변수 형 생략
        p.prints("Lambda exp three.");

        p = s -> System.out.println(s);                 // 매개변수 소괄호 생략
        p.prints("Lambda exp four.");
    }
}
