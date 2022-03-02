package org.xl.algorithm.sort;

import java.util.Arrays;

/**
 * 计数排序算法，假设数组中存储的都是非负整数
 *
 * @author xulei
 */
public class CountingSort {

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        // 获取最大值做为桶的数量。因为桶必须从0开始，所以不需要最小值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        // 记录每个桶的元素数量,下标大小[0,max]
        int[] count = new int[max + 1];
        // 计算每个元素的个数
        for (int i = 0; i < array.length; i++) {
            count[array[i]]++;
        }
        // 依次累加
        for (int i = 1; i < max + 1; i++) {
            count[i] = count[i-1] + count[i];
        }
        int[] tmp = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            // array[i]为分数，count[array[i]]为小于等于这个分数的数量
            int index = count[array[i]] - 1;
            tmp[index] = array[i];
            // 小于等于这个分数的数量减1
            count[array[i]]--;
        }
        // 将结果拷贝回array数组
        System.arraycopy(tmp, 0, array, 0, array.length);
    }

    public static void main(String[] args) {
        int[] array = new int[]{8,7,6,5,4,3,2,1};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
