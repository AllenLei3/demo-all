package org.xl.algorithm.stack;

/**
 * 顺序栈（数组实现, 不支持动态扩容）
 *
 * @author xulei
 */
public class ArrayStack {

    private final String[] item;

    /**
     * 数组实际存储的元素数量
     */
    private int count;

    public ArrayStack(int capacity) {
        item = new String[capacity];
    }

    /**
     * 入栈, 顺序写入, 避免移动数组元素
     */
    public boolean push(String value) {
        // 栈满
        if (count == item.length) {
            return false;
        }
        item[count] = value;
        ++count;
        return true;
    }

    /**
     * 出栈, 从数组尾部开始出栈
     */
    public String pop() {
        // 栈空
        if (count == 0) {
            return null;
        }
        String value = item[count - 1];
        --count;
        return value;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(10);
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
