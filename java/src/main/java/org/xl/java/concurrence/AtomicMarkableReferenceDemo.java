package org.xl.java.concurrence;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xulei
 */
public class AtomicMarkableReferenceDemo {

    private final AtomicMarkableReference<Integer> count = new AtomicMarkableReference<>(0, true);

    public int getCount() {
        return count.getReference();
    }

    public int increment() {
        boolean[] stamp = new boolean[1];
        while(true) {
            // 同时获取时间戳和数据，防止获取到数据和版本不是一致的
            Integer value = count.get(stamp);
            int newValue = value + 1;
            boolean writeOk = count.compareAndSet(value, newValue, stamp[0], stamp[0]);
            if (writeOk) {
                return newValue;
            }
        }
    }

    public int decrement() {
        boolean[] stamp = new boolean[1];
        while(true) {
            // 同时获取时间戳和数据，防止获取到数据和版本不是一致的
            Integer value = count.get(stamp);
            int newValue = value - 1;
            boolean writeOk = count.compareAndSet(value, newValue, stamp[0], stamp[0]);
            if (writeOk) {
                return newValue;
            }
        }
    }
}
