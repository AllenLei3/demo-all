package org.xl.java.concurrence;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author xulei
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static final AtomicIntegerFieldUpdater<Holder> updater =
            AtomicIntegerFieldUpdater.newUpdater(Holder.class,"count");

    public static void main(String[] args) {
        Holder holder = new Holder();

        if (updater.compareAndSet(holder,100,120)) {
            System.out.println("Update Success! current value is: " + holder.getCount());
        }
        if (updater.compareAndSet(holder,100,130)) {
            System.out.println("Update Success! current value is: " + holder.getCount());
        } else {
            System.out.println("Update Fail! current value is: " + holder.getCount());
        }
    }

    @Getter
    @Setter
    private static class Holder {

        public volatile int count = 100;
    }
}
