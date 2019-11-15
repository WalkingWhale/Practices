public class Quiz_07_02 {
    public static void main(String[] args) {
        System.out.println(rbig(1,3,5));
    }

    public static int rbig(int num1, int num2, int num3){
        int big =0;

        if(num1 > num2 && num1 > num3){
            big = num1;
        }

        else if(num2 > num1 && num2 > num3){
            big = num2;
        }

        else if(num3 > num1 && num3 > num1){
            big = num3;
        }

        return big;
    }
}
