import java.util.Scanner;

public class Quiz_IntegerExtraction {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("숫자와 정수를 무작위로 입력해 주세요:");
        String str = sc.nextLine();
        String temp = new String();
        String[] str2;
        int sum = 0;

        for(int i = 0; i < str.length() ; i++){
            if((47 < str.charAt(i)) && (str.charAt(i) < 58)){
                temp += str.charAt(i);
            }
        }

        str2 = temp.split("");
        for(int i= 0; i < str2.length ; i++){
            sum = sum + Integer.parseInt(str2[i]);
        }

        System.out.println("숫자의 합: " + sum);
    }
}
