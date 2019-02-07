package me.wxl.demo.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class ArrayUtil {
    public static <T> void swap(T[] a, int left, int right) {
        T tmp = a[left];
        a[left] = a[right];
        a[right] = tmp;
    }

    public static <T> void printArray(T[] a, String prefix) {
        System.out.printf("%s: %s\n", prefix, Arrays.stream(a).map(Object::toString).collect(Collectors.joining(", ")));
    }

    public static <T> void shuffle(T[] a) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            swap(a, i, random.nextInt(a.length));
        }
    }
}