package me.wxl.demo.sort;

import me.wxl.demo.utils.MockUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static me.wxl.demo.utils.ArrayUtil.printArray;
import static me.wxl.demo.utils.ArrayUtil.swap;

public class HeapSortFromOne<T> {
    private T[] data;
    private int size;
    private int root;
    private Comparator<T> comparator;

    public HeapSortFromOne(T[] data, Comparator<T> comparator) {
        this.data = data;
        this.size = data.length - 1;
        this.root = 1;
        this.comparator = comparator;
    }

    public HeapSortFromOne<T> setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        return this;
    }

    public void recursiveHeapify(int i) {
        if (i > size) {
            return;
        }
        int next = getNext(i);
        if (next != i) {
            swap(data, next, i);
            recursiveHeapify(next);
        }
    }

    public void heapify(int i) {
        while (i <= size) {
            int next = getNext(i);
            if (next != i) {
                swap(data, next, i);
                i = next;
            } else {
                break;
            }
        }
    }

    public int getNext(int i) {
        int left = i * 2;
        int right = left + 1;
        int next = i;
        if (left <= size && comparator.compare(data[left], data[next]) > 0) {
            next = left;
        }
        if (right <= size && comparator.compare(data[right], data[next]) > 0) {
            next = right;
        }
        return next;
    }

    public void buildHeap() {
        // parent(i) = i / 2
        final int lastParent = size / 2;
        // 从最后一个非叶子结点，直到根，进行堆调整
        for (int i = lastParent; i >= root; i--) {
            recursiveHeapify(i);
        }
    }

    public void sort() {
        for (int i = size; i > root; i--) {
            swap(data, root, i);
            size--;
            heapify(root);
        }
        // 还原size
        size = data.length - 1;
    }

    // 打印堆：由于字母宽度不一致，且有的是1位，有的是多位，所以会千万一定的偏移。
    public void printHeap() {
        System.out.println("--------------------------------------------------------------");
        List<String> strings = new ArrayList<>();
        int nodesNumber = data.length - 1;
        int depth = (int) Math.ceil(Math.log(nodesNumber + 1) / Math.log(2));
        final int fieldWidth = 2;

        int prefixEmptyCount = 0;
        int suffixEmptyCount = 1;
        while (depth > 0) {
            int start = (int) Math.pow(2, depth - 1);
            int end = (int) Math.pow(2, depth) - 1;
            if (end > nodesNumber) {
                end = nodesNumber;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = start; i <= end; i++) {
                for (int j = 0; j < prefixEmptyCount * fieldWidth; j++) {
                    builder.append(" ");
                }
                builder.append(data[i].toString());
                for (int j = 0; j < suffixEmptyCount * fieldWidth; j++) {
                    builder.append(" ");
                }
            }
            strings.add(0, builder.toString());
            depth--;
            prefixEmptyCount = prefixEmptyCount * 2 + 1;
            suffixEmptyCount = suffixEmptyCount * 2 + 1;
        }
        System.out.println(strings.stream().collect(Collectors.joining("\n")));
        System.out.println("--------------------------------------------------------------");
    }

    public static <U> void sort(U[] data, Comparator<U> comparator) {
        HeapSortFromOne<U> heapSortFromOne = new HeapSortFromOne<>(data, comparator);
        heapSortFromOne.buildHeap();
        heapSortFromOne.sort();
    }

    public static void main(String[] args) {
        Integer[] data;
        HeapSortFromOne<Integer> heap;

        for (int i = 2; i <= 11; i++) {
            data = MockUtil.getRandomIntegerArray(i, true);
            heap = new HeapSortFromOne<>(data, Comparator.naturalOrder());
            heap.buildHeap();
            heap.sort();
            heap.printHeap();

            heap.setComparator(Comparator.reverseOrder());
            heap.buildHeap();
            heap.sort();
            heap.printHeap();
        }

        Integer[] data2 = {0, 4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        HeapSortFromOne.sort(data2, Comparator.reverseOrder());
        printArray(data2, "sorted data:");
    }
}