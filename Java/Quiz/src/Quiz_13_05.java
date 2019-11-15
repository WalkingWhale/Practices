import java.util.Scanner;

public class Quiz_13_05 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        String[] arr;
        boolean flag = true;

        System.out.print("단어를 입력하세요 : ");
        input = sc.nextLine();

        arr = input.split("");

        /*
        for(int i = 0 ; i < input.length ; i++){
            arr[i]  = input.substring(i,i+1);
        }
        */

        /*
        char[] strArr3 = str.toCharArray();
        */

        int arr_len = arr.length - 1;


        for (int i = 0; i < (arr.length / 2); i++) {
            if (!(arr[i].equals(arr[(arr_len - i)]))) {
                System.out.println("회문이 아닙니다.");
                flag = false;
                break;
            }
        }

        if (flag) {
            System.out.println("회문입니다.");
        }

    }

}
