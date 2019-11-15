import java.util.concurrent.*;

public class C3_CallableDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> task = () -> {
            int sum = 0;
            for(int i = 0; i < 10; i++){
                sum += i;
            }
            return sum;
        };

        ExecutorService exr = Executors.newSingleThreadExecutor();
        Future<Integer> fur = exr.submit(task);

        Integer r1 = fur.get();
        System.out.println("Result: " + r1);

        Integer r2 = fur.get();
        System.out.println("Result: " + r2);

        exr.shutdown();

    }
}
