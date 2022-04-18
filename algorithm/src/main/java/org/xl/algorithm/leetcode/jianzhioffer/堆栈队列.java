package org.xl.algorithm.leetcode.jianzhioffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author xulei
 */
public class 堆栈队列 {

    /**
     * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
     * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
     *
     * 示例 1：
     *
     * 输入：
     * ["CQueue","appendTail","deleteHead","deleteHead"]
     * [[],[3],[],[]]
     * 输出：[null,null,3,-1]
     *
     * 示例 2：
     *
     * 输入：
     * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
     * [[],[],[5],[2],[],[]]
     * 输出：[null,-1,null,null,5,2]
     */
    public static class CQueue {

        private final java.util.Stack<Integer> stack = new java.util.Stack<>();
        private final java.util.Stack<Integer> deleteStack = new java.util.Stack<>();

        public void appendTail(int value) {
            stack.push(value);
        }

        public int deleteHead() {
            if (deleteStack.isEmpty()) {
                // 将stack中的值传递给deleteStack
                while (!stack.isEmpty()) {
                    deleteStack.push(stack.pop());
                }
            }
            // 如果deleteStack里还有值,则先输出deleteStack中的值
            return deleteStack.isEmpty() ? -1 : deleteStack.pop();
        }
    }

    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。
     *
     * 示例:
     *
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.min();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();   --> 返回 0.
     * minStack.min();   --> 返回 -2.
     */
    public static class MinStack {

        private final Stack<Integer> stack = new Stack<>();
        // minStack的栈顶始终保存最小元素，元素的相对位置和stack一致
        private final Stack<Integer> minStack = new Stack<>();

        public void push(int x) {
            stack.push(x);
            if (minStack.empty()) {
                minStack.push(x);
            } else {
                // 如果比minStack中的栈顶元素小
                if (minStack.peek() >= x) {
                    minStack.push(x);
                }
            }
        }

        public void pop() {
            int x = stack.pop();
            if (!minStack.empty()) {
                // 同步对minStack出栈
                if (minStack.peek() == x) {
                    minStack.pop();
                }
            }
        }

        public int top() {
            return stack.peek();
        }

        public int min() {
            return minStack.peek();
        }
    }

    /**
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。
     * 例如，序列 {1,2,3,4,5} 是某栈的压栈序列，序列 {4,5,3,2,1} 是该压栈序列对应的一个弹出序列，但 {4,3,5,1,2} 就不可能是该压栈序列的弹出序列。
     *
     * 示例 1：
     *
     * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
     * 输出：true
     * 解释：我们可以按以下顺序执行：
     * push(1), push(2), push(3), push(4), pop() -> 4,
     * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
     *
     * 示例 2：
     *
     * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
     * 输出：false
     * 解释：1 不能在 2 之前弹出。
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        // 入栈
        for (int item : pushed) {
            stack.push(item);
            // 每进行一次入栈，递归判断是否可以出栈
            while (!stack.empty() && stack.peek() == popped[i]) {
                stack.pop();
                i++;
            }
        }
        return stack.empty();
    }

    /**
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
     * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
     *
     * 例如，
     *
     * [2,3,4] 的中位数是 3
     * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
     *
     * 设计一个支持以下两种操作的数据结构：
     *
     * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
     * double findMedian() - 返回目前所有元素的中位数。
     *
     * 示例 1：
     *
     * 输入：
     * ["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
     * [[],[1],[2],[],[3],[]]
     * 输出：[null,null,null,1.50000,null,2.00000]
     *
     * 示例 2：
     *
     * 输入：
     * ["MedianFinder","addNum","findMedian","addNum","findMedian"]
     * [[],[2],[],[3],[]]
     * 输出：[null,null,2.00000,null,2.50000]
     */
    public static class MedianFinder {

        // 大顶堆，保存较小的一半
        private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        // 小顶堆，保存较大的一半
        private final PriorityQueue<Integer> minHeap = new PriorityQueue<>((x, y) -> y - x);

        public void addNum(int num) {
            if (maxHeap.size() != minHeap.size()) {
                maxHeap.add(num);
                minHeap.add(maxHeap.poll());
            } else {
                minHeap.add(num);
                maxHeap.add(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() != minHeap.size()) {
                return maxHeap.peek();
            } else {
                return (maxHeap.peek() + minHeap.peek()) / 2D;
            }
        }
    }

    /**
     * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
     * 若队列为空，pop_front 和 max_value 需要返回 -1
     *
     * 示例 1：
     *
     * 输入:
     * ["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
     * [[],[1],[2],[],[],[]]
     * 输出: [null,null,null,2,1,2]
     *
     * 示例 2：
     *
     * 输入:
     * ["MaxQueue","pop_front","max_value"]
     * [[],[],[]]
     * 输出: [null,-1,-1]
     */
    public static class MaxQueue {

        private final Deque<Integer> deque = new LinkedList<>();
        // 单调递减队列，队列头即为最大值
        private final Deque<Integer> maxDeque = new LinkedList<>();

        public int max_value() {
            return maxDeque.isEmpty() ? -1 : maxDeque.peekFirst();
        }

        public void push_back(int value) {
            deque.addLast(value);
            if (!maxDeque.isEmpty()) {
                // 把小于当前值的元素都出队,保证队列是单调递减的
                while (!maxDeque.isEmpty() && maxDeque.peekLast() < value) {
                    maxDeque.pollLast();
                }
            }
            maxDeque.addLast(value);
        }

        public int pop_front() {
            int res = deque.isEmpty() ? -1 : deque.pollFirst();
            // 同步删除，因为maxDeque中的元素与deque中元素的相对位置是相同的
            if (!maxDeque.isEmpty() && res == maxDeque.peekFirst()) {
                maxDeque.pollFirst();
            }
            return res;
        }
    }
}
