package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 插入排序算法
 *
 * @author xulei
 */
public class InsertSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 因为初始已排序区间为数组的第一个元素，所以下标从1开始
        for (int i = 1; i < array.length; i++) {
            // 获取当前未排序区间的第一个元素
            int value = array[i];
            // 获取当前已排序区间的最后一个元素下标
            int j = i - 1;
            // 这个循环是遍历已排序区间，与当前这个未排序区间的第一个元素依次进行比较，确定其插入位置
            for (; j >= 0; j--) {
                if (value < array[j]) {
                    // 向后移动数据
                    array[j + 1] = array[j];
                } else {
                    // 因为前面已经是排好序的了，直接返回
                    break;
                }
            }
            // 把数组元素后移后，最后会空出一个元素来，把value赋进去，就保证了已排序区间的有序性，因为上面有个j--，在退出循环后这里要加上1
            array[j + 1] = value;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{7,6,5,4,3,2,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}