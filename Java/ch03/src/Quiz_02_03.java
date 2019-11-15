package ch03;

import java.util.Scanner;

public class Quiz_02_03 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("두개의 정수를 입력해 주십시오:");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();

        System.out.println(num1 + " 나누기 " + num2 + " 의 몫은 " + (num1 / num2) + "입니다.");
        System.out.println(num1 + " 나누기 " + num2 + " 의 나머지는 " + (num1 % num2) + "입니다.");

        sc.close();
    }
}
