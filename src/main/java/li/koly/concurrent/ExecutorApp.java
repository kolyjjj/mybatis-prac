package li.koly.concurrent;

import java.util.concurrent.*;

public class ExecutorApp {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        ExecutorService single = Executors.newSingleThreadExecutor();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 50, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
        Runnable run1 = () -> {
            try {
                Thread.sleep(500);
                System.out.println("run1 is running " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable run2 = () -> {
            try {
                Thread.sleep(300);
                System.out.println("run2 is running " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
//        threadPool.execute(run1);
//        threadPool.execute(run2);
        Thread t1 = new Thread(run1);
        t1.run();
        t1.start();

    }
}
