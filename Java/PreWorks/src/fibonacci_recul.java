import java.util.Scanner;

public class fibonacci_recul {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num;
        System.out.print("몇 번째 피보나치 수열의까지 구할지 입력해 주십시오.(시작은 0)");
        num = sc.nextInt();

        for (int i = 0; i < num ; i++){
            System.out.print(fibo(i) + " ");
        }
    }

    public static int fibo(int n){
        if(n == 0){
            return 0;
        }

        else if(n == 1){
            return 1;
        }
        return (fibo(n-1) + fibo(n-2));
    }
}
