import java.util.Scanner;

public class Quiz_06_10 {

    public static void main(String[] args) {
        int sum = 0;
        int num;
        int count = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("5개의 정수를 입력받겠습니다(1 이상)");

        while (count < 5){
            System.out.print((count + 1) + "번째 정수 입력: ");
            num = sc.nextInt();

            if (num < 1){
                System.out.println("잘못된 범위의 숫자입니다 다시 입력해주세요");
            }

            else {
                sum += num;
                count++;
            }
        }

        System.out.println("입력한 5개의 정수의 합은 " + sum + "입니다.");

        sc.close();
    }
}
