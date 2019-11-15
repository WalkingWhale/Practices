import java.util.Scanner;

public class ThreadMain {

    public ThreadMain(){

    }
    static void init() {
        System.out.println("숫자를 입력해 주세요");
        Scanner sc = new Scanner(System.in);
        String s_num = sc.nextLine();
        int n_num = Integer.parseInt(s_num);

        try{
            Thread tsub = new ThreadSub(n_num);
            tsub.start();
        }catch(Exception e){
            System.out.println("예외: " + e);
        }

        System.out.println("이름을 입력해 주세요.");
        String s_name = sc.nextLine();
    }

    static class ThreadSub extends Thread{
        int nNum;

        public ThreadSub(int nNum) {
            this.nNum = nNum;
        }

        @Override
        public void run () {
            int i = 0;
            try {
                while (i < nNum) {
                    Thread.sleep(1000);
                    i = i + 1;
                    System.out.println("Thread : " + i);
                }
            } catch (Exception e) {
                System.out.println("예외 : " + e);
            }
        }
    }

    public static void main(String[] args) {
        ThreadMain tm = new ThreadMain();
        tm.init();
    }

}
