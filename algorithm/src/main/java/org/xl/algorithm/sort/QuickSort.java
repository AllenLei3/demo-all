package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序算法
 *
 * @author xulei
 */
public class QuickSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        quickSortInternally(array, 0, array.length - 1);
    }

    /**
     * 分区,在分区的过程中根据pivot进行排序
     */
    private static void quickSortInternally(int[] array, int start, int end) {
        // 递归终止条件
        if (start >= end) {
            return;
        }
        // 按照pivot进行递归
        int pivot = partition(array, start, end);
        quickSortInternally(array, start, pivot - 1);
        quickSortInternally(array, pivot + 1, end);
    }

    /**
     * 对 array[start…end] 分区，并返回分区后的pivot的下标
     */
    private static int partition(int[] array, int start, int end) {
        // 取最后一个元素作为分区点，这样便于区分已处理区间和未处理区间
        int pivot = array[end];
        // i表示已处理区间
        int i = start;
        // j表示未处理区间，循环取未处理区间中的元素与分区点比较，看是否要放入已处理区间
        for (int j = start; j < end; j++) {
            if (array[j] < pivot) {
                if (i != j) {
                    // 将未处理区间元素与已处理区间最后一个元素交换
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
                // 已处理区间里的元素数量加一
                i++;
            }
        }
        // 交换分区点位置
        int tmp = array[end];
        array[end] = array[i];
        array[i] = tmp;
        return i;
    }

    public static void main(String[] args) {
        int[] array = new int[]{7,6,5,4,3,2,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
