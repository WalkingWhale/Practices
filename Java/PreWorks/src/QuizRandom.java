import java.util.Random;

public class QuizRandom {
    public static void main(String[] args) {
        Random ran = new Random();

        int num1,num2,num3;


        while(true){
            num1 = (ran.nextInt(9)+1);
            num2 = (ran.nextInt(10));
            num3 = (ran.nextInt(10));
            if((num1 != num2) && (num1 != num3) && (num2 != num3)){
                break;
            }
        }

        System.out.println(num1 + " "+ num2 + " " + num3);

    }
}
