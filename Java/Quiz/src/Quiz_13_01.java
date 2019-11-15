import java.util.Scanner;

public class Quiz_13_01 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[] ar = new int[5];
        int max;
        int min;
        int sum = 0;

        for(int i = 0; i < ar.length; i++){
            System.out.print((i + 1) + "번째 정수를 입력해 주세요: ");
            ar[i] = sc.nextInt();
        }

        max = getMax(ar);
        min = getMin(ar);
        sum = getSum(ar);

        System.out.println("입력받은 정수 중 최대값: " + max);
        System.out.println("입력받은 정수 중 최소값: " + min);
        System.out.println("입력받은 정수 들의 합: " + sum);

    }

    private static int getMax(int[] ar) {
        int max = ar[0];

        for(int i = 1 ; i < ar.length ; i++){
            if(max < ar[i]){
                max = ar[i];
            }
        }

        return max;
    }

    private static int getMin(int[] ar) {
        int min = ar[0];

        for(int i = 1 ; i < ar.length ; i++){
            if(min > ar[i]){
                min = ar[i];
            }
        }

        return min;
    }



    private static int getSum(int[] ar) {
        int sum = 0;

        for(int e : ar){
            sum = sum + e;
        }

        return sum;
    }
}
