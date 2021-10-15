package org.xl.java.concurrence;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xulei
 */
public class CountDownLunchDemo implements Runnable {

    // 初始化等待线程数
    private static final CountDownLatch countDownLatch = new CountDownLatch(5);

    @Override
    public void run() {
        try {
            int costTime = new Random().nextInt(10) * 1000;
            Thread.sleep(costTime);
            System.out.println("work done! cost:" + costTime);
            // 表示完成任务
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        final CountDownLunchDemo demo = new CountDownLunchDemo();
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(demo);
        }
        countDownLatch.await();
        System.out.println("all work already done!");
        pool.shutdown();
    }
}
