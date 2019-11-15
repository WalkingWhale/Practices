public class Quiz_07_03 {
    public static void main(String[] args) {
        System.out.println(rSmall(1,3,5));
    }

    public static int rSmall(int num1, int num2, int num3){
        int small = 0;

        if(num1 < num2 && num1 < num3){
            small = num1;
        }

        else if(num2 < num1 && num2 < num3){
            small = num2;
        }

        else if(num3 < num1 && num3 < num1){
            small = num3;
        }

        return small;
    }
}
