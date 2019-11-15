class Static_Cat{
    static int a = 5;
    int num = 3;

    void print(int num3){
        System.out.println("a: " + a);
        num = num3;
        System.out.println("num: " + num);
    }
}

public class C_StaticCat {

    public static void main(String[] args) {
        int num1 = 5;
        int num2 = 2;
        System.out.println(num1 + "," + num2);

        Static_Cat cat1 = new Static_Cat();
        cat1.num = 1;
        cat1.a =10;
        cat1.print(20);
        System.out.println(cat1.num);
        System.out.println(cat1.a);

        Static_Cat cat2 = new Static_Cat();
        cat2.num = 1;
        cat2.a =11;
        cat2.print(20);
        System.out.println(cat2.num);
        System.out.println(cat2.a);
        System.out.println(cat1.a);
    }
}
