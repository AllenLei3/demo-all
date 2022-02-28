package org.xl.algorithm.queue;

/**
 * 用数组实现的循环队列
 *
 * @author xulei
 */
public class ArrayCycleQueue {

    private final String[] item;

    /**
     * 队列头元素数组下标
     */
    private int head;

    /**
     * 队列尾元素数组下标
     */
    private int tail;

    public ArrayCycleQueue(int capacity) {
        item = new String[capacity];
    }

    public boolean enqueue(String value) {
        // 队满
        if ((tail + 1) % item.length == head) {
            return false;
        }
        item[tail] = value;
        // 循环
        tail = (tail + 1) % item.length;
        return true;
    }

    public String dequeue() {
        // 队空
        if (head == tail) {
            return null;
        }
        String value = item[head];
        // 循环
        head = (head + 1) % item.length;
        return value;
    }

    public static void main(String[] args) {
        ArrayCycleQueue queue = new ArrayCycleQueue(5);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("e");
        queue.enqueue("f");

        for (int i = 0; i < 4; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
