public class Quiz_06_07 {

    public static void main(String[] args) {
        int num = 100;

        for(int i = 1; i < num ; i++){
            if((i % 7 == 0) || (i % 9 == 0)){
                System.out.print(i + " ");
            }
        }
    }
}
