package li.koly;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

    @Test
    public void test_concurrentmap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        // put调用的几种情况：1,空HashMap时候的调用;2,非空hashMap的时候调用，然后传入的key不一样;
        // 3，非空hashMap传入的key一样;4,传入不同的key值的时候，之前的数组已经放不下了，需要扩容。
        // 扩容需要做的事情，创建一个新的map，然后将原来map的值copy到新的map里面；第一步非并发，第二步copy的过程是可以进行并发的。分步并发的思想。
        map.put("one", "1");
        map.put("two", "2");
        System.out.println(map.get("one"));
    }

    @Test
    public void should_enlarge_map() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(2);
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");
        map.put("four", "4");
        map.put("five", "5");
        System.out.println(map.get("three"));
    }

    @Test
    public void should_resize_map() {
        Map<String, String> map = new HashMap(2);
        map.put("1", "one"); // hash for 1 is 1
        map.put("2", "two"); // hash for 2 is 0
        map.put("3", "three"); // hash for 1 when size is 2: 1
        System.out.println(map.get("three"));
    }
}
