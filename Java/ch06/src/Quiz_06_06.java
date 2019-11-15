import java.util.Scanner;

public class Quiz_06_06 {

    public static void main(String[] args) {

        int num;
        int temp = 0;
        double avg;
        Scanner sc = new Scanner(System.in);

        System.out.print("몇개의 정수를 입력할지 입력해 주십시오:");
        num = sc.nextInt();

        for(int i = 0; i < num ; i++){
            System.out.print((i + 1) + "번째 정수를 입력해 주십시오: ");
            temp += sc.nextInt();
        }

        avg = temp / num;

        System.out.printf("입력받은 정수들의 평균은 : %.3f 입니다.", avg);
    }
}
