package me.wxl.demo.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * @author wxl
 * @date 2019-02-10
 */
public class RadixSort {
    public static void radixSort(int[] data, int[] destination, int digitNumber) {
        char[][][] chars = {
            new char[data.length][digitNumber],
            new char[data.length][digitNumber]
        };
        int from = 0;
        char[][] fromChars = chars[from];
        for (int i = 0; i < data.length; i++) {
            fromChars[i] = String.valueOf(data[i]).toCharArray();
        }

        for (int i = digitNumber - 1; i >= 0; i--) {
            countSort(chars[from % 2], chars[1 - from % 2], i);
            from++;
        }
        char[][] result = chars[from % 2];
        for (int i = 0; i < data.length; i++) {
            destination[i] = Integer.parseInt(String.valueOf(result[i]));
        }
    }

    private static void countSort(char[][] digitsArray, char[][] sortedChars, int index) {
        // 字符映射为数字，'9'(57)
        int[] counts = new int[58];
        int len = digitsArray.length;
        for (int i = 0; i < len; i++) {
            counts[digitsArray[i][index]]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        for (int i = digitsArray.length - 1; i >= 0; i--) {
            sortedChars[counts[digitsArray[i][index]] - 1] = digitsArray[i];
            counts[digitsArray[i][index]]--;
        }
    }

    private static void insertSort(int[] a, int start, int end) {
        for (int i = start; i < end; i++) {
            int j = i - 1;
            int key = a[i];
            while (j >= start && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static void quickSort(int[] a, int start, int end) {
        if (end - start < 2) {
            return;
        }
        if (end - start < 5) {
            insertSort(a, start, end);
            return;
        }
        int p = partition(a, start, end);
        quickSort(a, start, p);
        quickSort(a, p + 1, end);
    }

    private static int partition(int[] a, int start, int end) {
        if (end - start < 2) {
            return start;
        }
        int lastIndex = end - 1;
        int mid = (start + end) / 2;
        swap(a, mid, lastIndex);
        int paviot = a[lastIndex];
        int i = start - 1;
        for (int j = start; j < lastIndex; j++) {
            if (a[j] <= paviot) {
                swap(a, ++i, j);
            }
        }
        swap(a, ++i, lastIndex);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        int len = 100000;
        Random random = new Random(System.currentTimeMillis());
        int[] a = new int[len];
        int[] b = new int[len];
        int[] c = new int[len];
        for (int i = 0; i < len; i++) {
            int tmp = 100 + random.nextInt(900);
            a[i] = b[i] = c[i] = tmp;
        }
//        printIntArray(a, "before sort");
        long start = System.currentTimeMillis();
        int[] d = new int[len];
        radixSort(a, d, 3);
        long end = System.currentTimeMillis();
        long cost1 = end - start;
//        printIntArray(c, "after  sort");

        start = System.currentTimeMillis();
        Arrays.sort(a);
        end = System.currentTimeMillis();
        long cost2 = end - start;

        start = System.currentTimeMillis();
        quickSort(b, 0, len);
        end = System.currentTimeMillis();
        long cost3 = end - start;

        start = System.currentTimeMillis();
        insertSort(c, 0, len);
        end = System.currentTimeMillis();
        long cost4 = end - start;

        System.out.printf("costTime: radixSort=%d, system.quickSort=%d, simpleQuickSort=%d, insertSort=%d (ms)\n", cost1, cost2, cost3, cost4);
        // output: costTime: radixSort=108, system.quickSort=16, simpleQuickSort=23, insertSort=4172 (ms)
    }
}
