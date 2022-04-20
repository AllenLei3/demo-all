package org.xl.algorithm.leetcode.tophot100;

import org.xl.algorithm.leetcode.ListNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xulei
 */
public class 链表 {

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
        // 删除倒数第k个节点
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

    /**
     * 给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表.
     *
     * 示例 1：
     *
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 将它们合并到一个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     *
     * 示例 2：
     *
     * 输入：lists = []
     * 输出：[]
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return merge(lists, 0, lists.length - 1);
    }

    private ListNode merge(ListNode[] lists, int i, int j) {
        if (i == j) {
            return lists[i];
        }
        if (i > j) {
            return null;
        }
        int mid = i + (j - i) / 2;
        // 合并两个有序链表
        return mergeTwoLists(merge(lists, i, mid), merge(lists, mid + 1, j));
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null || b == null) {
            return a != null ? a : b;
        }
        ListNode head = new ListNode(0);
        ListNode tail = head, aPtr = a, bPtr = b;
        while (aPtr != null && bPtr != null) {
            if (aPtr.val < bPtr.val) {
                tail.next = aPtr;
                aPtr = aPtr.next;
            } else {
                tail.next = bPtr;
                bPtr = bPtr.next;
            }
            tail = tail.next;
        }
        tail.next = (aPtr != null ? aPtr : bPtr);
        return head.next;
    }

    /**
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 如果链表中存在环，则返回 true。否则，返回 false。
     *
     * 示例 1：
     *
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * 示例 2：
     *
     * 输入：head = [1,2], pos = 0
     * 输出：true
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     *
     * 示例 3：
     *
     * 输入：head = [1], pos = -1
     * 输出：false
     * 解释：链表中没有环。
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 快慢指针，如果是环一定会重合
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。不允许修改链表。
     *
     * 示例 1：
     *
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     *
     * 示例 2：
     *
     * 输入：head = [1,2], pos = 0
     * 输出：返回索引为 0 的链表节点
     * 解释：链表中有一个环，其尾部连接到第一个节点。
     *
     * 示例 3：
     *
     * 输入：head = [1], pos = -1
     * 输出：返回 null
     * 解释：链表中没有环。
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while (true) {
            if (fast == null || fast.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        // 构建第二次相遇，相遇时的点即为环的入口
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    /**
     * 请你设计并实现一个满足 LRU (最近最少使用) 缓存 约束的数据结构。
     * 实现 LRUCache 类：
     * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
     * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
     * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；如果不存在，则向缓存中插入该组 key-value 。如果插入操作导致关键字数量超过 capacity ，则逐出最久未使用的关键字。
     * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
     *
     * 示例：
     *
     * 输入
     * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
     * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
     * 输出
     * [null, null, null, 1, null, -1, null, -1, 3, 4]
     *
     * 解释
     * LRUCache lRUCache = new LRUCache(2);
     * lRUCache.put(1, 1); // 缓存是 {1=1}
     * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
     * lRUCache.get(1);    // 返回 1
     * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
     * lRUCache.get(2);    // 返回 -1 (未找到)
     * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
     * lRUCache.get(1);    // 返回 -1 (未找到)
     * lRUCache.get(3);    // 返回 3
     * lRUCache.get(4);    // 返回 4
     */
    private static class LRUCache {

        private final DLinkedNode head;
        private final DLinkedNode tail;
        private final Map<Integer, DLinkedNode> cache = new HashMap<>();
        private final int capacity;
        private int size;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            // 使用伪头部和伪尾部节点
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            // 将键值对移动到链表头部
            removeNode(node);
            addToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            DLinkedNode node = cache.get(key);
            if (node != null) {
                // 如果节点存在，更新节点值
                node.value = value;
                // 将键值对移动到链表头部
                removeNode(node);
                addToHead(node);
            } else {
                // 新建链表节点并同步写入哈希表
                node = new DLinkedNode(key, value);
                addToHead(node);
                cache.put(key, node);
                size++;
                // 如果超过最大容量，则删除链尾节点，同步删除哈希表数据
                if (size > capacity) {
                    DLinkedNode tailNode = tail.prev;
                    cache.remove(tailNode.key);
                    removeNode(tailNode);
                    size--;
                }
            }
        }

        private static class DLinkedNode {
            private int key;
            private int value;
            private DLinkedNode next;
            private DLinkedNode prev;
            public DLinkedNode() {}
            public DLinkedNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private void removeNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToHead(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
    }

    /**
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     *
     * 示例 1：
     *
     * 输入：head = [4,2,1,3]
     * 输出：[1,2,3,4]
     *
     * 示例 2：
     *
     * 输入：head = [-1,5,3,4,0]
     * 输出：[-1,0,3,4,5]
     *
     * 示例 3：
     *
     * 输入：head = []
     * 输出：[]
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 通过快慢指针获取链表中心
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 分成两个链表
        ListNode tmp = slow.next;
        slow.next = null;
        // 进行归并排序
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        // 合并两个有序链表
        // TODO
        return null;
    }

    /**
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     *
     * 示例 1：
     *
     * 输入：head = [1,2,2,1]
     * 输出：true
     *
     * 示例 2：
     *
     * 输入：head = [1,2]
     * 输出：false
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> path = new ArrayList<>();
        while (head != null) {
            path.add(head.val);
            head = head.next;
        }
        if (path.isEmpty()) {
            return true;
        }
        // 双指针判断头尾两个元素是否相等
        int i = 0, j = path.size() - 1;
        while (i <= j) {
            if (!path.get(i).equals(path.get(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
