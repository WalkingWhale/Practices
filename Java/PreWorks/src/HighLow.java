import java.util.Random;
import java.util.Scanner;

class Computer{
    private int num;
    private int count;

    Computer(){
        genNum();
        resetCount();
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    protected void resetCount(){
        this.count = 6;
    }

    protected int counting(){
        return this.count = (this.count - 1);
    }

    protected void genNum(){
        Random ran = new Random();
        setNum(ran.nextInt(100));
    }

    protected boolean guess(int num){
        if(num > this.num){
            System.out.println(num + " 은(는) 제가 생각한 숫자보다 큽니다.");
            System.out.println();
            return false;
        }

        else if(num == this.num) {
            return true;
        }

        else{
            System.out.println(num + " 은(는) 제가 생각한 숫자보다 작습니다.");
            System.out.println();
            return false;
        }
    }
}
public class HighLow {

    public static void printStart(){
        System.out.println("나는 지금 0 부터 100 사이의 값 중 하나를 생각하겠습니다.");
        System.out.println("당신은 그 숫자를 6회 안에 맞추시면 됩니다");
        System.out.println();
    }

    public static boolean Game(Scanner sc, Computer com, boolean flag){

        while (true){

            if(com.getCount() == 0){
                lose();
                flag = keepPlay(sc);
                break;
            }
            System.out.println("몇 이라고 생각합니까? <0 to 100>");
            System.out.println("[ " + com.getCount() + " ]" + "번의 기회가 남았습니다.");
            System.out.print("입력 : ");
            int num = sc.nextInt();

            if(com.guess(num)){
                Win(num);
                flag = keepPlay(sc);
                break;
            }
            com.counting();
        }


        return flag;
    }

    public static void Win(int num){
        System.out.println();
        System.out.println(num + "은(는) 정답입니다. 축하합니다");
        System.out.println();
    }

    public static void lose(){
        System.out.println();
        System.out.println("정해진 횟수 안에 맞추지 못하셨습니다.");
        System.out.println();
    }

    public static boolean keepPlay(Scanner sc){
        System.out.println("High / Low 게임을 플레이해 주셔서 감사합니다.");
        System.out.print("다시하시겠습니까? <y,n>");
        String str = sc.next();
        if(!str.equalsIgnoreCase("y")){
            System.out.println("Good bye~");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Computer com = new Computer();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while(flag){
            com.resetCount();
            com.genNum();
            printStart();
            flag = Game(sc,com, flag);
        }

        sc.close();
    }
}
