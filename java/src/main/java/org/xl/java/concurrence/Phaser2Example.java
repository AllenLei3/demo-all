package org.xl.java.concurrence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author xulei
 */
public class Phaser2Example {

    public static void main(String[] args) {
        final int totalRequestCount = 10;
        // 注册主线程, 当外部条件满足时, 由主线程打开开关
        Phaser phaser = new Phaser(1);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalRequestCount; i++) {
            // 注册各个参与者线程
            phaser.register();
            executorService.execute(() -> {
                // 等待其它参与者线程到达
                int j = phaser.arriveAndAwaitAdvance();
                System.out.println(String.format("currentThread:%s, Executing the task, currentPhase:%s", Thread.currentThread().getName(), j));
            });
        }
        // 打开开关 [parties 共 11 个, 主线程从之后需要等待的成员中移除, 即 parties 还剩 10]
        phaser.arriveAndAwaitAdvance();
        System.out.println("主线程打开了开关");
        executorService.shutdown();
    }
}
