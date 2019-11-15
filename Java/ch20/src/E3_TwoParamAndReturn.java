interface Calculate4{
    int cal(int a, int b);
}

public class E3_TwoParamAndReturn {
    public static void main(String[] args) {
        Calculate4 c;
        c = (a, b) -> {return a + b;};
        System.out.println(c.cal(3,4 ));

        c = (a, b) -> a + b;
        System.out.println(c.cal(4,3));
    }
}
