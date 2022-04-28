package org.xl.algorithm.dynamic;

/**
 * 矩阵最短路径问题(状态转移表法实现)
 *
 * 在4*4的矩阵中，起点是matrix[0][0]，终点是matrix[3][3]
 * 求从起点到终点所经过的路径的最短长度
 *
 * @author xulei
 * @date 2020/8/18 2:53 下午
 */
public class MatrixMinDist {

    /** 4*4的矩阵 */
    private static final int[][] matrix = new int[4][4];
    /** 状态数组，对应矩阵，数组值为从起始点到该节点的最短路径 */
    private static final int[][] states = new int[4][4];

    static {
        matrix[0] = new int[]{1, 3, 5, 9};
        matrix[1] = new int[]{2, 1, 3, 4};
        matrix[2] = new int[]{5, 2, 6, 7};
        matrix[3] = new int[]{6, 8, 4, 3};
    }

    /**
     * 求矩阵的最短路径(状态转移表法实现)
     */
    public static int minDist() {
        // 初始化第一行
        int sum = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            sum = sum + matrix[0][i];
            states[0][i] = sum;
        }
        // 初始化第一列
        sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum = sum + matrix[i][0];
            states[i][0] = sum;
        }
        // 循环填充状态
        for (int i = 1; i < matrix[0].length; i++) {
            for (int j = 1; j < matrix.length; j++) {
                // 取上一步路径短的节点来累加
                states[i][j] = matrix[i][j] + Math.min(states[i - 1][j], states[i][j - 1]);
            }
        }
        return states[states.length - 1][states[0].length - 1];
    }

    public static void main(String[] args) {
        System.out.println(MatrixMinDist.minDist());
    }
}
