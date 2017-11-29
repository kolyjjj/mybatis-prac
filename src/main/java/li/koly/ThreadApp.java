package li.koly;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * message
 *
 * @author koly
 * @date 17-11-29
 */
public class ThreadApp {
    public static void main(String[] args) {
        Runnable run1 = () -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "run1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable run2 = () -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "run2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

//        Thread t1 = new Thread(run1);
//        Thread t2 = new Thread(run2);
//        t1.start();
//        t2.start();
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        Executors.newFixedThreadPool(10);
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(10);
        pool.execute(run1);
        pool.execute(run2);
    }
}
