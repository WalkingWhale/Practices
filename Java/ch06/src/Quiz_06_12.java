import java.util.Scanner;

public class Quiz_06_12 {

    public static void main(String[] args) {
        int num1, num2;
        int sum = 0;
        int i =0;

        Scanner sc = new Scanner(System.in);

        System.out.println("두 개의 정수를 입력해 주십시오");
        System.out.print("첫번째 정수: ");
        num1 = sc.nextInt();
        System.out.print("두번째 정수: ");
        num2 = sc.nextInt();


        if(num2 > num1){
            i = num1;

            for(; i < (num2 + 1); i++){
                if(i == num2){
                    System.out.print(i);
                    sum = sum + i;
                }
                else{
                    System.out.print(i + " + ");
                    sum = sum + i;
                }

            }
        }

        else{
            i = num1;

            for(; i > (num2 - 1); i--){
                if(i == num2){
                    System.out.print(i);
                    sum = sum + i;
                }
                else{
                    System.out.print(i + " + ");
                    sum = sum + i;
                }

            }
        }

        System.out.println(" = " + sum);

        sc.close();
    }
}
