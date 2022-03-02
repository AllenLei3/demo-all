package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 选择排序算法
 *
 * @author xulei
 */
public class SelectSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 外层循环用于确定交换次数
        for (int i = 0; i < array.length; i++) {
            // 先让最小值等于未排序区间中的第一个元素
            int minValue = array[i];
            int minIndex = i;
            // 内存循环用于获取未排序区间中的最小值
            for (int j = i + 1; j < array.length; j++) {
                if (minValue > array[j]) {
                    minValue = array[j];
                    minIndex = j;
                }
            }
            // 将未排序区间的第一个值和最小值交换
            array[minIndex] = array[i];
            array[i] = minValue;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{7,6,5,4,3,2,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
