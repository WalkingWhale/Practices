import java.util.Random;
import java.util.Scanner;

public class BaseballGame_simple {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int com = gen_com();
        int inNum;
        boolean flag = true;

        int count = 0;

        printStart();

        while(flag){

            count++;

            System.out.println("세자리 숫자를 입력하세요.(" + count + ")");
            inNum = sc.nextInt();
            flag = determin(inNum,com);

        }

    }

    static int gen_com(){

        Random ran = new Random();
        int num1,num2,num3;
        int sum;

        while(true){
            num1 = (ran.nextInt(9) + 1);
            num2 = (ran.nextInt(9) + 1);
            num3 = (ran.nextInt(9) + 1);
            if((num1 != num2) && (num1 != num3) && (num2 != num3)){
                break;
            }
        }

        sum = (num1 * 100) + (num2 * 10) + num3;

        return sum;

    }

    static void printStart(){
        System.out.println("숫자로 하는 야구게임 시작");
    }

    static boolean determin(int inNum, int com){

        int pTemp1,pTemp2,pTemp3;
        int cTemp1,cTemp2,cTemp3;
        int strike = 0;
        int ball = 0;

        pTemp1 = inNum / 100;                       // 100의 자리
        pTemp2 = ((inNum - (pTemp1 * 100)) / 10);   // 10의 자리
        pTemp3 = inNum % 10 ;                       // 1의 자리

        cTemp1 = com / 100;                         // 100의 자리
        cTemp2 = ((com - (cTemp1 * 100)) / 10);     // 10의 자리
        cTemp3 = com % 10 ;                         // 1의 자리

        if(pTemp1 == cTemp1){
            strike++;
        }

        else if(pTemp1 == cTemp2 || pTemp1 == cTemp3){
            ball++;
        }

        if(pTemp2 == cTemp2){
            strike++;
        }

        else if(pTemp2 == cTemp1 ||pTemp2 == cTemp3){
            ball++;
        }

        if (pTemp3 == cTemp3){
            strike++;
        }

        else if(pTemp3 == cTemp1 ||pTemp3 == cTemp2){
            ball++;
        }

        System.out.println(pTemp1 + ":" + pTemp2 + ":" + pTemp3);
        System.out.println(strike + " Strike\t" + ball + " Ball");

        if(strike == 3){
            System.out.println("YOU WIN!!!");
            return false;
        }

        return true;

    }


}
