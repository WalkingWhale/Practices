import java.util.Scanner;

public class PrintingPyramid {

    public static void main(String[] args) {

        int row;
        int k = 0;
        Scanner sc = new Scanner(System.in);

        row = sc.nextInt();

        for(int i = 1 ; i <= row ; i++ , k =0){                 //입력받은 차수 만큼 반복
             for(int space = 1 ; space <= row - i ; space++){   //차수 -1번째 까지 ' ' 출력으로 빈칸 표시
                 System.out.print(" ");
             }

             for(; k < 2 * i -1; k++){                          //i 번째 줄의 i * 2 - 1 개 만큼 * 출력
                 System.out.print("*");
             }

            System.out.println();
        }
    }
}
