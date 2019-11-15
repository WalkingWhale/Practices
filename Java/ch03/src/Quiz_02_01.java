package ch03;

import java.util.Scanner;

public class Quiz_02_01 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("두개의 정수를 입력해 주십시오:");
        int num1 = sc.nextInt();
        int num2 = sc.nextInt();

        System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
        System.out.println(num1 + " - " + num2 + " = " + (num1 - num2));
        System.out.println(num1 + " * " + num2 + " = " + (num1 * num2));
        System.out.println(num1 + " / " + num2 + " = " + ( (double) num1 / (double) num2) );

        sc.close();
    }
}
