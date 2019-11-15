package ch03;

import java.util.Scanner;

public class Quiz_02_04 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("두개의 정수를 입력해 주십시오:");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();

        int big = (num1 >= num2) ? num1 : num2;

        System.out.println("입력받은 두 수 " + num1 + " " + num2 + " 중 더 큰 수는 " + big + "입니다." );

        sc.close();
    }
}
