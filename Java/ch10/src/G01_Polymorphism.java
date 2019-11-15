abstract class Calc01{
    int a = 5;
    int b = 6;

    abstract int Plus01();
}

class MyCalc01 extends Calc01{
    int Plus01(){
        return (a + b);
    }

    int Minus01(){
        return ( a-b );
    }
}

public class G01_Polymorphism {

    public static void main(String[] args) {
        MyCalc01 myCalc01 = new MyCalc01();
        myCalc01.Plus01();
        myCalc01.Minus01();

        Calc01 myCalc02 = new Calc01() {
            @Override
            int Plus01() {
                return 0;
            }
        };
        myCalc02.Plus01();
        //myCalc02.Minus01();
    }
}
