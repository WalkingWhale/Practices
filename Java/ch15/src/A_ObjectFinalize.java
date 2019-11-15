class A_Person{
    String name;

    public A_Person(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("destroyed: " + name);
    }
}
public class A_ObjectFinalize {
    public static void main(String[] args) {
        A_Person p1 = new A_Person("홍길동");
        A_Person p2 = new A_Person("전우치");

        p1 = null;
        p2 = null;

        System.gc();
        System.runFinalization();

        System.out.println("end of program");
    }
}
