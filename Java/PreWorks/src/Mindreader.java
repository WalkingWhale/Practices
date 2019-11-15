import java.util.Scanner;

class Mind{
    private int guess;
    private int temp;
    private int count;

    public Mind() {
        this.guess = 50;
        this.count = 0;
    }

    public int getGuess() {
        return guess;
    }

    public int getCount() {
        return count;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void increase(){
        this.temp = (int)Math.round(((double)temp / 2)); // 1회차 25 증가 2회차 12 증가 3회차  6 증가...
        this.guess = this.guess + this.temp;
    }

    public void decrease(){
        temp = (int)Math.round(((double)temp / 2)); // 1회차 25 감소 2회차 12 감소 3회차 6 감소...
        guess = guess - temp;
    }

    public void counting(){
        this.count++;
    }


}
public class Mindreader {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases;
        Mind mind = new Mind();
        boolean flag = true;

        printStart();

        while(flag){

            mind.counting();
            System.out.println("당신이 선택한 숫자는 " + mind.getGuess() + "입니까?");
            cases = guessing(sc);

            switch (cases){
                case 0:{
                    System.out.println("정답입니다.[" + mind.getCount() + "회차]");
                    flag = false;
                    break;
                }

                case 1:{
                    mind.increase();
                    break;
                }

                case 2:{
                    mind.decrease();
                    break;
                }

                default:{
                    System.out.println("Error");
                    break;
                }
            }
        }
    }

    static void printStart(){
        System.out.println("지금부터 0 이상 100 미만의 값 중 하나를 생각하세요");
        System.out.println("제(컴퓨터)가 제시한 숫자가 생각한 숫자보다 크면 h를 입력하세요");
        System.out.println("제(컴퓨터)가 제시한 숫자가 생각한 숫자보다 작으면 l을 입력하세요");
        System.out.println("제(컴퓨터)가 숫자를 맞췄다면 y를 입력해 주세요");
    }

    static int guessing(Scanner sc){
        while(true){
            System.out.print("입력 : ");
            String str = sc.next();

            if(str.equalsIgnoreCase("y")){
                return 0;       //게임 종료 시그널 리턴
            }

            else if(str.equalsIgnoreCase("h")){
                return 2;       //guessing number 증가 시그널 리턴
            }

            else if(str.equalsIgnoreCase("l")){
                return 1;       //guessing number 감소 시그널 리턴
            }

            else{
                continue;
            }
        }
    }

}
