package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 桶排序算法
 *
 * 从时间复杂度的角度来看，核心逻辑只有每个桶通过快排进行排序，最后循环取出每个桶中的元素
 * 当桶的个数接近元素个数时，时间复杂度接近O(n)，前面的取最大值、最小值、分桶操作都是前置条件。
 *
 * @author xulei
 */
public class BucketSort {

    public static void sort(int[] array, int bucketSize) {
        if (array == null || array.length < 2) {
            return;
        }
        // 获取最大值、最小值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        // 根据最大值最小值求桶数量
        int bucketCount = (max - min) / bucketSize + 1;
        int[][] buckets = new int[bucketCount][bucketSize];
        // 记录每个桶的元素数量
        int[] index = new int[bucketCount];
        // 循环数组填充到桶里
        for (int i = 0; i < array.length; i++) {
            int bucketIndex = (array[i] - min) / bucketSize;
            // 桶满了进行扩容
            if (index[bucketIndex] == buckets[bucketIndex].length) {
                int[] temp = buckets[bucketIndex];
                int[] newBucket = new int[temp.length * 2];
                System.arraycopy(temp, 0, newBucket, 0, temp.length);
                buckets[bucketIndex] = newBucket;
            }
            // 放到指定桶里的数组的后面
            buckets[bucketIndex][index[bucketIndex]++] = array[i];
        }
        // 循环对桶内元素进行快排，并把桶内元素依次赋值到原数组
        int k = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (index[i] == 0) {
                continue;
            }
            QuickSort.sort(buckets[i]);
            for (int j = 0; j < index[i]; j++) {
                array[k++] = buckets[i][j];
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{8,7,6,5,4,3,2,1};
        sort(array, 2);
        System.out.println(Arrays.toString(array));
    }
}
