class Counter2{
    private int counter = 0;

    synchronized public void increment(){
        this.counter++;
    }

    synchronized public void decrement(){
        this.counter--;
    }

    public int getCounter() {
        return counter;
    }
}
public class B2_MutualAccessSyncMethod {
    public static Counter2 cnt = new Counter2();

    public static void main(String[] args) throws InterruptedException{
        Runnable task1 = () -> {
            for(int i = 0 ; i < 1000; i++){
                cnt.increment();
            }
        };

        Runnable task2 = () -> {
            for(int i = 0 ; i < 1000; i++){
                cnt.decrement();
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();      // t1이 참조하는 쓰레드의 종료를 기다림
        t2.join();      // t2가 참조하는 쓰레드의 종료를 기다림

        // 스레드가 종료되면 출력을 진행함. 위 join의 영향
        System.out.println(cnt.getCounter());
    }
}
