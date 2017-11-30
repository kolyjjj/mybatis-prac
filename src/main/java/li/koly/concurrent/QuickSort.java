package li.koly.concurrent;

import java.util.Arrays;

public class QuickSort {
    public Integer[] sort(Integer[] input) {
        Integer[] copy = Arrays.copyOf(input, input.length);
        sortIt(copy, 0, copy.length - 1);
        return copy;
    }

    private void sortIt(Integer[] input, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(input, lo, hi);
        sortIt(input, lo, j - 1);
        sortIt(input, j + 1, hi);
    }

    private int partition(Integer[] a, int lo, int hi) {
        int i = lo;
        int j = hi;
        Integer temp = a[lo];
        while (i < j) {
            while (a[j] <= temp && j > i) {
                j--;
            }
            while (a[i] >= temp && i < j) {
                i++;
            }
            if (i == j) break;
            swap(a, i, j);
        }
        swap(a, lo, i);
        return i;
    }

    private void swap(Integer[] a, int i, int j) {
        Integer temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
