public class Quiz_06_04 {
    public static void main(String[] args) {
        int num = 1000;
        int temp = 0;
        int i=1;

        do{
            if(i != num)
            {
                System.out.print(i +"+");
            }

            else{
                System.out.print(i);
            }

            temp += i;
            i++;

        }while(i<=num);

        System.out.println(" = " + temp);
    }
}
