package org.xl.algorithm.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author xulei
 */
public class 二叉树 {

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。
     *
     * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     *
     * 思路：
     * 前序遍历性质： 节点按照 [ 根节点 | 左子树 | 右子树 ] 排序。
     * 中序遍历性质： 节点按照 [ 左子树 | 根节点 | 右子树 ] 排序。
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        // 用来快速定位根节点在中序遍历中的索引
        for (int i = 0; i < inorder.length; i++) {
            inOrderMap.put(inorder[i], i);
        }
        return buildTreeLeft(preorder, 0, preorder.length - 1, 0);
    }

    private static final Map<Integer, Integer> inOrderMap = new HashMap<>();

    private TreeNode buildTreeLeft(int[] preorder, int preorder_left, int preorder_right, int inorder_left) {
        if (preorder_left > preorder_right) {
            return null;
        }
        // 前序遍历第一个元素为根节点
        int rootVal = preorder[preorder_left];
        // 在中序遍历中定位根节点
        int inOrderRootIndex = inOrderMap.get(rootVal);
        // 建立根节点
        TreeNode rootNode = new TreeNode(rootVal);
        // 得到左子树中的节点数量
        int leftSize = inOrderRootIndex - inorder_left;
        // 递归解析左右子树
        rootNode.left = buildTreeLeft(preorder, preorder_left + 1, preorder_left + leftSize, inorder_left);
        rootNode.right = buildTreeLeft(preorder, preorder_left + leftSize + 1, preorder_right, inOrderRootIndex + 1);
        return rootNode;
    }

    /**
     * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
     *
     * B是A的子结构， 即A中有出现和B相同的结构和节点值。
     *
     * 例如:
     * 给定的树 A:
     *
     *      3
     *     / \
     *    4   5
     *   / \
     *  1   2
     * 给定的树 B：
     *
     *    4 
     *   /
     *  1
     * 返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
     *
     * 示例 1：
     *
     * 输入：A = [1,2,3], B = [3,1]
     * 输出：false
     *
     * 示例 2：
     *
     * 输入：A = [3,4,5,1,2], B = [4,1]
     * 输出：true
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        return (A != null && B != null) &&
                (recur(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B));
    }

    private boolean recur(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null || A.val != B.val) {
            return false;
        }
        // 递归判断左右节点是否相等
        return recur(A.left, B.left) && recur(A.right, B.right);
    }

    /**
     * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
     *
     * 例如输入：
     *
     *      4
     *    /   \
     *   2     7
     *  / \   / \
     * 1   3 6   9
     *
     * 镜像输出：
     *
     *      4
     *    /   \
     *   7     2
     *  / \   / \
     * 9   6 3   1
     *
     * 示例 1：
     *
     * 输入：root = [4,2,7,1,3,6,9]
     * 输出：[4,7,2,9,6,3,1]
     */
    public TreeNode mirrorTree(TreeNode root) {
        mirrorTreeInternal(root);
        return root;
    }

    private void mirrorTreeInternal(TreeNode root) {
        if (root == null) {
            return;
        }
        // 先交换
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        // 再递归
        mirrorTreeInternal(root.left);
        mirrorTreeInternal(root.right);
    }

    /**
     * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
     *
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     *
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     *
     * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
     *
     *     1
     *    / \
     *   2   2
     *    \   \
     *    3    3
     *
     * 示例 1：
     *
     * 输入：root = [1,2,2,3,4,4,3]
     * 输出：true
     *
     * 示例 2：
     *
     * 输入：root = [1,2,2,null,3,null,3]
     * 输出：false
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetricInternal(root.left, root.right);
    }

    private boolean isSymmetricInternal(TreeNode left, TreeNode right) {
        // 要么都为空
        if (left == null && right == null) {
            return true;
        }
        // 其中一个为空
        if (left == null || right == null) {
            return false;
        }
        // 值不相等
        if (left.val != right.val) {
            return false;
        }
        return isSymmetricInternal(left.left, right.right) && isSymmetricInternal(left.right, right.left);
    }

    /**
     * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。
     *
     * 例如: 给定二叉树: [3,9,20,null,null,15,7]
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * 返回：
     *
     * [3,9,20,15,7]
     */
    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        // 利用队列的先入先出特性来做BFS
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            // 取队列头节点
            TreeNode node = queue.poll();
            ans.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        // 转int数组
        int[] res = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            res[i] = ans.get(i);
        }
        return res;
    }

    /**
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
     *
     * 例如:给定二叉树: [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回其层次遍历结果：
     *
     * [
     *   [3],
     *   [9,20],
     *   [15,7]
     * ]
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<List<Integer>> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            List<Integer> tmp = new ArrayList<>();
            // 遍历每一层
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();
                tmp.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(tmp);
        }
        return result;
    }

    /**
     * 请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
     *
     * 例如:给定二叉树: [3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     *
     * 返回其层次遍历结果：
     *
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     */
    public List<List<Integer>> levelOrder3(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();
        queue.add(root);
        boolean seq = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> tmp = new LinkedList<>();
            for (int i = queue.size(); i > 0; i--) {
                TreeNode node = queue.poll();
                // 判断是正序输出还是逆序输出
                if (seq) {
                    tmp.addLast(node.val);
                } else {
                    tmp.addFirst(node.val);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            seq = !seq;
            result.add(tmp);
        }
        return result;
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
     *
     * 参考以下这颗二叉搜索树：
     *
     *      5
     *     / \
     *    2   6
     *   / \
     *  1   3
     *
     * 示例 1：
     *
     * 输入: [1,6,3,2,5]
     * 输出: false
     *
     * 示例 2：
     *
     * 输入: [1,3,2,6,5]
     * 输出: true
     *
     * 后序遍历：左->右->父
     */
    public boolean verifyPostOrder(int[] postOrder) {
        return verifyPostOrderInternal(postOrder, 0, postOrder.length - 1);
    }

    private boolean verifyPostOrderInternal(int[] postOrder, int i, int j) {
        // 如果子树节点数量小于1则直接返回
        if (i >= j) {
            return true;
        }
        int m = i;
        // 左子树下的所有值肯定是要小于父亲的,通过判断找到左子树的尾
        while (postOrder[m] < postOrder[j]) {
            m++;
        }
        // 右子树下的所有值肯定是要大于父亲的,通过判断找到右子树的尾
        int n = m;
        while (postOrder[n] > postOrder[j]) {
            n++;
        }
        // n==j的目的是保证右子树之后就是根节点，递归左右子树判断是否仍符合二叉搜索树要求
        return n == j && verifyPostOrderInternal(postOrder, i, m - 1) &&
                verifyPostOrderInternal(postOrder, m, j - 1);
    }

    /**
     * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
     * 叶子节点 是指没有子节点的节点。
     *
     * 示例 1：
     *
     * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
     * 输出：[[5,4,11,2],[5,8,4,5]]
     *
     * 示例 2：
     *
     * 输入：root = [1,2,3], targetSum = 5
     * 输出：[]
     *
     * 示例 3：
     *
     * 输入：root = [1,2], targetSum = 0
     * 输出：[]
     */
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        recur(root, target);
        return pathSumRes;
    }

    private final List<List<Integer>> pathSumRes = new ArrayList<>();
    private final LinkedList<Integer> path = new LinkedList<>();

    private void recur(TreeNode root, int target) {
        if (root == null) {
            return;
        }
        // 每经过一个节点就减去该节点值
        target = target - root.val;
        path.add(root.val);
        // 路径和等于target并为叶子节点
        if (target == 0 && root.left == null && root.right == null) {
            pathSumRes.add(new ArrayList<>(path));
        }
        recur(root.left, target);
        recur(root.right, target);
        // 回溯
        path.removeLast();
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
     * 当转化完成以后，树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继。还需要返回链表中的第一个节点的指针。
     *
     * 思路：在中序遍历（保证顺序性）过程中构建前驱后继节点
     */
    public TreeNode treeToDoublyList(TreeNode root) {
        if (root == null) {
            return null;
        }
        dfs(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    private TreeNode pre, head;

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.left);
        if (pre != null) {
            pre.right = root;
        } else {
            head = root;
        }
        root.left = pre;
        pre = root;
        dfs(root.right);
    }

    /**
     * 给定一棵二叉搜索树，请找出其中第 k 大的节点的值。
     *
     * 示例 1:
     *
     * 输入: root = [3,1,4,null,2], k = 1
     *    3
     *   / \
     *  1   4
     *   \
     *    2
     * 输出: 4
     *
     * 示例 2:
     *
     * 输入: root = [5,3,6,2,4,null,null,1], k = 3
     *        5
     *       / \
     *      3   6
     *     / \
     *    2   4
     *   /
     *  1
     * 输出: 4
     */
    public int kthLargest(TreeNode root, int k) {
        this.kth = k;
        // 通过反转中序遍历的顺序得到逆序的顺序
        backInOrder(root);
        return kRes;
    }

    private int kth, kRes;

    private void backInOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        backInOrder(root.right);
        // 获取第K个元素
        if (--kth == 0) {
            kRes = root.val;
            return;
        }
        backInOrder(root.left);
    }

    /**
     * 输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。
     *
     * 例如：
     *
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度 3 。
     */
    public int maxDepth(TreeNode root) {
        // 通过dfs进行深度遍历
        dfs(root, new LinkedList<>());
        return maxCount;
    }

    private int maxCount;

    private void dfs(TreeNode root, LinkedList<Integer> path) {
        if (root == null) {
            maxCount = Math.max(maxCount, path.size());
            return;
        }
        path.add(root.val);
        dfs(root.left, path);
        dfs(root.right, path);
        // 回溯
        path.removeLast();
    }

    /**
     * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。
     *
     * 示例 1:
     *
     * 给定二叉树 [3,9,20,null,null,15,7]
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回 true 。
     *
     * 示例 2:
     *
     * 给定二叉树 [1,2,2,3,3,null,null,4,4]
     *
     *        1
     *       / \
     *      2   2
     *     / \
     *    3   3
     *   / \
     *  4   4
     * 返回 false 。
     */
    public boolean isBalanced(TreeNode root) {
        isBalancedInternal(root);
        return isBalance;
    }

    private boolean isBalance = true;

    private int isBalancedInternal(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDeep = isBalancedInternal(root.left);
        int rightDeep = isBalancedInternal(root.right);
        // 判断左右子树高度是否相差1
        if (Math.abs(leftDeep - rightDeep) > 1) {
            isBalance = false;
        }
        // 向上返回当前节点的高度
        return 1 + Math.max(leftDeep, rightDeep);
    }

    /**
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     *
     * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
     *
     * 示例 1:
     *
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * 输出: 6
     * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
     *
     * 示例 2:
     *
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * 输出: 2
     * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            // p、q都在root的左子树中
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            }
            // p、q都在root的右子树中
            else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
    }

    /**
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     *
     * 例如，给定如下二叉树:  root = [6,2,8,0,4,7,9,null,null,3,5]
     *hgh
     * 示例 1:
     *
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * 输出: 6
     * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
     *
     * 示例 2:
     *
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
     * 输出: 2
     * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
     *
     * 考虑通过递归对二叉树进行先序遍历，当遇到节点 pp 或 qq 时返回。从底至顶回溯，当节点 p, qp,q 在节点 root 的异侧时，
     * 节点 root 即为最近公共祖先，则向上返回 root 。
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        TreeNode left = lowestCommonAncestor2(root.left, p, q);
        TreeNode right = lowestCommonAncestor2(root.right, p, q);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return root;
    }

    private static class TreeNode {

        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
