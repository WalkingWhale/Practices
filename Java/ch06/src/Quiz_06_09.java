import java.util.Scanner;

public class Quiz_06_09 {

    public static void main(String[] args) {
        int num;
        Scanner sc = new Scanner(System.in);
        System.out.print("출력하고자 하는 단을 입력해 주세요: ");
        num = sc.nextInt();

        for(int i = 9 ; i >0 ; i--){
            System.out.println(num + " * " + i +" = " + num * i);

        }

        sc.close();
    }
}
