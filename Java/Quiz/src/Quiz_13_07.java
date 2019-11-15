public class Quiz_13_07 {
    public static void main(String[] args) {

        int[][] arr1 = {{1,2,3,4},{5,6,7,8}};
        int col = arr1.length;              //2
        int row = arr1[(col -1)].length;    //4

        int[][] arr2 = new int[row][col];   //arr2[4][2]

        for(int i = 0 ; i < col; i++){      //2
            for(int j = 0 ; j <row ; j++){  //4
                arr2[j][i] = arr1[i][j];
            }
        }


        for(int j = 0 ; j < row; j++){
            for(int i = 0 ; i <col ; i++){
                System.out.print(arr2[j][i]);
            }
            System.out.println();
        }
    }
}
