enum Person4{
    MAN, WOMAN;

    @Override
    public String toString() {
        return "I am a cat person";
    }
}
public class B2_EnumConst {
    public static void main(String[] args) {
        System.out.println(Person4.MAN);
        System.out.println(Person4.WOMAN);
    }
}
