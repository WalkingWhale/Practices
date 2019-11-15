class Calc_02{

    int add(int a, int b){
        return a+b;
    }
}

public class Calculation_02 {

    static void disp(){

        int nRtn;
        Calc calc = new Calc();

        nRtn = calc.add(3,9);

        System.out.println("nRtn1 = " + nRtn);
    }
    public static void main(String[] args) {
        disp();
    }
}
