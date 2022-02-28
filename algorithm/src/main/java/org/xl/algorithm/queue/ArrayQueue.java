package org.xl.algorithm.queue;

/**
 * 顺序队列
 *
 * @author xulei
 */
public class ArrayQueue {

    private final String[] item;

    /**
     * 队列头元素数组下标
     */
    private int head;

    /**
     * 队列尾元素数组下标
     */
    private int tail;

    public ArrayQueue(int capacity) {
        item = new String[capacity];
    }

    /**
     * 入队, 新元素放在队尾
     */
    public boolean enqueue(String value) {
        // 队满
        if (tail == item.length) {
            if (head > 0) {
                // 有空余空间
                for (int i = 0; i < head; i++) {
                    item[i] = item[i + head];
                }
                // 搬移完之后重新更新head和tail
                tail = tail - head;
                head = 0;
            } else {
                // 表示整个队列都占满了
                return false;
            }
        }
        item[tail] = value;
        tail++;
        return true;
    }

    /**
     * 出队, 从队头取元素
     */
    public String dequeue() {
        // 如果队头和队尾重合，则没有元素
        if (head == tail) {
            return null;
        }
        String value = item[head];
        head++;
        return value;
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(10);
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
