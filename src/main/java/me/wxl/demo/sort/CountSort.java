package me.wxl.demo.sort;

import java.util.Random;

import static me.wxl.demo.utils.ArrayUtil.printIntArray;

/**
 * @author wxl
 * @date 2019-02-10
 */
public class CountSort {
    public static void sort(int[] source, int[] destination, int max) {
        if (source == null || source.length < 2) {
            return;
        }
        // 声明中间数组，并将数组元素全部初始化为0
        int[] tmp = new int[max];
        // 由于int声明自动初始化，所以下面初始化是非必需的
//        for (int i = 0; i < tmp.length; i++) {
//            tmp[i] = 0;
//        }
        int len = source.length;
        for (int i = 0; i < len; i++) {
            tmp[source[i]] += 1;
        }
        for (int i = 1; i < max; i++) {
            tmp[i] += tmp[i - 1];
        }
        for (int i = len - 1; i >= 0; i--) {
            destination[tmp[source[i]] - 1] = source[i];
            tmp[source[i]]--;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        int max = 10;
        int len = 20;
        int[] source = new int[len];
        for (int i = 0; i < len; i++) {
            source[i] = random.nextInt(max);
        }
        int[] des = new int[len];
        sort(source, des, max);
        printIntArray(source, "source");
        printIntArray(des, "des   ");
    }
}
