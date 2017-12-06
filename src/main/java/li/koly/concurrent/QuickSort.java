package li.koly.concurrent;

import java.util.Arrays;

public class QuickSort {

    public Integer[] sort(Integer[] input) {
        Integer[] copy = Arrays.copyOf(input, input.length);
        sortIt(copy, 0, copy.length - 1);
        return copy;
    }

    private void sortIt(Integer[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sortIt(a, lo, j - 1);
        sortIt(a, j + 1, hi);
    }

    //1, 2, 4, 3, 10, 7, 33, 40, 12
    private int partition(Integer[] a, int lo, int hi) {
        System.out.println(lo + "," + hi);
        for(int x = 0; x < a.length; x++) {
            System.out.print(a[x] + " ");
        }
        System.out.println("");
        int i = lo;
        int j = hi+1;
        Integer temp = a[lo];
        while (true) {
            while (a[++i] <= temp && i < j);
            while (a[--j] >= temp && j > i);
            if (i >= j) break;
            swap(a, i, j);
        }
        if (a[lo] > a[i]) swap(a, lo, i);
        return i;
    }

    private void swap(Integer[] a, int i, int j) {
        Integer temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
