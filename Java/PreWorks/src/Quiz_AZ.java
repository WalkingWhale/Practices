public class Quiz_AZ {
    public static void main(String[] args) {

        for(int A = 1 ;A < 10; A++){
            for(int Z = 1; Z < 10; Z++){
                if(((A+Z) == 9) && (A != Z)) {
                    System.out.printf("%d %d and %d %d\n", A, Z, Z, A);
                }
            }
        }
    }
}
