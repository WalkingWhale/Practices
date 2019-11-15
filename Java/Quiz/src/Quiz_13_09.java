public class Quiz_13_09 {
    public static void main(String[] args) {

        int[] arr = {210, 19, 72, 129, 34};
        int temp;

        for(int i = 0; i < arr.length -1 ; i++){

            for(int j = 0; j < (arr.length - 1) - i ; j++){
                if(arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }

        }

        for(int i = 0 ; i < arr.length ; i++ ){
            System.out.print(arr[i] + " ");
        }
    }
}
