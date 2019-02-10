package me.wxl.demo.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NormalSort {
    public <T> void bubbleSort(T[] a, Comparator<T> comparator) {
        if (a == null || a.length < 2) {
            return;
        }

        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(a[j], a[j + 1]) > 0) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    public <T> void selectionSort(T[] a, Comparator<T> comparator) {
        if (a == null || a.length < 2) {
            return;
        }

        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (comparator.compare(a[i], a[j]) > 0) {
                    swap(a, i, j);
                }
            }
        }
    }

    public <T> void insertionSort(T[] a, Comparator<T> comparator) {
        if (a == null || a.length < 2) {
            return;
        }

        for (int i = 1; i < a.length; i++) {
            T key = a[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(a[j], key) > 0) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    public <T> void mergeSort(T[] a, int left, int right, Comparator<T> comparator) {
        if (a == null || a.length < 2 || left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        // 注意要判断mid != right，防止陷入死循环。
        if (mid != right) {
            mergeSort(a, left, mid, comparator);
        }
        if (mid != left) {
            mergeSort(a, mid, right, comparator);
        }
        merge(a, left, mid, right, comparator);
    }

    public <T> void merge(T[] a, int s1, int e1, int e2, Comparator<T> comparator) {
        if (a == null || s1 > e1 || e1 > e2 || e2 - s1 < 2) {
            return;
        }
        List<T> tmp = new ArrayList<>(e2 - s1);
        int i = s1;
        int j = e1;
        while (i < e1 && j < e2) {
            if (comparator.compare(a[i], a[j]) < 0) {
                tmp.add(a[i++]);
            } else {
                tmp.add(a[j++]);
            }
        }
        while (i < e1) {
            tmp.add(a[i++]);
        }
        while (j < e2) {
            tmp.add(a[j++]);
        }

        int k = 0;
        for (i = s1; i < e2; ) {
            a[i++] = tmp.get(k++);
        }
    }

    public <T> void swap(T[] a, int left, int right) {
        T tmp = a[left];
        a[left] = a[right];
        a[right] = tmp;
    }

    public <T> void printArray(T[] a, String prefix) {
        System.out.printf("%s: %s\n", prefix, Arrays.stream(a).map(Object::toString).collect(Collectors.joining(", ")));
    }

    public static void main(String[] args) {
        Integer[][] arrays = {
            {4, 1, 3, 2, 2, 5, 4, 7, 5, 6, 8},
            {1, 3, 2, 5, 4, 7, 6, 8},
            {1, 3, 2, 5, 4},
            {1, 3, 2},
            {1, 2},
            {1}
        };
        NormalSort normalSort = new NormalSort();
        Comparator<Integer> comparator1 = Comparator.naturalOrder();
        Comparator<Integer> comparator2 = Comparator.reverseOrder();
        for (Integer[] a : arrays) {
            normalSort.insertionSort(a, comparator1);
            normalSort.printArray(a, "insertionSort");

            normalSort.selectionSort(a, comparator2);
            normalSort.printArray(a, "selectionSort");

            normalSort.bubbleSort(a, comparator1);
            normalSort.printArray(a, "bubbleSort");

            normalSort.mergeSort(a, 0, a.length, comparator2);
            normalSort.printArray(a, "mergeSort");

            normalSort.mergeSort(a, 0, a.length, comparator1);
            normalSort.printArray(a, "mergeSort");

            normalSort.selectionSort(a, comparator2);
            normalSort.printArray(a, "selectionSort");

            normalSort.bubbleSort(a, comparator1);
            normalSort.printArray(a, "bubbleSort");

            normalSort.insertionSort(a, comparator2);
            normalSort.printArray(a, "insertionSort");

            System.out.println();
        }
    }
}