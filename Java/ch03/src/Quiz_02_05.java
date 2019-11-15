package ch03;

import java.util.Scanner;

public class Quiz_02_05 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int mul = 0;

        System.out.print("두개의 정수를 입력해 주십시오:");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();


        mul = (num1 * num2 > 0) ? (num1 * num2) : (num1 * num2 * (-1));

        System.out.println("입력받은 두 수 " + num1 + " " + num2 + " 의 곱한 값의 절대값은 " + mul + "입니다.");

        sc.close();
    }

}
