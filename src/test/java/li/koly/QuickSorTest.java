package li.koly;

import li.koly.concurrent.QuickSort;
import org.junit.Test;

public class QuickSorTest {

    @Test
    public void should_sort() {
        QuickSort sort = new QuickSort();
        Integer[] result = sort.sort(new Integer[]{1, 2, 4, 3, 10, 7, 33, 40, 12});
        for(int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
