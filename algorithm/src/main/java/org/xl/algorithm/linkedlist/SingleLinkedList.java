package org.xl.algorithm.linkedlist;

/**
 * 单链表简易实现
 *
 * @author xulei
 */
public class SingleLinkedList<T> {

    /** 头结点 */
    private SNode<T> headNode;
    /** 链表长度 */
    private int length;

    /**
     * 添加链表元素(头插法)
     */
    public void addHead(T data) {
        if (data == null) {
            return;
        }
        SNode<T> node = new SNode<>(data, null);
        length++;
        if (headNode == null) {
            headNode = node;
            return;
        }
        node.next = headNode;
        headNode = node;
    }

    /**
     * 添加链表元素(尾插法)
     */
    public void addTail(T data) {
        if (data == null) {
            return;
        }
        SNode<T> node = new SNode<>(data, null);
        length++;
        if (headNode == null) {
            headNode = node;
        } else {
            // 获取尾节点
            SNode<T> tail = headNode;
            while (tail.next != null) {
                tail = tail.next;
            }
            tail.next = node;
        }
    }

    /**
     * 删除链表元素
     */
    public void delete(T data) {
        if (headNode == null || data == null) {
            return;
        }
        if (headNode.element.equals(data)) {
            headNode = headNode.next;
            length--;
            return;
        }
        // 获取待删除节点的前一个节点
        SNode<T> prev = headNode;
        while (prev.next != null && !prev.next.element.equals(data)) {
            prev = prev.next;
        }
        if (prev.next == null) {
            return;
        }
        // 删除节点
        prev.next = prev.next.next;
        length--;
    }

    public static class SNode<T> {

        private final T element;

        private SNode<T> next;

        public SNode(T element, SNode<T> next) {
            this.element = element;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        SingleLinkedList<String> linkedList = new SingleLinkedList<>();
        linkedList.addHead("1");
        linkedList.addHead("2");
        linkedList.addHead("3");
        linkedList.addHead("4");
        linkedList.addTail("5");
        linkedList.addTail("6");

        linkedList.delete("1");
        System.out.println(linkedList.length);
    }
}
