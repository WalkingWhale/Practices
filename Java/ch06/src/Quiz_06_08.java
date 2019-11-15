import java.util.Scanner;

public class Quiz_06_08 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num;
        while (true){
            System.out.print(" 몇단까지 출력 할 것인지 입력해 주세요(최대 8) : ");
            num  = sc.nextInt();

            if((num > 0 ) && (num < 9)){
                break;
            }
            else{
                System.out.println("범위에 맞지 않습니다. 다시 입력해 주세요");
                System.out.println();
            }
        }


        for (int i = 2 ; i < num + 1 ; ){
            for(int j = 1 ;  j < num + 1 ; j++){
                System.out.println(i + " x " + j + " = " + (i * j));
            }
            i = i+2;
            System.out.println();
        }

        sc.close();
    }

}
