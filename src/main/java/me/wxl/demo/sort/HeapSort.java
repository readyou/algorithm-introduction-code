package me.wxl.demo.sort;

import me.wxl.demo.utils.MockUtil;

import java.util.Comparator;

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

    public static void main(String[] args) {
        for (int i = 1; i <= 11; i++) {
            Integer[] data = MockUtil.getRandomIntegerArray(i, false);
            HeapSort.sort(data, Comparator.naturalOrder());
            printArray(data, "sort  asc");
            HeapSort.sort(data, Comparator.reverseOrder());
            printArray(data, "sort desc");
            System.out.println();
        }
    }
}