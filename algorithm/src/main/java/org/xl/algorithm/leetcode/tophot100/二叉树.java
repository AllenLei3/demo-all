package org.xl.algorithm.leetcode.tophot100;

import org.xl.algorithm.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulei
 */
public class 二叉树 {

    /**
     * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？返回满足题意的二叉搜索树的种数。
     *
     * 示例 1：
     *
     * 输入：n = 3
     * 输出：5
     *
     * 示例 2：
     *
     * 输入：n = 1
     * 输出：1
     */
    public int numTrees(int n) {
        if (n  == 0 || n == 1) {
            return 1;
        }
        // 避免重复计算
        if (map.containsKey(n)) {
            return map.get(n);
        }
        int count = 0;
        for (int i = 1; i <= n; i++) {
            // 如果以i为根节点，递归计算其左、右部分可构成的二叉搜索树的数量
            int leftNum = numTrees(i - 1);
            map.put(i - 1, leftNum);
            int rightNum = numTrees(n - i);
            map.put(n - i, rightNum);
            // 乘积即为以i为根节点可以构成的二叉搜索树的数量
            count += leftNum * rightNum;
        }
        return count;
    }

    private final Map<Integer /* value */, Integer /* num */> map = new HashMap<>();

    /**
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     * 有效二叉搜索树定义如下：
     *
     * 节点的左子树只包含 小于 当前节点的数。
     * 节点的右子树只包含 大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     *
     * 示例 1：
     *
     * 输入：root = [2,1,3]
     * 输出：true
     *
     * 示例 2：
     *
     * 输入：root = [5,1,4,null,null,3,6]
     * 输出：false
     * 解释：根节点的值是 5 ，但是右子节点的值是 4 。
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBSTRecur(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTRecur(TreeNode root, long min, long max) {
        if (root == null) {
            return true;
        }
        if (root.val <= min || root.val >= max) {
            return false;
        }
        return isValidBSTRecur(root.left, min, root.val) && isValidBSTRecur(root.right, root.val, max);
    }

    /**
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     *
     * 示例 1：
     *
     * 输入：root = [1,2,5,3,4,null,6]
     * 输出：[1,null,2,null,3,null,4,null,5,null,6]
     *
     * 示例 2：
     *
     * 输入：root = []
     * 输出：[]
     *
     * 示例 3：
     *
     * 输入：root = [0]
     * 输出：[0]
     */
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode next = curr.left;
                TreeNode predecessor = next;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                predecessor.right = curr.right;
                curr.left = null;
                curr.right = next;
            }
            curr = curr.right;
        }
    }

    /**
     * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
     * 该路径 至少包含一个 节点，且不一定经过根节点。路径和 是路径中各节点值的总和。给你一个二叉树的根节点 root ，返回其 最大路径和 。
     *
     * 示例 1：
     *
     * 输入：root = [1,2,3]
     * 输出：6
     * 解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
     *
     * 示例 2：
     *
     * 输入：root = [-10,9,20,null,null,15,7]
     * 输出：42
     * 解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
     */
    public int maxPathSum(TreeNode root) {
        maxPathSumRecur(root);
        return maxSum;
    }

    private int maxSum = Integer.MIN_VALUE;

    private int maxPathSumRecur(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftMaxPathSum = Math.max(maxPathSumRecur(root.left), 0);
        int rightMaxPathSum = Math.max(maxPathSumRecur(root.right), 0);
        maxSum = Math.max(maxSum, root.val + leftMaxPathSum + rightMaxPathSum);
        return root.val + Math.max(leftMaxPathSum, rightMaxPathSum);
    }
}
