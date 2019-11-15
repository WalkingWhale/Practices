import java.util.Scanner;

public class SnailArray {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int degree;                             // 행렬 차수 입력 변수

        System.out.println("Input Degree of Array");
        degree = sc.nextInt();                  // degree에 차수 입력
        sc.close();                             // Scanner 종료

        int[][] snail = new int[degree][degree];
        int sw = 1;                             // 증감 변수 - 반복횟수에 따라 1씩 증가 or 1씩 감소
        int key = degree;                       // 반복문에서 사용하기 위해 복사
        int row = 0;                            // 행 index는 0에서 시작 row 증가 for문에선 바로 index가 1로 증가
        int col = -1;                           // 열 index는-1 시작 col 증가 for문 안에서 바로 +1 해서 index 0 에서 부터 시작

        int num = 1;                            // 배열안에 들어갈 정수

        while(true){
            for(int i = 0 ; i < key ; i++){     // 열 증가 반복문 Arr[][x]
                col = col + sw;
                snail[row][col] = num++;
            }

            key--;                              // 반복 횟수 -1

            if(key == 0 ){                      // 더이상 빈 공간이 없기 때문에 while문 break
                break;
            }

            for(int i = 0 ; i < key ; i++){     // 행 증가 반복문 Arr[x][]
                row = row + sw;
                snail[row][col] = num++;
            }

            sw = sw * (-1);                     // sw의 증감을 변경
        }

        for(int i = 0 ; i < degree ; i++){      // 배열 출력
            for(int j = 0 ; j < degree ; j++){
                System.out.printf("%3d", snail[i][j]);
            }
            System.out.println();
        }



    }
}
