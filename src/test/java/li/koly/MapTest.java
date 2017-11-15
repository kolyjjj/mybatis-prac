package li.koly;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

    @Test
    public void test_concurrentmap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        System.out.println(map.get("one"));
    }
}
