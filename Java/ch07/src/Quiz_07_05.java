import java.util.Scanner;

public class Quiz_07_05 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num1, num2, num3;

        print();

        System.out.print("첫 번째 정수를 입력해 주십시오 : ");
        num1 = sc.nextInt();

        System.out.print("두 번째 정수를 입력해 주십시오 : ");
        num2 = sc.nextInt();

        num3 = getThird();


        Calc(num1, num2, num3);

        sc.close();
    }

    public static void print(){
        System.out.println("3개의 정수를 입력해 주십시오");
        System.out.println("첫 번째 정수와 두 번째 정수는 연산에 이용됩니다.");
        System.out.println("세 번째 정수는 연산자로 사용됩니다");
    }

    public static int getThird(){
        int num3;
        Scanner sc = new Scanner(System.in);

        while(true){

            System.out.println("연산자로 사용될 3 번째 정수를 입력해 주십시오");
            System.out.println("1 == +, 2 == -, 3 == *, 4 == /");
            num3 = sc.nextInt();
            if(num3 > 0 && num3 < 5){
                break;
            }
            else{
                System.out.println("잘못된 입력입니다. 다시 입력해 주세요");
            }
        }

        return num3;
    }

    public static void Calc(int num1, int num2, int num3){

        switch (num3){
            case 1:{
                System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
                break;
            }
            case 2:{
                System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
                break;
            }
            case 3:{
                System.out.println(num1 + " * " + num2 + " = " + (num1 * num2));
                break;
            }
            case 4:{
                System.out.println(num1 + " / " + num2 + " = " + ((double)num1 / (double)num2));
                break;
            }

            default:{
                System.out.println("Error");
            }
        }
    }
}
