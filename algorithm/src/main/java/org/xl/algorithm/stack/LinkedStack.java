package org.xl.algorithm.stack;

/**
 * 链式栈（链表实现）
 *
 * @author xulei
 */
public class LinkedStack {

    /**
     * 链表头节点指针
     */
    private Node head;

    /**
     * 入栈(头插法)
     */
    public void push(String value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            return;
        }
        node.next = head;
        head = node;
    }

    /**
     * 出栈, 链表头节点是最新插入的元素
     */
    public String pop() {
        if (head == null) {
            return null;
        }
        String value = head.value;
        head = head.next;
        return value;
    }

    private static class Node {
        private final String value;
        private Node next;

        public Node(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LinkedStack stack = new LinkedStack();
        System.out.println(stack.pop());

        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
