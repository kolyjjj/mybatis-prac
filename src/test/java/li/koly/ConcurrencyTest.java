package li.koly;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyTest {

    @Test
    public void test_concurrentmap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        System.out.println(map.get("one"));
    }

    @Test
    public void test_atomic_integer() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.incrementAndGet();
        atomicInteger.compareAndSet(1, 5);
        System.out.println(atomicInteger.getAndAdd(2));
    }

    @Test
    public void test_countdown_latch() {
        CountDownLatch latch = new CountDownLatch(1);
        Runnable thread1 = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        };
        new Thread(thread1).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
