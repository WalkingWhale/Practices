import java.util.Scanner;

public class Quiz_06_05 {

    public static void main(String[] args) {
        int num = 0;
        int temp;
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("정수를 입력해 주십시오(종료는 0입력): ");
            temp = sc.nextInt();

            if(temp == 0){
                break;
            }

            num += temp;
        }

        System.out.println("여지껏 입력하신 정수들의 합은 "+ num + " 입니다.");
    }
}

