import java.util.Scanner;

public class Quiz_13_03 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[] num = new int[10];

        /*
        int[] even = new int[10];
        int count_even = 0;

        int[] odd = new int[10];
        int count_odd = 0;
        */

        /*
        StringBuilder even = new StringBuilder();       //배열 공간 아낄수있음
        StringBuilder odd = new StringBuilder();
        */

        for(int i = 0 ; i < num.length ; i++){
            System.out.print((i + 1) + "번째 정수를 입력해 주세요: ");
            num[i] = sc.nextInt();
        }

        System.out.println("짝수 : ");
        for(int e : num){
            if((e % 2) == 0){
                System.out.print(e + " ");
            }
        }

        System.out.println("홀수 : ");
        for(int e : num){
            if((e % 2) != 0){
                System.out.print(e + " ");
            }
        }



        /*
        for(int e : num){
            if( e % 2 == 0){
                even.append(e + " ");
                //even[count_even] = e;
                //count_even++;
            }

            else{
                odd.append(e + " ");
                //odd[count_odd] = e;
                //count_odd++;
            }
        }
        */
        /*
        System.out.println("홀수 : " + odd);
        System.out.println("짝수 : " + even);
        */
        /*System.out.print("짝수 : ");
        for(int  i = 0 ; i < count_even ; i++){
            System.out.print(even[i] + " ");
        }

        System.out.println();

        System.out.print("홀수 : ");
        for(int  i = 0 ; i < count_odd ; i++){
            System.out.print(odd[i] + " ");
        }*/

    }
}
