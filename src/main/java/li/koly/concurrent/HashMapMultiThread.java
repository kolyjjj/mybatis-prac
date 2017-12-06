package li.koly.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashMapMultiThread {
    private static Map<String, String> map = new HashMap<>();

    public static class AddThread implements Runnable {
        int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for(int i = start; i < 50000; i += 2) {
                map.put(Integer.toString(i), Integer.toString(i));
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
        Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
//            Thread.sleep(3000);
            ArrayList<String> a = new ArrayList<>();
            for (String key : map.keySet()) {
                a.add(map.get(key));
            }
            System.out.println();
            System.out.println(map.size());
            System.out.println(a.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
