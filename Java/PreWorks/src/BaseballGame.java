import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BaseballGame {

    public static void main(String[] args) {

        Random rand = new Random();
        Scanner sc = new Scanner(System.in);

        int[] Target = new int[4];                      // 자동으로 생성되는 목표값
        int[] InNum = new int[4];                       // 자릿수 확인을 위한 사용자 입력값의 배열화를 위한 배열
        int Temp;                                       // 사용자가 입력한 값을 넣기위한 변수
        int TryCount = 1;                               //' 도전횟수
        int balls;
        int strikes;

        System.out.println("Welcome to baseball Game!!!");

        System.out.println("Creating Target Value");
        System.out.println();

        for(int i = 0; i < Target.length ; i++){        // 중복값 없이 Random한 4자리 정수 생성
            Target[i] = (int)(Math.random() * 9) + 1;

            for(int j = 0; j < i; j ++){
                if(Target[i] == Target[j]){
                    i--;
                    break;
                }
            }
        }

        try {                                           // 1초간 대기

            Thread.sleep(1000);

        } catch (InterruptedException e) {
        }

        System.out.println("Target Value Created");
        System.out.println("Target Value is 4 Digits Number without Duplication ");
        System.out.println("Try Your Best to Solve!!");
        System.out.println();
        //System.out.println(Arrays.toString(Target));

        while (true) {

            strikes = 0;
            balls = 0;
            System.out.println("-------------------------------------------------------");
            System.out.println("Enter 99999 to Exit now");  //혹시 모를 상황을 위한 강제 탈출 장치
            System.out.println("Enter Your Numbers(Total 4 Digits) : ");
            Temp = sc.nextInt();

            if (Temp > 999 && Temp < 10000) {               // 입력값이 4자리수일경우
                int N = 1000;
                for (int i = 0; i < 4; i++) {
                    InNum[i] = Temp / N;
                    Temp = Temp % N;
                    N = N / 10;
                }

            }

            else if (Temp > 99 && Temp < 1000) {            // 입력값이 3자리수일경우 1000의 자리에 0을 강제 입력
                InNum[0] = 0;
                int N = 100;
                for (int i = 1; i < 4; i++) {
                    InNum[i] = Temp / N;
                    Temp = Temp % N;
                    N = N / 10;
                }

            }

            else if (Temp > 9 && Temp < 100) {              // 입력값이 2자리수일경우 1000의자리와 100의자리에 0을 강제 입력
                InNum[0] = 0;
                InNum[1] = 0;
                int N = 10;
                for (int i = 2; i < 4; i++) {
                    InNum[i] = Temp / N;
                    Temp = Temp % N;
                    N = N / 10;
                }

            }

            else if (Temp < 10) {                           // 입력값이 1자리수일경우 1000, 100, 10의 자리에 0을 강제 입력
                InNum[0] = 0;
                InNum[1] = 0;
                InNum[2] = 0;
                InNum[3] = Temp;

            }

            else if(Temp == 99999){                         // 무한루프를 강제로 깨기 위한 예외 처리
                break;
            }

            else {
                System.out.println("Invalid Input. Please try agian");      // 범위에 안맞는 수 일경우 에러 처리후 다시 입력
                System.out.println();
            }

            System.out.print("Your Number is : ");
            System.out.println(Arrays.toString(InNum));                     // 사용자 입력값 출력

            for(int i = 0; i < 4 ; i++){                                    // Strike, Ball 여부 확인
                for(int j = 0 ; j < 4 ; j++){
                    if((Target[i] == InNum[j]) && (i == j)){
                        strikes++;
                    }

                    else if(Target[i] == InNum[j] && (i != j)){
                        balls++;
                    }
                }
            }



            if(strikes == 4){                                               //4 Strike일경우 승리 메세지, 도전횟수 출력후 종료
                System.out.println("Congrats! You win the game ");
                System.out.println("Total Attempt : " + TryCount);
                System.out.println();
                break;
            }
            else if (strikes == 0 && balls == 0){                           //스트라이크와 볼 모두 없을경우 out처리 - 메세지 출력으로 대체
                System.out.println("You got Out");
                System.out.println();
            }
            else{
                System.out.printf("You got %d strikes and %d balls! \n", strikes, balls);       //플레이어가 맞춘 Strike와 ball의 갯수 출력
                System.out.println();
                TryCount++;
            }

        }

    }

}


