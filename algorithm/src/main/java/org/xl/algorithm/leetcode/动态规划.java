package org.xl.algorithm.leetcode;

import java.util.Arrays;

/**
 * @author xulei
 */
public class 动态规划 {

    /**
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     *
     * 示例 1：
     *
     * 输入：n = 2
     * 输出：1
     *
     * 示例 2：
     *
     * 输入：n = 5
     * 输出：5
     *
     * 虽然递归可以解, 但会重复计算, 动态规划是最优方案
     */
    public int fib(int n) {
        int a = 0, b = 1, sum;
        // 循环计算n的值，避免重复计算
        for (int i = 0; i < n; i++) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     *
     * 示例 1：
     *
     * 输入：n = 2
     * 输出：2
     *
     * 示例 2：
     *
     * 输入：n = 7
     * 输出：21
     *
     * 示例 3：
     *
     * 输入：n = 0
     * 输出：1
     *
     * 递归公式：f(n) = f(n - 1) + f(n - 2)
     */
    public int numWays(int n) {
        int a = 1, b = 1, sum;
        // 循环计算n的值，避免重复计算
        for (int i = 0; i < n; i++) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;
    }

    /**
     * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为O(n)。
     *
     * 示例1:
     *
     * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 思路：
     * 设动态规划列表 dp ，dp[i] 代表以元素 nums[i] 为结尾的连续子数组最大和。
     * 若 dp[i-1]≤0 ，说明 dp[i - 1] 对 dp[i] 产生负贡献，即 dp[i-1] + nums[i] 还不如 nums[i] 本身大。
     *
     * 当 dp[i-1]>0 时：执行 dp[i] = dp[i-1] + nums[i]；
     * 当 dp[i-1]≤0 时：执行 dp[i] = nums[i]；
     */
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 如果比0小，则累加会比原值更低
            nums[i] += Math.max(nums[i - 1], 0);
            // 获取和最大的值
            res = Math.max(res, nums[i]);
        }
        return res;
    }

    /**
     * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。
     * 一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
     *
     * 示例 1:
     *
     * 输入: 12258
     * 输出: 5
     * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
     */
    public int translateNum(int num) {
        String s = String.valueOf(num);
        int a = 1, b = 1;
        for (int i = 2; i <= s.length(); i++) {
            String tmp = s.substring(i - 2, i);
            int c = tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0 ? a + b : a;
            b = a;
            a = c;
        }
        return a;
    }

    /**
     * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。
     * 你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。
     * 给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
     *
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 12
     * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
     */
    public int maxValue(int[][] grid) {
        int s = grid.length, h = grid[0].length;
        // 初始化dp的第一列
        for (int i = 1; i < s; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        // 初始化dp的第一行
        for (int i = 1; i < h; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        // 填充棋盘剩余内容
        for (int i = 1; i < s; i++) {
            for (int j = 1; j < h; j++) {
                grid[i][j] += Math.max(grid[i - 1][j], grid[i][j - 1]);
            }
        }
        // 返回右下角元素，即为最优解
        return grid[s - 1][h - 1];
    }

    /**
     * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
     * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
     *
     * 示例 1:
     *
     * 输入: 1
     * 输出: [0.16667,0.16667,0.16667,0.16667,0.16667,0.16667]
     *
     * 示例 2:
     *
     * 输入: 2
     * 输出: [0.02778,0.05556,0.08333,0.11111,0.13889,0.16667,0.13889,0.11111,0.08333,0.05556,0.02778]
     */
    public double[] dicesProbability(int n) {
        double[] dp = new double[6];
        // 初始化概率数组
        Arrays.fill(dp, 1.0 / 6.0);
        for (int i = 2; i <= n; i++) {
            double[] tmp = new double[5 * i + 1];
            // 概率累加
            for (int j = 0; j < dp.length; j++) {
                for (int k = 0; k < 6; k++) {
                    tmp[j + k] += dp[j] / 6.0;
                }
            }
            dp = tmp;
        }
        return dp;
    }

    /**
     * 0,1,···,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
     * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
     *
     * 示例 1：
     *
     * 输入: n = 5, m = 3
     * 输出: 3
     *
     * 示例 2：
     *
     * 输入: n = 10, m = 17
     * 输出: 2
     *
     * 动态规划转移方程：dp[i]=(dp[i−1]+m)%i
     */
    public int lastRemaining(int n, int m) {
        int x = 0;
        for (int i = 2; i <= n; i++) {
            x = (x + m) % i;
        }
        return x;
    }

    /**
     * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少？
     *
     * 示例 1:
     *
     * 输入: [7,1,5,3,6,4]
     * 输出: 5
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
     *
     * 示例 2:
     *
     * 输入: [7,6,4,3,1]
     * 输出: 0
     * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
     *
     * 思路：我们需找出给定数组中两个数字之间的最大差值（即最大利润）。且第二个数字（卖出价格）必须大于第一个数字（买入价格）。
     */
    public static int maxProfit(int[] prices) {
        // cost为最小成本，profit为最大盈利
        int cost = Integer.MAX_VALUE, profit = 0;
        for (int price : prices) {
            if (price < cost) {
                // 找到最小成本
                cost = price;
            } else if (price - cost > profit) {
                // 找到最大盈利
                profit = price - cost;
            }
        }
        return profit;
    }

    /**
     * 给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B[i] 的值是数组 A 中除了下标 i 以外的元素的积,
     * 即 B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。
     *
     * 示例:
     *
     * 输入: [1,2,3,4,5]
     * 输出: [120,60,40,30,24]
     */
    public int[] constructArr(int[] a) {
        int len = a.length;
        if (len == 0) {
            return new int[0];
        }
        int[] b = new int[len];
        b[0] = 1;
        int tmp = 1;
        // 计算左下角乘积
        for (int i = 1; i < len; i++) {
            b[i] = b[i - 1] * a[i - 1];
        }
        // 计算右上角乘积
        for (int i = len - 2; i >= 0; i--) {
            tmp *= a[i + 1];
            b[i] *= tmp;
        }
        return b;
    }
}
