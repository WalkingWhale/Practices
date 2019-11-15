import java.util.Random;
import java.util.Scanner;

public class RPS_Game_simple {

    public static int gen_Com(){
        int com;
        int com_temp;

        Random ran = new Random(System.currentTimeMillis());


        while(true){
            com_temp = ran.nextInt(3);

            if(com_temp == 0 ){
                com = 0;             //com = "가위";
                break;
            }

            else if(com_temp == 1){
                com = 1;             //com = "바위";
                break;
            }

            else if(com_temp == 2){
                com = 2;             //com = "보";
                break;
            }

            else{
                continue;
            }
        }

        return com;
    }

    public static int play(int player, int computer){     // 0 == 가위, 1 == 바위, 2 ==보

        int ret;

        if(player == 0){            //player 가위
            if(computer == 0){        //com    가위
                System.out.println("비겼습니다.");
                System.out.println();
                ret = 0;            //비겼음을 return
            }

            else if(computer == 1){   //com    바위
                System.out.println("Computer가 이겼습니다.");
                ret = 1;            //computer가 이겼음을 return
            }

            else{                   //com    보
                System.out.println("Player가 이겼습니다. 축하합니다");
                ret = 2;            //player가 이겼음을 return
            }
        }

        else if(player == 1){       //player 바위
            if(computer == 0){        //com    가위
                System.out.println("Player가 이겼습니다. 축하합니다");
                ret = 2;            //player가 이겼음을 return
            }

            else if(computer == 1){   //com    바위
                System.out.println("비겼습니다.");
                System.out.println();
                ret = 0;            //비겼음을 return
            }

            else{                   //com    보
                System.out.println("Computer가 이겼습니다.");
                ret = 1;            //computer가 이겼음을 return
            }
        }

        else{                       //player  보
            if(computer == 0){        //com    가위
                System.out.println("Computer가 이겼습니다.");
                ret = 1;            //computer가 이겼음을 return
            }

            else if(computer == 1){   //com    바위
                System.out.println("Player가 이겼습니다. 축하합니다");
                ret = 2;            //player가 이겼음을 return
            }

            else{                   //com     보
                System.out.println("비겼습니다.");
                System.out.println();
                ret = 0;            //비겼음을 return
            }
        }

        return  ret;
    }

    public static int determine(int det, int count){
        if(det == 2){
            System.out.println(count + "번만에 이기셨습니다.");
            System.out.println();
            count = 0;

            return count;
        }

        else if(det == 1){
            System.out.println(count + "번째 시도에 지셨습니다.");
            System.out.println();
            count = 0;

            return count;

        }

        else{
            System.out.println("다시 시도합니다.");
            System.out.println();

            return count;
        }
    }



    public static void start(){
        int num;

        int computer;
        int count = 0;

        int det;
        Scanner sc = new Scanner(System.in);

        System.out.println("가위바위보 게임에 오신것을 환엽합니다");


        while(true){
            computer = gen_Com();
            count++;

            System.out.print("무엇을 내겠습니까?<1: 가위, 2:바위, 3:보> : ");
            num = sc.nextInt();

            if(num == 0){
                break;
            }

            det = play(num, computer);
            count = determine(det, count);
        }

        sc.close();
    }

    public static void main(String[] args) {
        start();
    }
}


