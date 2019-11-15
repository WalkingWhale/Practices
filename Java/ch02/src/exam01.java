import com.sun.org.apache.xpath.internal.operations.Mod;

public class exam01 {
    public static void main(String[] args) {
        int LM = 0;
        int SUM;
        int N, J, R;
        int K;

        for (N = 4; N <= 1000; N++) {

            SUM = 0;

            K = (int) (N / 2);

            for (J = 1; J <= K; J++) {
                R = N % J;

                if (R == 0) {
                    SUM += J;
                }

                else{
                    continue;
                }
            }

            if (N == SUM) {
                System.out.println(N + " ");
                LM += 1;
            }

            else {
                continue;
            }
        }

        System.out.println(LM);

    }

}

