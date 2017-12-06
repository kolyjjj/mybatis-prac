package li.koly;

import li.koly.concurrent.QuickSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class QuickSorTest {

    @Test
    public void should_sort() {
        QuickSort sort = new QuickSort();
        Integer[] result = sort.sort(new Integer[]{1, 2, 4, 3, 10, 7, 33, 40, 12});
        for(int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    @Test
    public void should_jj() {
        int i = 0;
        List<Integer> a = Arrays.asList(1, 2, 3);
        if(a.get(++i) == 2 && i == 1) {
            System.out.println("hello");
        }
    }

}
