package org.xl.algorithm.leetcode;

import org.xl.algorithm.sort.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xulei
 */
public class 数组 {

    /**
     * 找出数组中重复的数字。
     *
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     *
     * 示例 1：
     *
     * 输入：
     * [2, 3, 1, 0, 2, 5, 3]
     * 输出：2 或 3
     *
     * 思路：将索引值放置到指定索引上，当有多个值放到同一个索引上时，该值即为重复元素
     */
    public int findRepeatNumber(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            // 如果索引值与索引对应元素相等,则跳过
            if (nums[i] == i) {
                i++;
                continue;
            }
            // 有重复元素则直接退出
            if (nums[i] == nums[nums[i]]) {
                return nums[i];
            }
            // 交换元素保证索引值与索引对应元素相等
            int tmp = nums[i];
            nums[i] = nums[tmp];
            nums[tmp] = tmp;
        }
        return -1;
    }

    /**
     * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * 示例:
     *
     * 现有矩阵 matrix 如下：
     *
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     * 给定 target = 5，返回 true。
     * 给定 target = 20，返回 false。
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        // 取矩阵右上角元素
        int i = 0, j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
            int value = matrix[i][j];
            if (value == target) {
                return true;
            }
            if (value < target) {
                // 向下移动
                i++;
            } else {
                // 向左移动
                j--;
            }
        }
        return false;
    }

    /**
     * 编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为 汉明重量)。
     *
     * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
     * 在 Java 中，编译器使用 二进制补码 记法来表示有符号整数。因此，在上面的 示例 3 中，输入表示有符号整数 -3。
     *  
     * 示例 1：
     *
     * 输入：n = 11 (控制台输入 00000000000000000000000000001011)
     * 输出：3
     * 解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。
     *
     * 示例 2：
     *
     * 输入：n = 128 (控制台输入 00000000000000000000000010000000)
     * 输出：1
     * 解释：输入的二进制串 00000000000000000000000010000000 中，共有一位为 '1'。
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n = n & (n - 1);
            count++;
        }
        return count;
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 给你一个可能存在 重复 元素值的数组 numbers ，它原来是一个升序排列的数组，并按上述情形进行了一次旋转。
     * 请返回旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一次旋转，该数组的最小值为 1。  
     *
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     *
     * 示例 1：
     *
     * 输入：numbers = [3,4,5,1,2]
     * 输出：1
     *
     * 示例 2：
     *
     * 输入：numbers = [2,2,2,0,1]
     * 输出：0
     */
    public int minArray(int[] numbers) {
        int left = 0, right = numbers.length - 1;
        // 通过二分查找旋转点
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (numbers[middle] > numbers[right]) {
                left = middle + 1;
            } else if (numbers[middle] < numbers[right]) {
                right = middle;
            } else {
                right = right - 1;
            }
        }
        return numbers[left];
    }

    /**
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用。
     *
     * 示例 1：
     *
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     *
     * 示例 2：
     *
     * 输入：board = [["a","b"],["c","d"]], word = "abcd"
     * 输出：false
     */
    public boolean exist(char[][] board, String word) {
        char[] charArray = word.toCharArray();
        // 选择从哪一个单元格开始进行匹配
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (dfs(board, charArray, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // i、j为board的横纵坐标，k为当前待匹配字符的索引
    private boolean dfs(char[][] board, char[] word, int i, int j, int k) {
        // 索引越界
        if (i >= board.length || i < 0 || j < 0 || j >= board[i].length) {
            return false;
        }
        // 与目标字符不匹配
        if (board[i][j] != word[k]) {
            return false;
        }
        // 如果字符全部匹配则返回成功
        if (k == word.length - 1) {
            return true;
        }
        // 将访问过的字符设为特殊字符,避免和目标字符再次匹配
        board[i][j] = '\0';
        // 递归遍历
        boolean result = dfs(board, word, i + 1, j, k + 1) || dfs(board, word, i - 1, j, k + 1) ||
                dfs(board, word, i, j + 1, k + 1) || dfs(board, word, i, j - 1, k + 1);
        // 每次回溯都回退为原数据
        board[i][j] = word[k];
        return result;
    }

    /**
     * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。
     * 一个机器人从坐标 [0, 0] 的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），
     * 也不能进入行坐标和列坐标的数位之和大于k的格子。
     * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，
     * 因为3+5+3+8=19。请问该机器人能够到达多少个格子？
     *
     * 示例 1：
     *
     * 输入：m = 2, n = 3, k = 1
     * 输出：3
     *
     * 示例 2：
     *
     * 输入：m = 3, n = 1, k = 0
     * 输出：1
     */
    public int movingCount(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.visited = new boolean[m][n];
        return dfs(0, 0, 0, 0);
    }

    private int m, n, k;
    private boolean[][] visited;

    // si为i的数字之和，sj为j的数字之和
    private int dfs(int i, int j, int si, int sj) {
        if (i >= m || j >= n || si + sj > k || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        return 1 + dfs(i + 1, j, (i + 1) % 10 != 0 ? si + 1 : si - 8, sj) +
                dfs(i, j + 1, si, (j + 1) % 10 != 0 ? sj + 1 : sj - 8);
    }

    /**
     * 输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。
     *
     * 示例 1:
     *
     * 输入: n = 1
     * 输出: [1,2,3,4,5,6,7,8,9]
     */
    public int[] printNumbers(int n) {
        int max = 1;
        for (int i = 0; i < n; i++) {
            max = max * 10;
        }
        max = max - 1;
        int[] result = new int[max];
        for (int i = 0; i < max; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数在数组的前半部分，所有偶数在数组的后半部分。
     *
     * 示例：
     *
     * 输入：nums = [1,2,3,4]
     * 输出：[1,3,2,4]
     * 注：[3,1,2,4] 也是正确的答案之一。
     */
    public int[] exchange(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            // 如果是奇数，则奇数区间向右移动
            if (nums[j] % 2 == 1) {
                // 交换奇偶
                if (i != j) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
                // 奇数区间向右移动
                i++;
            }
        }
        return nums;
    }

    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     *
     * 示例 1：
     *
     * 输入：matrix = [
     * [1,2,3],
     * [4,5,6],
     * [7,8,9]
     * ]
     * 输出：[1,2,3,6,9,8,7,4,5]
     *
     * 示例 2：
     *
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0];
        }
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        int[] result = new int[(bottom + 1) * (right + 1)];
        int index = 0;
        while (true) {
            // left to right
            for (int i = left; i <= right; i++) {
                result[index++] = matrix[top][i];
            }
            if (++top > bottom) {
                break;
            }
            // top to bottom
            for (int i = top; i <= bottom; i++) {
                result[index++] = matrix[i][right];
            }
            if (--right < left) {
                break;
            }
            // right to left
            for (int i = right; i >= left; i--) {
                result[index++] = matrix[bottom][i];
            }
            if (--bottom < top) {
                break;
            }
            // bottom to top
            for (int i = bottom; i >= top; i--) {
                result[index++] = matrix[i][left];
            }
            if (++left > right) {
                break;
            }
        }
        return result;
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     *
     * 示例 1:
     *
     * 输入: [1, 2, 3, 2, 2, 2, 5, 4, 2]
     * 输出: 2
     *
     * 本题常见的三种解法：
     *
     * 哈希表统计法：遍历数组 nums ，用 HashMap 统计各数字的数量，即可找出 众数 。此方法时间和空间复杂度均为 O(N)。
     * 数组排序法：将数组 nums 排序，数组中点的元素 一定为众数。
     * 摩尔投票法：核心理念为 票数正负抵消 。此方法时间和空间复杂度分别为 O(N) 和 O(1)，为最佳解法。
     */
    public int majorityElement(int[] nums) {
        int x = 0, vote = 0;
        for (int num : nums) {
            // 如果当前平票，众数为当前值
            if (vote == 0) {
                x = num;
            }
            // 判断当前值是否为众数
            vote += num == x ? 1 : -1;
        }
        return x;
    }

    /**
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
     *
     * 示例 1：
     *
     * 输入：arr = [3,2,1], k = 2
     * 输出：[1,2] 或者 [2,1]
     *
     * 示例 2：
     *
     * 输入：arr = [0,1,2,1], k = 1
     * 输出：[0]
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        QuickSort.sort(arr);
        return Arrays.copyOf(arr, k);
    }

    /**
     * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
     *
     * 示例 1:
     *
     * 输入: [10,2]
     * 输出: "102"
     *
     * 示例 2:
     *
     * 输入: [3,30,34,5,9]
     * 输出: "3033459"
     *
     * 思路：
     * 对nums数组进行自定义排序
     * 若拼接字符串 x + y > y + x ，则 x “大于” y ；
     * 反之，若 x + y < y + x ，则 x “小于” y ；
     */
    public String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = String.valueOf(nums[i]);
        }
        // 自定义排序
        quickSort(strs, 0, strs.length - 1);
        StringBuilder res = new StringBuilder();
        for (String s : strs) {
            res.append(s);
        }
        return res.toString();
    }

    private void quickSort(String[] strs, int l, int r) {
        if (l >= r) {
            return;
        }
        int i = l, j = r;
        String tmp = strs[i];
        while (i < j) {
            while((strs[j] + strs[l]).compareTo(strs[l] + strs[j]) >= 0 && i < j) j--;
            while((strs[i] + strs[l]).compareTo(strs[l] + strs[i]) <= 0 && i < j) i++;
            tmp = strs[i];
            strs[i] = strs[j];
            strs[j] = tmp;
        }
        strs[i] = strs[l];
        strs[l] = tmp;
        quickSort(strs, l, i - 1);
        quickSort(strs, i + 1, r);
    }

    /**
     * 统计一个数字在排序数组中出现的次数
     *
     * 示例 1:
     *
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: 2
     *
     * 示例 2:
     *
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: 0
     */
    public int search(int[] nums, int target) {
        // 通过二分找到第一个匹配的位置
        int l = 0, r = nums.length - 1;
        int mid = -1;
        while (l <= r && mid == -1) {
            int middle = l + (r - l) / 2;
            if (nums[middle] < target) {
                l = middle + 1;
            } else if (nums[middle] > target) {
                r = middle - 1;
            } else {
                mid = middle;
            }
        }
        if (mid == -1) {
            return 0;
        }
        // 从命中的值开始向左扩散
        l = mid;
        while (l >= 0 && nums[l] == target) {
            l--;
        }
        // 从命中的值开始向右扩散
        r = mid;
        while (r < nums.length && nums[r] == target) {
            r++;
        }
        return r - l - 1;
    }

    /**
     * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
     * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
     *
     * 示例 1:
     *
     * 输入: [0,1,3]
     * 输出: 2
     *
     * 示例 2:
     *
     * 输入: [0,1,2,3,4,5,6,7,9]
     * 输出: 8
     */
    public int missingNumber(int[] nums) {
        int l = 0, r = nums.length - 1;
        // 通过二分找索引值与对应元素不匹配的位置
        while (l <= r) {
            int middle = l + (r - l) / 2;
            if (nums[middle] == middle) {
                l = middle + 1;
            } else {
                r = middle - 1;
            }
        }
        // l即为第一个索引与值不匹配的位置
        return l;
    }

    /**
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
     *
     * 示例 1：
     *
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[2,7] 或者 [7,2]
     *
     * 示例 2：
     *
     * 输入：nums = [10,26,30,31,47,60], target = 40
     * 输出：[10,30] 或者 [30,10]
     */
    public int[] twoSum(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        // 滑动窗口
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum > target) {
                j--;
            } else if (sum < target) {
                i++;
            } else {
                return new int[]{nums[i], nums[j]};
            }
        }
        return null;
    }

    /**
     * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
     * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
     *
     * 示例 1：
     *
     * 输入：target = 9
     * 输出：[[2,3,4],[4,5]]
     *
     * 示例 2：
     *
     * 输入：target = 15
     * 输出：[[1,2,3,4,5],[4,5,6],[7,8]]
     *
     * 思路：通过滑动窗口从左移动到右
     */
    public int[][] findContinuousSequence(int target) {
        List<int[]> res = new ArrayList<>();
        // 初始窗口为1、2
        int i = 1, j = 2, sum = 3;
        // 因为至少要有两个元素，所以i不能等于j
        while (i < j) {
            if (sum < target) {
                // 窗口右移，sum同步累加
                j++;
                sum += j;
            } else if (sum > target) {
                // 窗口左移，sum同步减i
                sum -= i;
                i++;
            } else {
                // 输出i到j的区间
                int[] array = new int[j - i + 1];
                for (int tmp = i; tmp <= j; tmp++) {
                    array[tmp - i] = tmp;
                }
                res.add(array);
                // 窗口继续左移
                sum -= i;
                i++;
            }
        }
        return res.toArray(new int[][]{});
    }

    /**
     * 给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
     *
     * 示例:
     *
     * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
     * 输出: [3,3,5,5,6,7]
     * 解释:
     *
     *   滑动窗口的位置                最大值
     * --------------------------    -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> win = new LinkedList<>();
        // 滑动窗口
        for (int r = 0, l = 1 - k; r < nums.length; l++, r++) {
            // 删除左窗口值，如果左窗口值为队列中最大的值，则删除
            if (l > 0 && win.peekFirst() == nums[l - 1]) {
                win.removeFirst();
            }
            // 保持队列中的值是递减的
            while (!win.isEmpty() && win.peekLast() < nums[r]) {
                win.removeLast();
            }
            // 添加右窗口值
            win.addLast(nums[r]);
            // 移动窗口前记录窗口内的最大值
            if (l >= 0) {
                res[l] = win.peekFirst();
            }
        }
        return res;
    }

    /**
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
     *
     * 示例 1:
     *
     * 输入: [7,5,6,4]
     * 输出: 5
     *
     * 思路：归并排序
     * 其合并阶段 本质上是 合并两个排序数组 的过程，而每当遇到 左子数组当前元素 > 右子数组当前元素 时，
     * 意味着 「左子数组当前元素 至 末尾元素」 与 「右子数组当前元素」 构成了若干 「逆序对」 。
     */
    public int reversePairs(int[] nums) {
        this.nums = nums;
        this.tmp = new int[nums.length];
        return mergeSort(0, nums.length - 1);
    }

    private int[] nums, tmp;

    private int mergeSort(int left, int right) {
        // 终止条件
        if (left >= right) {
            return 0;
        }
        // 递归划分
        int middle = (left + right) / 2;
        int res = mergeSort(left, middle) + mergeSort(middle + 1, right);

        // 合并阶段，把[left, middle]和[middle+1, right]进行合并
        int i = left, j = middle + 1;
        // 辅助数组
        for (int k = left; k <= right; k++) {
            tmp[k] = nums[k];
        }
        // 合并
        for (int k = left; k <= right; k++) {
            if (i == middle + 1)
                nums[k] = tmp[j++];
            else if (j == right + 1 || tmp[i] <= tmp[j])
                nums[k] = tmp[i++];
            else {
                nums[k] = tmp[j++];
                res += middle - i + 1; // 统计逆序对
            }
        }
        return res;
    }

    /**
     * 从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。
     * 2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
     *
     * 示例 1:
     *
     * 输入: [1,2,3,4,5]
     * 输出: True
     *
     * 示例 2:
     *
     * 输入: [0,0,1,2,5]
     * 输出: True
     */
    public boolean isStraight(int[] nums) {
        int max = 0, min = 14;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            // 跳过大小王
            if (num == 0) {
                continue;
            }
            // 若有重复，提前返回false
            if (set.contains(num)) {
                return false;
            }
            set.add(num);
            // 最大牌
            max = Math.max(max, num);
            // 最小牌
            min = Math.min(min, num);
        }
        // 最大牌 - 最小牌 < 5 则可构成顺子
        return max - min < 5;
    }

    /**
     * 求 1+2+...+n ，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     *
     * 示例 1：
     *
     * 输入: n = 3
     * 输出: 6
     *
     * 示例 2：
     *
     * 输入: n = 9
     * 输出: 45
     */
    public int sumNums(int n) {
        boolean x = n > 1 && (n += sumNums(n - 1)) > 0;
        return n;
    }

    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
     *
     * 示例:
     *
     * 输入: a = 1, b = 1
     * 输出: 2
     */
    public int add(int a, int b) {
        if (b == 0) {
            return a;
        }
        // 转换成非进位和 + 进位
        return add(a ^ b, (a & b) << 1);
    }
}
