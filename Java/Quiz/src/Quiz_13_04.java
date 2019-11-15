import java.util.Scanner;

public class Quiz_13_04 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[] outArr = new int[10];

        int input;

        int arr_len = outArr.length - 1;
        int count_even = 0;
        int count_odd = 0;


        for (int i = 0; i < outArr.length; i++) {

            System.out.print((i + 1) + "번째 정수를 입력해 주세요: ");
            input = sc.nextInt();

            if((input % 2) == 0){
                outArr[arr_len - count_even] = input;
                count_even++;
            }

            else{
                outArr[count_odd] = input;
                count_odd++;
            }

        }

        System.out.print("결과 : ");
        for (int e : outArr){
            System.out.print(e + " ");
        }

    }
}


