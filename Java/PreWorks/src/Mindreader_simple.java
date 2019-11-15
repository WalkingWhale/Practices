import java.util.Scanner;

public class Mindreader_simple {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int max = 100, guess = 50, min =0, count = 0;

        printStart();

        while (true){
            count++;
            System.out.println("당신이 선택한 숫자는 " + guess + "입니까?");
            String str = sc.next();

            if(str.equalsIgnoreCase("y")){
                System.out.println("정답입니다.[" + count + "회차]");
                break;
            }

            else if(str.equalsIgnoreCase("h")){
                max = guess;
                guess = ((guess + min) / 2);
            }

            else if(str.equalsIgnoreCase("l")){
                min = guess;
                guess = ((guess + max) / 2);
            }
        }
    }

    static void printStart(){
        System.out.println("지금부터 0 이상 100 미만의 값 중 하나를 생각하세요");
        System.out.println("제(컴퓨터)가 제시한 숫자가 생각한 숫자보다 크면 h를 입력하세요");
        System.out.println("제(컴퓨터)가 제시한 숫자가 생각한 숫자보다 작으면 l을 입력하세요");
        System.out.println("제(컴퓨터)가 숫자를 맞췄다면 y를 입력해 주세요");
    }
}
