public class Quiz_06_11 {

    public static void main(String[] args) {
        int sum = 0;
        int i = 1;

        do {
            if(i%2 == 0){
                sum += i;
            }

            i++;
        }while(i < 101);

        System.out.println("1부터 100까지 정수중 짝수의 합은 " + sum + "입니다");
    }
}
