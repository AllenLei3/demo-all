package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序算法
 *
 * @author xulei
 */
public class MergeSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        mergeSortInternally(array, 0, array.length - 1);
    }

    /**
     * 分解
     */
    private static void mergeSortInternally(int[] array, int start, int end) {
        // 递归终止条件
        if (start >= end) {
            return;
        }
        // 取中间位置,防止(start + end)的和超过int类型最大值
        int middle = start + (end - start) / 2;
        mergeSortInternally(array, start, middle);
        mergeSortInternally(array, middle + 1, end);
        merge(array, start, middle, end);
    }

    /**
     * 对array数组的 [start-middle]、[(middle+1)-end] 部分进行合并排序
     */
    private static void merge(int[] array, int start, int middle, int end) {
        int[] tmp = new int[end - start + 1];
        int i = start;
        int j = middle + 1;
        int k = 0;
        // 比较
        while (i <= middle && j <= end) {
            if (array[i] < array[j]) {
                tmp[k++] = array[i++];
            } else {
                tmp[k++] = array[j++];
            }
        }
        // 判断哪个子数组中有剩余的数据，就将剩余的数据拷贝到临时数组tmp中
        if (i <= middle) {
            while (i <= middle) {
                tmp[k++] = array[i++];
            }
        } else {
            while (j <= end) {
                tmp[k++] = array[j++];
            }
        }
        // 将tmp数组中的数据拷贝回array
        for (int i1 = 0; i1 <= end - start; i1++) {
            array[start + i1] = tmp[i1];
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{7,6,5,4,3,2,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
