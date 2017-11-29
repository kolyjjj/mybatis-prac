package li.koly.concurrent;

import java.util.concurrent.atomic.AtomicLong;

public class CasSimulator {
    private static AtomicLong test = new AtomicLong(1L);
    private long getVolatile(Object o, int offset) {
        return 1L;
    }

    private boolean compareAndSwap(Object o, int offset, long expected, long val) {
        return false;
    }

    // 如果有两个线程调用了compareAndSwap，那么一定是增加了2,而不是增加了1,因为每次通过getVolatile取到的值都是最新的。
    private void getAndIncrement(Object o, int offset) {
        while (true) {
            long curr = getVolatile(o, offset);
            if (compareAndSwap(o, offset, curr, curr + 1)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            long ret = test.getAndIncrement();
            System.out.println("run1 " + ret);
        });
        Thread t2 = new Thread(() -> {
            long ret = test.getAndIncrement();
            System.out.println("run2 " + ret);
        });

        t1.start();
        t2.start();
    }
}
