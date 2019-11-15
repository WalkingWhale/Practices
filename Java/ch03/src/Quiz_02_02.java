package ch03;

import java.util.Scanner;

public class Quiz_02_02 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("제곱하고자 하는 정수를 입력해 주십시오: ");
        int num1 = sc.nextInt();

        System.out.println(num1 + "의 제곱값 : " + (num1 * num1));

        sc.close();
    }
}
