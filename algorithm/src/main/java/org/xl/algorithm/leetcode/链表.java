package org.xl.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author xulei
 */
public class 链表 {

    /**
     * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
     *
     * 示例 1：
     *
     * 输入：head = [1,3,2]
     * 输出：[2,3,1]
     */
    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        // 借用辅助栈进行链表入栈
        Stack<Integer> stack = new Stack<>();
        ListNode node = head;
        while (node != null) {
            stack.push(node.val);
            node = node.next;
        }
        // 遍历出栈
        int[] array = new int[stack.size()];
        int index = 0;
        while (!stack.empty()) {
            array[index] = stack.pop();
            index++;
        }
        return array;
    }

    /**
     * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。返回删除后的链表的头节点。
     * 题目保证链表中节点的值互不相同
     *
     * 示例 1:
     *
     * 输入: head = [4,5,1,9], val = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     *
     * 示例 2:
     *
     * 输入: head = [4,5,1,9], val = 1
     * 输出: [4,5,9]
     * 解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
     */
    public ListNode deleteNode(ListNode head, int val) {
        // 先判断头节点
        if (head.val == val) {
            return head.next;
        }
        ListNode node = head;
        // 遍历判断next节点是否是待删除的节点
        while (node.next != null) {
            if (node.next.val == val) {
                node.next = node.next.next;
                return head;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
     *
     * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
     *
     * 示例：
     *
     * 给定一个链表: 1->2->3->4->5, 和 k = 2.
     *
     * 返回链表 4->5.
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        // 从head开始跳过k个节点,定位到第K个节点
        ListNode kthNode = head;
        for (int i = 0; i < k; i++) {
            kthNode = kthNode.next;
        }
        // headNode、kthNode同步向后遍历，当kthNode遍历到尾部时，headNode即为倒数k个节点的链表头节点
        while (kthNode != null) {
            kthNode = kthNode.next;
            head = head.next;
        }
        return head;
    }

    /**
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     *
     * 示例:
     *
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            // 把next节点先暂存起来
            ListNode tmp = cur.next;
            // 将next指针指向其前一个节点
            cur.next = pre;
            // 移动cur和pre
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    /**
     * 输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
     *
     * 示例1：
     *
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode headNode = head;
        // 当两个链表都有值时，根据值判断合并哪一个
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                headNode.next = new ListNode(l1.val);
                headNode = headNode.next;
                l1 = l1.next;
            } else if (l1.val > l2.val) {
                headNode.next = new ListNode(l2.val);
                headNode = headNode.next;
                l2 = l2.next;
            } else {
                headNode.next = new ListNode(l1.val);
                headNode.next.next = new ListNode(l2.val);
                headNode = headNode.next.next;
                l1 = l1.next;
                l2 = l2.next;
            }
        }
        // 如果其中一个链表已经合并完了,则直接合并另一个链表的剩余部分
        headNode.next = l1 == null ? l2 : l1;
        return head.next;
    }

    /**
     * 请实现 copyRandomList 函数，复制一个复杂链表。
     * 在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
     */
    public Node copyRandomList(Node head) {
        Map<Node, Node> map = new HashMap<>();
        // 遍历原链表，构建<原链表节点-新链表节点>之间的映射
        Node node = head;
        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }
        // 再次遍历原链表，用来构建指针关系
        node = head;
        while (node != null) {
            Node nextNode = node.next;
            Node randomNode = node.random;
            // 根据原链表节点找到对应的新节点并构建引用关系
            Node newNode = map.get(node);
            newNode.next = map.get(nextNode);
            newNode.random = map.get(randomNode);
            node = node.next;
        }
        return map.get(head);
    }

    private static class Node {
        int val;
        Node next;
        Node random;
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 输入两个链表，找出它们的第一个公共节点。
     *
     * 示例 1：
     *
     * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
     * 输出：Reference of the node with value = 8
     * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。
     * 从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
     *  
     *
     * 示例 2：
     *
     * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * 输出：null
     * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
     * 解释：这两个链表不相交，因此返回 null。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        // a走完后从b的头节点开始继续、b走完后从a的头节点开始继续
        // 当相交时即为第一个公共节点（相当于把a、b串成了一个环来寻找公共节点）
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    /**
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * 示例 1：
     *
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807.
     *
     * 示例 2：
     *
     * 输入：l1 = [0], l2 = [0]
     * 输出：[0]
     *
     * 示例 3：
     *
     * 输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * 输出：[8,9,9,9,0,0,0,1]
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int carry = 0;// 表示当前是否需要进位
        ListNode node1 = l1;// 当前l1节点
        ListNode node2 = l2;// 当前l2节点
        ListNode head = new ListNode(0);
        ListNode current = head;

        while (node1 != null || node2 != null) {
            int x = node1 != null ? node1.val : 0;
            int y = node2 != null ? node2.val : 0;
            int sum = x + y + carry;// 计算总和
            carry = sum / 10;// 判断是否进位
            current.next = new ListNode(sum % 10);// 添加当前元素
            current = current.next;
            if (node1 != null) {
                node1 = node1.next;
            }
            if (node2 != null) {
                node2 = node2.next;
            }
        }
        // 最后看是否需要向前补一位
        if (carry > 0) {
            current.next = new ListNode(carry);
        }
        return head.next;
    }

    /**
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 示例 1：
     *
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     *
     * 示例 2：
     *
     * 输入：head = [1], n = 1
     * 输出：[]
     *
     * 示例 3：
     *
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 先遍历链表获取总长度
        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }
        if (length == 0) {
            return head;
        }
        // 相当于删除整个链表
        if (length < n) {
            return null;
        }
        if (length == n) {
            return head.next;
        }
        int index = length - n;
        node = head;
        while (node.next != null) {
            if (index == 1) {
                node.next = node.next.next;
                break;
            }
            index--;
            node = node.next;
        }
        return head;
    }

    public static class ListNode {

        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
