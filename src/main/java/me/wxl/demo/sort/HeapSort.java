package me.wxl.demo.sort;

import java.util.Comparator;
import java.util.Random;

import static me.wxl.demo.utils.ArrayUtil.printArray;
import static me.wxl.demo.utils.ArrayUtil.swap;

public class HeapSort<T> {
    private T[] data;
    private int size;
    private int root;
    private Comparator<T> comparator;

    private HeapSort(T[] data, Comparator<T> comparator) {
        this.data = data;
        this.root = 0;
        this.size = data.length;
        this.comparator = comparator;
    }

    private HeapSort<T> setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }

    private void recursiveHeapify(int i) {
        if (i >= size) {
            return;
        }
        int next = getNext(i);
        if (next != i) {
            swap(data, next, i);
            recursiveHeapify(next);
        }
    }

    private int getNext(int i) {
        int right = (i + 1) * 2;
        int left = right - 1;
        int next = i;
        if (left < size && comparator.compare(data[left], data[next]) > 0) {
            next = left;
        }
        if (right < size && comparator.compare(data[right], data[next]) > 0) {
            next = right;
        }
        return next;
    }

    private void buildHeap() {
        // parent(i) = (i + 1) / 2 - 1
        final int lastParent = ((data.length - 1) + 1) / 2 - 1;
        // 从最后一个非叶子结点，直到根，进行堆调整
        for (int i = lastParent; i >= root; i--) {
            recursiveHeapify(i);
        }
    }

    private void sort() {
        for (int i = size - 1; i > root; i--) {
            swap(data, root, i);
            size--;
            recursiveHeapify(root);
        }
        size = data.length;
    }

    public static <U> void sort(U[] data, Comparator<U> comparator) {
        HeapSort<U> heapSort = new HeapSort<>(data, comparator);
        heapSort.buildHeap();
        heapSort.sort();
    }

    public static Integer[] getInitData(int n, boolean skipFirst) {
        if (n < 1) {
            throw new IllegalArgumentException("n不能小于1");
        }
        Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }
        if (n > 1) {
            Random random = new Random();
            for (int i = skipFirst ? 1 : 0; i < n; i++) {
                int j = skipFirst ? (1 + random.nextInt(n - 1)) : random.nextInt(n);
                int tmp = result[i];
                result[i] = result[j];
                result[j] = tmp;
            }
        }
        printArray(result, "init data");
        return result;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 11; i++) {
            Integer[] data = getInitData(i, false);
            HeapSort.sort(data, Comparator.naturalOrder());
            printArray(data, "sort  asc");
            HeapSort.sort(data, Comparator.reverseOrder());
            printArray(data, "sort desc");
            System.out.println();
        }
    }
}