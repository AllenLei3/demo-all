package org.xl.algorithm.tree;

/**
 * 二叉查找树
 *
 * @author xulei
 */
public class BinarySearchTree {

    private TreeNode root;

    /**
     * 插入
     */
    public void insertNode(int data) {
        if (root == null) {
            root = new TreeNode(data);
            return;
        }
        insertNodeInternal(root, data);
    }

    private void insertNodeInternal(TreeNode node, int data) {
        if (node.value < data) {
            if (node.right == null) {
                node.right = new TreeNode(data);
            } else {
                insertNodeInternal(node.right, data);
            }
        } else {
            if (node.left == null) {
                node.left = new TreeNode(data);
            } else {
                insertNodeInternal(node.left, data);
            }
        }
    }

    /**
     * 查找
     */
    public TreeNode findNode(int data) {
        return findNodeInternal(root, data);
    }

    private TreeNode findNodeInternal(TreeNode node, int data) {
        if (node == null) {
            return null;
        }
        if (node.value == data) {
            return node;
        }
        if (node.value < data) {
            return findNodeInternal(node.right, data);
        } else {
            return findNodeInternal(node.left, data);
        }
    }

    /**
     * 删除
     */
    public TreeNode deleteNode(int data) {
        TreeNode root = this.root;
        // 先获取要删除节点的父节点
        TreeNode parent = null;
        while (root != null && root.value != data) {
            parent = root;
            if (root.value > data) {
                root = root.left;
            } else {
                root = root.right;
            }
        }
        if (root == null) {
            return null;
        }
        // 如果要删除的节点有两个子节点
        if (root.left != null && root.right != null) {
            // 获取要删除节点的右子树中的最小值
            TreeNode min = root.right;
            TreeNode minParent = root;
            while (min.left != null) {
                minParent = min;
                min = min.left;
            }
            // 交换元素值
            root.value = min.value;
            // 这里改变引用是为了通过下面的代码来删除min节点
            root = min;
            parent = minParent;
        }

        // 要删除的节点是叶子节点或只有一个子节点
        TreeNode child = null;
        if (root.left != null) {
            child = root.left;
        } else if (root.right != null) {
            child = root.right;
        }

        // 要删除的是根节点
        if (parent == null) {
            return root;
        }
        // 将子节点代替要删除的节点,child为空或是要删除节点的子节点
        else if (parent.left == root) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        return parent;
    }


    private static class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insertNode(55);
        tree.insertNode(67);
        tree.insertNode(84);
        tree.insertNode(15);
        tree.insertNode(39);
        tree.insertNode(42);
        tree.insertNode(123);
        tree.insertNode(44);
        tree.insertNode(65);
        tree.insertNode(4);

        TreeNode node = tree.findNode(55);

        tree.deleteNode(15);

        System.out.println("---");

    }
}
