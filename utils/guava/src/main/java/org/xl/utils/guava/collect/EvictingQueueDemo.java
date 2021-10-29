package org.xl.utils.guava.collect;

import com.google.common.collect.EvictingQueue;

import java.util.Arrays;

/**
 * @author xulei
 */
public class EvictingQueueDemo {

    public static void main(String[] args) {
        EvictingQueue<String> queue = EvictingQueue.create(3);

        queue.add("a");
        queue.add("b");
        queue.add("c");
        queue.add("d");

        System.out.println(Arrays.toString(queue.toArray()));
    }
}
