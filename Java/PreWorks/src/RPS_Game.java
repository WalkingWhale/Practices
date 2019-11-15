import java.util.Random;
import java.util.Scanner;

public class RPS_Game {

    public static int gen_Com(){
        int com;
        int com_temp;

        Random ran = new Random();


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

    public static int gen_Player()
    {
        Scanner sc = new Scanner(System.in);

        int player;
        String P_temp;

        System.out.println("플레이어가 낼 것을 골라주세요(가위, 바위, 보) : ");

        while (true){
            P_temp = null;

            System.out.print("1.가위, 2,바위, 3.보(숫자만 입력도 가능)");
            P_temp = sc.nextLine();

            if(P_temp.equals("1")  || P_temp.equals("가위") || P_temp.equals("Scissors") || P_temp.equals("scissors")){
                player = 0;             //player = "가위";
                break;
            }

            else if(P_temp.equals("2")  || P_temp.equals("바위") || P_temp.equals("Rock") || P_temp.equals("rock")){
                player = 1;             //player = "바위";
                break;
            }

            else if(P_temp.equals("3")  || P_temp.equals("보") || P_temp.equals("Paper") || P_temp.equals("paper")){
                player = 2;             //player = "보";
                break;
            }
            else{
                System.out.println("잘못된 입력입니다. 다시입력해 주세요");
            }
        }

        return player;
    }

    public static int det(int player, int computer){     // 0 == 가위, 1 == 바위, 2 ==보

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

    public static void play(){
        int count = 0;
        int temp;
        int keepP;
        int player;
        int computer;

        Scanner sc = new Scanner(System.in);

        while(true){

            computer = gen_Com();
            player = gen_Player();

            count++;

            temp = det(player, computer);

            if(temp == 2){
                System.out.println(count + "번만에 이기셨습니다.");
                System.out.println();
                System.out.print("계속진행은 아무키나 입력 종료는 0을 입력해 주세요");
                keepP = sc.nextInt();
                System.out.println();

                if(keepP == 0) {
                    System.out.println("Good Bye");
                    break;
                }
                else{
                    count = 0;
                }
            }

            else if(temp == 1){
                System.out.println(count + "번째 시도에 지셨습니다.");
                System.out.println();
                System.out.print("계속진행은 아무키나 입력 종료는 0을 입력해 주세요");
                keepP = sc.nextInt();
                System.out.println();

                if(keepP == 0) {
                    System.out.println();
                    System.out.println("Good Bye");
                    break;
                }

                else{
                    count = 0;
                }
            }

            else{
                System.out.println("다시 시도합니다.");
                System.out.println();
                computer = gen_Com();
            }
        }

    }

    public static void start(){
        int num;
        Scanner sc = new Scanner(System.in);
        System.out.println("가위바위보 게임에 오신것을 환엽합니다");
        System.out.print("게임을 진행하시려면 아무키나 입력해주세요(종료는 0입력)");
        num = sc.nextInt();

        if(num == 0){
            System.out.println("Good Bye");
        }
        else{
            play();
        }

        sc.close();
    }



    public static void main(String[] args) {
        start();
    }
}
