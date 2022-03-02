package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序算法
 *
 * @author xulei
 */
public class BubbleSort {

    /**
     * 冒泡排序
     */
    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 外层循环定义每次冒泡需要比较的元素个数，随着冒泡执行，待排序的元素个数会递减
        for (int i = array.length; i > 1; i--) {
            // 内层循环定义每次冒泡过程中，元素比较的次数
            for (int j = 0; j < i - 1; j++) {
                if (array[j] > array[j+1]) {
                    int tmp = array[j+1];
                    array[j + 1] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }

    /**
     * 如果已经是有序的了，可以提前退出排序
     */
    public static void betterSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = array.length; i > 1; i--) {
            // 排序提前结束的标志
            boolean flag = false;
            for (int j = 0; j < i - 1; j++) {
                if (array[j] > array[j+1]) {
                    int tmp = array[j+1];
                    array[j + 1] = array[j];
                    array[j] = tmp;
                    flag = true;
                }
            }
            // 没有进行过任何一次数据交换，表示现在的数据已经是有序的了
            if (!flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{7,6,5,4,3,2,1};
        betterSort(array);
        System.out.println(Arrays.toString(array));
    }
}
