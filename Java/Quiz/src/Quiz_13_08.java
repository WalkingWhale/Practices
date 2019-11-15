import java.util.Scanner;

public class Quiz_13_08 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] arr = new int[4][4];
        int[] sum_row = new int[4];
        int[] sum_col = new int[4];

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: {
                    System.out.print("이순신 학생의 성적을 입력합니다. ");
                    arr[i] = getGrade(sc,arr[i]);
                    break;
                }

                case 1: {
                    System.out.println();
                    System.out.print("강감찬 학생의 성적을 입력합니다. ");
                    arr[i] = getGrade(sc,arr[i]);
                    break;
                }

                case 2: {
                    System.out.println();
                    System.out.print("을지문덕 학생의 성적을 입력합니다. ");
                    arr[i] = getGrade(sc,arr[i]);
                    break;
                }

                case 3: {
                    System.out.println();
                    System.out.print("권율 학생의 성적을 입력합니다. ");
                    arr[i] = getGrade(sc,arr[i]);
                    break;
                }

                default: {
                    System.out.println("잘못된 입력");
                    i--;
                    continue;
                }

            }
        }

        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){
                sum_row[i] = sum_row[i] + arr[i][j];
                sum_col[i] = sum_col[i] + arr[j][i];
            }
        }

        System.out.println("구분\t이순신\t강감찬\t을지문덕\t권율\t총점");
        for(int i = 0 ; i < 5 ; i++){

            if( i == 0){
                System.out.print("국어\t");
            }
            else if( i == 1){
                System.out.print("영어\t");
            }
            else if( i == 2){
                System.out.print("수학\t");
            }
            else if( i == 3){
                System.out.print("국사\t");
            }
            else if(i ==4){
                System.out.print("총점\t");
                break;
            }

            for(int j = 0 ; j < 4 ; j++){
                if(j == 3){
                    System.out.print("\t" + arr[i][j] + "\t\t" + sum_row[i]);
                }
                else{
                    System.out.print(arr[i][j] + "\t\t");
                }
            }

            System.out.println();

        }
        for(int i = 0 ; i < 4 ; i++){
            if(i==3){
                System.out.print("\t");
            }

            System.out.print(sum_col[i] + "\t\t");
        }


    }

    public static int[] getGrade(Scanner sc , int arr[]) {
        int grade[] = new int[4];
        System.out.println();

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: {
                    System.out.print("국어 성적을 입력해 주십시오: ");
                    grade[i] = sc.nextInt();
                    break;
                }

                case 1: {
                    System.out.print("영어 성적을 입력해 주십시오: ");
                    grade[i] = sc.nextInt();
                    break;
                }

                case 2: {
                    System.out.print("수학 성적을 입력해 주십시오: ");
                    grade[i] = sc.nextInt();
                    break;
                }

                case 3: {
                    System.out.print("국사 성적을 입력해 주십시오: ");
                    grade[i] = sc.nextInt();
                    break;
                }

                default: {
                    System.out.println("잘못된 입력");
                    i--;
                    continue;
                }

            }
        }

        return grade;
    }
}


