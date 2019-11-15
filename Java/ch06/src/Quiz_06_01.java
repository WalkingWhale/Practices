import java.util.Scanner;

public class Quiz_06_01 {

    public static void main(String[] args) {

        int num1, num2;
        Scanner sc = new Scanner(System.in);

        System.out.print("두 수를 입력해 주세요: ");
        num1 = sc.nextInt();
        num2 = sc.nextInt();

        if(num1 > num2){
            System.out.println("입력받은 두 수 "+ num1 +" "+ num2 + " 의 차는 " + (num1 - num2) +" 입니다.");
        }
        else{
            System.out.println("입력받은 두 수 "+ num1 +" "+ num2 + " 의 차는 " + (num2 - num1) +" 입니다.");
        }

    }
}
