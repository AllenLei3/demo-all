package org.xl.algorithm.list;

import org.xl.algorithm.hash.HashTable;

/**
 * 基于双向链表+哈希表实现的LRU缓存淘汰算法
 * 双向链表和哈希表各维护一份数据，哈希表用来快速获取节点，双向链表用来快速插入、删除和维护顺序关系
 *
 * @author xulei
 */
public class LRUBaseHashTable<K, V> {

    /** 默认链表容量 */
    private final static Integer DEFAULT_CAPACITY = 10;

    /** 链表长度 */
    private Integer length;
    /** 链表容量,即缓存容量 */
    private final Integer capacity;
    /** 头结点 */
    private final Node<K, V> head = new Node<>();
    /** 尾节点 */
    private final Node<K, V> tail = new Node<>();
    /** 散列表存储key对应的双向链表节点 */
    private final HashTable<K, Node<K, V>> table = new HashTable<>();

    public LRUBaseHashTable() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBaseHashTable(int capacity) {
        this.length = 0;
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 新增节点数据，新节点要放在头部
     *
     * 1. 如果已经在其中，需要将其移动到双向链表的头部
     * 2. 如果不在其中，还要看缓存有没有满
     *      2.1 如果满了，则将双向链表尾部的结点删除，然后再将数据放到链表的头部
     *      2.2 如果没有满，就直接将数据放到链表的头部。
     */
    public void add(K k, V v) {
        Node<K, V> node = table.get(k);
        // 如果已经在其中，需要将其移动到双向链表的头部
        if (node != null) {
            node.v = v;
            removeNode(node);
            addNodeToHead(node);
        } else {
            Node<K, V> newNode = new Node<>();
            newNode.k = k;
            newNode.v = v;
            addNodeToHead(newNode);
            table.put(k, newNode);
            // 如果满了，则将双向链表尾部的结点删除
            if (++length > capacity) {
                Node<K, V> n = tail.prev;
                removeNode(n);
                table.remove(n.k);
                length--;
            }
        }
    }

    /**
     * 获取节点数据，新访问的节点也要放在头部
     *
     * 通过散列表，我们可以很快地在缓存中找到一个数据。当找到数据后，我们还需要将它移动到双向链表的头部
     */
    public V get(K k) {
        Node<K, V> node = table.get(k);
        if (node == null) {
            return null;
        }
        removeNode(node);
        addNodeToHead(node);
        return node.v;
    }

    /**
     * 移除节点数据
     *
     * 找到数据所在的结点，然后将结点删除
     * 因为我们的链表是双向链表，可以通过前驱指针获取前驱结点，所以在双向链表中，删除结点只需要O(1)的时间复杂度
     */
    public void remove(K k) {
        Node<K, V> node = table.get(k);
        if (node == null) {
            return;
        }
        removeNode(node);
        table.remove(k);
        length--;
    }

    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNodeToHead(Node<K, V> node) {
        // 更新node指针
        node.next = head.next;
        node.prev = head;
        // 更新原来head.next指向的节点，让它的prev指向现在的node
        head.next.prev = node;
        // 让head.next指向现在的node
        head.next = node;
    }

    /**
     * 双向链表节点
     */
    private static class Node<K, V> {
        private K k;
        private V v;
        private Node<K, V> prev;
        private Node<K, V> next;
    }

    public static void main(String[] args) {
        LRUBaseHashTable<String, String> lruList = new LRUBaseHashTable<>();
        lruList.add("张三", "20");
        lruList.add("李四", "23");
        lruList.add("王五", "18");
        lruList.add("赵六", "34");

        System.out.println(lruList.get("王五"));

        lruList.remove("李四");
    }
}
