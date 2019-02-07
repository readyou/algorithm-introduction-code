package me.wxl.demo.sort;

import java.util.Comparator;
import java.util.Random;

import static me.wxl.demo.utils.ArrayUtil.printArray;
import static me.wxl.demo.utils.ArrayUtil.swap;
import static me.wxl.demo.utils.MockUtil.*;

/**
 * @author wxl
 * @date 2019-02-07
 */
public class QuickSort<T> {
    private T[] data;
    private Comparator<T> comparator;
    private Random random;

    private QuickSort(T[] data, Comparator<T> comparator) {
        this.data = data;
        this.comparator = comparator;
        this.random = new Random(System.currentTimeMillis());
    }

    private void quickSort(int start, int end) {
        if (end - start < 2) {
            return;
        }
//        int p = partition(start, end);
//        quickSort(start, p);
//        quickSort(p + 1, end);
        while (start < end) {
            int p = partition(start, end);
            quickSort(start, p);
            start = p + 1;
        }
    }

    private int partition(int start, int end) {
        if (end - start < 2) {
            return start;
        }

        int randIndex = random.nextInt(end - start) + start;
        swap(data, randIndex, end - 1);

        int lastIndex = end - 1;
        T pivot = data[lastIndex];
        int i = start - 1;
        for (int j = start; j < lastIndex; j++) {
            if (comparator.compare(data[j], pivot) <= 0) {
                swap(data, ++i, j);
            }
        }
        swap(data, ++i, lastIndex);
        return i;
    }

    public static <U> void sort(U[] data, Comparator<U> comparator) {
        if (data == null || data.length < 2) {
            return;
        }
        new QuickSort<U>(data, comparator).quickSort(0, data.length);
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 11; i++) {
            Integer[] data = getIntegerArray(i, false);
            QuickSort.sort(data, Comparator.naturalOrder());
            printArray(data, "sort  asc");
            QuickSort.sort(data, Comparator.reverseOrder());
            printArray(data, "sort desc");
            System.out.println();
        }

        Integer[] data = null;
        QuickSort.sort(data, Comparator.naturalOrder());
        printArray(data, "sort desc");
        System.out.println();

        SimpleRecord[] records = getSimpleRecordArray(10, false);
        for (int i = 0; i < 10; i++) {
            if (i % 4 == 0) {
                records[i].setValue(4);
            }
            if (i % 3 == 0) {
                records[i].setValue(3);
            }
        }
        printArray(records, "before sort");
        QuickSort.sort(records, Comparator.naturalOrder());
        printArray(records, "sort asc");
        System.out.println();

    }
}
