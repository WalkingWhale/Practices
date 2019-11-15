import java.util.Scanner;

public class TillInput_ByTimes {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num;
        while (true){
            System.out.println(" 몇단까지 출력 할 것인지 입력해 주세요(최대 17) : ");
            num  = sc.nextInt();

            if((num > 0 ) && (num < 18)){
                break;
            }
            else{
                System.out.println("범위에 맞지 않습니다. 다시 입력해 주세요");
                System.out.println();
            }
        }


        for (int i = 2 ; i < num + 1 ; i++ ){
            for(int j = 1 ;  j < num + 1 ; j++){
                System.out.println(i + " x " + j + " = " + (i * j));
            }

            System.out.println();
        }

        sc.close();
    }
}
