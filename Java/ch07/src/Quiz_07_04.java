import java.util.Scanner;

public class Quiz_07_04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("화씨로 변환할 섭씨를 입력해 주십시오 : ");        int cel = sc.nextInt();

        System.out.println("변환된 화씨 온도는 : " + toFahre(cel) + "입니다.");

        System.out.print("섭씨로 변환할 화씨를 입력해 주십시오 : ");
        int fahre = sc.nextInt();
        System.out.println("변환된 섭씨 온도는 : " + toCel(fahre) + "입니다.");

        sc.close();
    }

    public static double toCel(double fahre){
        double cel = ((fahre - 32) / 1.8);

        return cel;
    }

    public static double toFahre(double cel){
        double fahre = (((1.8) * cel) + 32);

        return fahre;
    }
}
