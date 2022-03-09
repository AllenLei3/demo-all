package org.xl.algorithm.tree;

/**
 * 二叉树的三种遍历方式
 *
 * @author xulei
 */
public class BinaryTree {

    /**
     * 二叉树前序遍历
     */
    public void preOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * 二叉树中序遍历
     */
    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.println(root.value);
        inOrder(root.right);
    }

    /**
     * 二叉树后序遍历
     */
    public void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.value);
    }

    private static class TreeNode {
        private final int value;
        private TreeNode left;
        private TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.left = node2;
        node1.right = node3;
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        node2.left = node4;
        node2.right = node5;
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        node3.left = node6;
        node3.right = node7;

        //        1
        //      2   3
        //    4  5 6  7
        BinaryTree tree = new BinaryTree();
        tree.preOrder(node1);
        System.out.println("-------------");
        tree.inOrder(node1);
        System.out.println("-------------");
        tree.postOrder(node1);
    }
}
