package org.xl.algorithm.list;

/**
 * 206 反转链表
 *
 * @author xulei
 */
public class ReverseLinkedList {

    public Node reverseList(Node head) {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node tmp = cur.next;
            // 将next指针指向前一个元素
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }

    private static class Node {
        private final int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Node list1 = new Node(1);
        Node list2 = new Node(2);
        Node list3 = new Node(3);
        list1.next = list2;
        list2.next = list3;

        ReverseLinkedList revers = new ReverseLinkedList();
        Node node = revers.reverseList(list1);
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
