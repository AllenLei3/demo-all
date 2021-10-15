package org.xl.java.concurrence;

import java.util.concurrent.CountDownLatch;

/**
 * volatile非原子性的代码示例
 *
 * @author xulei
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREAD_COUNT = 20;
    private static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
                countDownLatch.countDown();
            });
            threads[i].start();
        }
        // 等待所有累加线程都结束
        countDownLatch.await();
        System.out.println(race);
    }
}