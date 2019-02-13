package me.wxl.demo.utils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static me.wxl.demo.utils.ArrayUtil.printArray;

/**
 * @author wxl
 * @date 2019-02-07
 */
public class MockUtil {
    /**
     * 造一个随机数组
     *
     * @param n         数组大小
     * @param skipFirst 是不不包含第0个元素（因为有时候不包含第0个元素，利于处理）
     * @return
     */
    public static Integer[] getRandomIntegerArray(int n, boolean skipFirst) {
        if (n < 1) {
            throw new IllegalArgumentException("n不能小于1");
        }
        Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }
        shuffle(result, skipFirst);
        printArray(result, "init data");
        return result;
    }

    public static SimpleRecord[] getSimpleRecordArray(int n, boolean skipFirst) {
        if (n < 1) {
            throw new IllegalArgumentException("n不能小于1");
        }
        SimpleRecord[] simpleRecords = new SimpleRecord[n];
        for (int i = 0; i < n; i++) {
            simpleRecords[i] = new SimpleRecord(i, i);
        }
        shuffle(simpleRecords, skipFirst);
        for (int i = 0; i < n; i++) {
            simpleRecords[i].index = i;
        }
        printArray(simpleRecords, "init simpleRecords");
        return simpleRecords;
    }

    public static <T> void shuffle(T[] data, boolean skipFirst) {
        int n = data.length;
        if (n > 1) {
            Random random = new Random();
            for (int i = skipFirst ? 1 : 0; i < n; i++) {
                int j = skipFirst ? (1 + random.nextInt(n - 1)) : random.nextInt(n);
                T tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
            }
        }
    }

    public static class SimpleRecord implements Comparable<SimpleRecord>, Cloneable {
        private int index;
        private int value;

        public SimpleRecord(int index, int value) {
            this.index = index;
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public int getValue() {
            return value;
        }

        public SimpleRecord setValue(int value) {
            this.value = value;
            return this;
        }

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof SimpleRecord)) {
                return false;
            }
            return ((SimpleRecord) obj).value == this.value;
        }

        @Override
        public String toString() {
            return String.format("(%d: %d)", index, value);
        }

        @Override
        public int compareTo(SimpleRecord o) {
            if (o == null) {
                return 0;
            }
            return value - o.value;
        }

        public SimpleRecord clone() {
            try {
                super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return new SimpleRecord(this.index, this.value);
        }
    }

    public static void main(String[] args) {
        // 简单演示一下Map的key不应该变化，因为变化了之后，可以再也找不到Map中存储的对象了。
        Map<SimpleRecord, Integer> map = new ConcurrentHashMap<>();
        SimpleRecord record = new SimpleRecord(1, 2);
        map.put(record, 10);
        System.out.println(map.get(record));
        record.setValue(20);
        System.out.println(map.get(record));
        record.setValue(2);
        System.out.println(map.get(record));
    }
}
