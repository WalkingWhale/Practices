public class Quiz_13_10 {
    public static void main(String[] args) {

        int[][] arr = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int arr_len = arr.length;

        int[][] arr_rotate = new int[arr_len][arr_len];

        for(int i = 0; i < arr.length ; i++){
            for(int j = 0; j < arr.length ; j++){
                arr_rotate[j][i] = arr[(arr_len-1)-i][j];
            }
        }

        for(int i = 0; i < arr_len ; i++){
            for(int j = 0; j < arr_len ; j++){
                System.out.print(arr_rotate[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
