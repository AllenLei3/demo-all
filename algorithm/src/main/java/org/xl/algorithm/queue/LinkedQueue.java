package org.xl.algorithm.queue;

/**
 * 链式队列
 *
 * @author xulei
 */
public class LinkedQueue {

    private Node head;

    private Node tail;

    /**
     * 入队（尾插）
     */
    public void enqueue(String value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            tail = node;
        }
        tail.next = node;
        tail = node;
    }

    /**
     * 出队（取头）
     */
    public String dequeue() {
        if (head == null) {
            return null;
        }
        String value = head.value;
        // 只有一个节点时
        if (head == tail) {
            head = null;
            tail = null;
            return value;
        }
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
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
