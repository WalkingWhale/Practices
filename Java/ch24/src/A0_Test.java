class Mytest1{
    public void print(){
        int num = 30;

        for(int i=0;i<num;i++){

            System.out.println("Mytest1 : " + i);
        }
    }
}

class Mytest2{
    public void print(){
        int num = 30;

        for(int i=0;i<num;i++){

            System.out.println("Mytest2 : " + i);
        }
    }
}
public class A0_Test {
    public static void main(String[] args) {
        Mytest1 test1 = new Mytest1();
        Mytest2 test2 = new Mytest2();

        test1.print();
        System.out.println("=======================");
        test2.print();
    }
}
