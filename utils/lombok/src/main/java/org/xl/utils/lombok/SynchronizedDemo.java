package org.xl.utils.lombok;

import lombok.Synchronized;

/**
 * @author xulei
 */
public class SynchronizedDemo {

    private final Object readLock = new Object();

    @Synchronized
    public static void hello() {
        System.out.println("world");
    }
    @Synchronized
    public int answerToLife() {
        return 42;
    }
    @Synchronized("readLock")
    public void foo() {
        System.out.println("bar");
    }
}
