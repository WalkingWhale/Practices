import java.util.Random;
import java.util.Scanner;

class Player {
    private int row;
    private int col;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int get_Row() {
        return row;
    }

    public int get_Col() {
        return col;
    }

    public void set_Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Quiz_3x3 {
    static final int ARR_LEN = 3;

    public static void main(String[] args) {

        String[][] Quiz = new String[ARR_LEN][ARR_LEN];
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        Player p = new Player(2, 2);
        Quiz = setQuiz(Quiz, p);
        Quiz = AutoSuffle(p, Quiz);

        for (int i = 0; i < ARR_LEN; i++) {
            for (int j = 0; j < ARR_LEN; j++) {
                System.out.print(Quiz[i][j] + "\t");
            }
            System.out.println();
        }



        while (flag) {

            flag = getInput(p, sc, flag);

            if (!flag) {
                break;
            }

            Quiz = changePosition(p, Quiz);

            for (int i = 0; i < ARR_LEN; i++) {
                for (int j = 0; j < ARR_LEN; j++) {
                    System.out.print(Quiz[i][j] + "\t");
                }
                System.out.println();
            }

            flag = determine(Quiz);

        }

    }

    public static Boolean determine(String[][] arr) {
        String temp = new String();

        for (int i = 0; i < ARR_LEN; i++) {
            for (int j = 0; j < ARR_LEN; j++) {
                temp += arr[i][j];
            }
        }

        //System.out.println(temp);

        if (temp.equals("12345678x")) {
            System.out.println("승리하셨습니다!!! 축하합니다");
            return false;
        }
        else {
            return true;
        }
    }

    public static String[][] changePosition(Player p, String[][] arr) {
        int tempRow1 = p.get_Row();
        int tempCol1 = p.get_Col();

        int tempRow2 = 0;
        int tempCol2 = 0;
        String temp;

        for (int i = 0; i < ARR_LEN; i++) {
            for (int j = 0; j < ARR_LEN; j++) {
                if (arr[i][j].equals("x")) {
                    tempRow2 = i;
                    tempCol2 = j;
                }

            }
        }

        temp = arr[tempRow1][tempCol1];
        arr[tempRow1][tempCol1] = arr[tempRow2][tempCol2];
        arr[tempRow2][tempCol2] = temp;

        return arr;
    }

    public static String[][] setQuiz(String[][] arr, Player p) {

        int count = 1;

        for (int i = 0; i < ARR_LEN; i++) {
            for (int j = 0; j < ARR_LEN; j++) {

                arr[i][j] = Integer.toString(count);

                if (arr[i][j].equals("9")) {
                    p.set_Position(i, j);
                    arr[i][j] = "x";
                }

                count++;
            }
        }

        return arr;
    }

    public static String[][] AutoSuffle(Player p, String[][] arr) {
        Random ran = new Random();
        int num;
        int col = p.get_Col();
        int row = p.get_Row();
        String temp;

        for(int i = 0 ; i < 1; i++){

            num = ran.nextInt(4);

            row = p.get_Row();
            col = p.get_Col();


            if (num == 0) {
                if (p.get_Col() - 1 < 0) {
                    //System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                }
                else {
                    temp = arr[row][col];
                    arr[row][col] = arr[row][col - 1];
                    arr[row][col - 1] = temp;
                    p.set_Position(p.get_Row() , p.get_Col() - 1);
                }
            }

            else if (num == 1) {
                if (p.get_Col() + 1 > 2) {
                    //System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                }
                else {
                    temp = arr[row][col];
                    arr[row][col] = arr[row][col + 1];
                    arr[row][col + 1] = temp;
                    p.set_Position((p.get_Row() ), p.get_Col() + 1);
                }
            }

            else if (num == 2) {
                if (p.get_Row() - 1 < 0) {
                    //System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                }
                else {
                    temp = arr[row][col];
                    arr[row][col] = arr[row - 1][col];
                    arr[row - 1][col] = temp;
                    p.set_Position((p.get_Row() -1 ), p.get_Col());
                }

            }

            else if (num == 3) {
                if (p.get_Row() + 1 > 2) {
                    //System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                }
                else {
                    temp = arr[row][col];
                    arr[row][col] = arr[row + 1][col];
                    arr[row + 1][col] = temp;
                    p.set_Position(p.get_Row() + 1, p.get_Col());
                }
            }

        }

        for (int i = 0; i < ARR_LEN; i++) {
            for (int j = 0; j < ARR_LEN; j++) {

                if (arr[i][j].equals("x")) {
                    p.set_Position(i, j);
                }
            }
        }

        return arr;
    }

    public static Boolean getInput(Player p, Scanner sc, Boolean flag) {
        boolean key_while = true;
        String str;
        while (key_while) {
            System.out.println();
            System.out.println("====================================");
            System.out.println("[ Move ] a: Left s:Right w:Up z:Down");
            System.out.println("[ Exit ] k: Exit");
            System.out.print("이동키를 입력하세요 : ");
            str = sc.nextLine();

            switch (str) {
                case "a": {
                    if (p.get_Col() - 1 < 0) {
                        System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                        key_while = false;
                        break;
                    }
                    else {
                        key_while = false;
                        p.set_Position(p.get_Row(), p.get_Col() - 1);
                        break;
                    }

                }

                case "s": {
                    if (p.get_Col() + 1 > 2) {
                        System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                        key_while = false;
                        break;
                    }
                    else {
                        key_while = false;
                        p.set_Position((p.get_Row()), p.get_Col() + 1);
                        break;
                    }
                }

                case "w": {
                    if (p.get_Row() - 1 < 0) {
                        System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                        key_while = false;
                        break;
                    }
                    else {
                        key_while = false;
                        p.set_Position(p.get_Row() - 1, p.get_Col());
                        break;
                    }
                }

                case "z": {
                    if (p.get_Row() + 1 > 2) {
                        System.out.println("해당 방향으로 움직일 수 없습니다. 다시 입력해 주십시오.");
                        key_while = false;
                        break;
                    }
                    else {
                        key_while = false;
                        p.set_Position(p.get_Row() + 1, p.get_Col());
                        break;
                    }

                }

                case "k": {
                    flag = false;
                    key_while = false;
                    System.out.println("게임을 종료합니다.");
                    break;
                }

                default: {
                    System.out.println("잘못된 입력입니다 다시 입력해 주세요");
                    key_while = false;
                    break;
                }
            }
        }

        return flag;
    }
}
