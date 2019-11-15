class task extends Thread{
    @Override
    public void run() {
        int n1 = 10;
        int n2 = 20;
        String name = Thread.currentThread().getName();
        System.out.println(name +": "+(n1 + n2));
    }
}
public class A2_MakeThreadDemo {
    public static void main(String[] args) {
        task t1 = new task();

        t1.start();

        System.out.println("End " + Thread.currentThread().getName());
    }
}
