import java.util.Scanner;

public class Input_ByTimes {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num;

        System.out.println(" 몇단을 출력 할 것인지 입력해 주세요 : ");
        num  = sc.nextInt();

        for (int i = 1 ; i < num + 1 ; i++ ){
            System.out.println(num + " x " + i + " = " + (num * i));
        }

        sc.close();
    }
}
