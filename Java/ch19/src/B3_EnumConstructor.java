enum Person5{
    MAN, WOMAN;

    private Person5(){
        System.out.println("Person constructor called");
    }
    @Override
    public String toString() {
        return "I am a cat person";
    }
}
public class B3_EnumConstructor {
    public static void main(String[] args) {
        System.out.println(Person5.MAN);
        //System.out.println(Person4.WOMAN);
    }
}
