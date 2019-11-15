public class Quiz_13_06 {
    public static void main(String[] args) {
        int[][] arr = new int[3][9];

        for(int i = 0 ; i < 3 ; i ++){
            for(int j = 0 ; j < 9 ; j++){
                arr[(i)][j] = (( i + 2 )*( j + 1 ));
            }
        }

        for(int i = 0 ; i < 3 ; i ++){
            for(int j = 0 ; j < 9 ; j++){
                System.out.print(( i + 2 ) + " * " + ( j + 1 ) + " = " +arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
