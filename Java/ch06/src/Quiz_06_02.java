import java.util.Scanner;

public class Quiz_06_02 {

    public static void main(String[] args) {
        int kor, eng, math;
        double avg;
        Scanner sc = new Scanner(System.in);

        System.out.println("국어 영어 수학의 점수를 차례대로 입력해 주세요: ");
        System.out.print("국어 : ");
        kor = sc.nextInt();

        System.out.print("영어 : ");
        eng = sc.nextInt();

        System.out.print("수학 : ");
        math = sc.nextInt();

        avg = (double)(kor + eng + math) / 3;

        System.out.printf("입력받은 학생의 평균 점수는 : %.3f 점 입니다.", avg);
        System.out.print("학점은 ");

        if(avg >= 90){
            System.out.print("A ");
        }

        else if(avg >= 80){
            System.out.print("B ");
        }

        else if(avg >= 70){
            System.out.print("C ");
        }
        else if(avg >= 50){
            System.out.print("D ");
        }
        else{
            System.out.print("F");
        }

        System.out.println("입니다.");
    }
}
