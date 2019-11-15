import java.util.Scanner;

public class Quiz_06_03 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("정수를 하나 입력해 주세요 : ");
        int num=sc.nextInt();
        int temp = 1;

        while(num>0){

            if(num > 1)
            {
                System.out.print(num +"*");
            }

            else{
                System.out.print(num);
            }

            temp *= num;
            num--;

        }
        System.out.println(" = " + temp);
    }
}
