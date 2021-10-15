package org.xl.java.concurrence;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author xulei
 */
public class PhaserExample {

    public static void main(String[] args) {
        final int parties = 3;
        final int phases = 4;
        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("====== Phase :" + phase + "======");
                return super.onAdvance(phase, registeredParties);
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < parties; i++) {
            // 注册各个参与者线程
            phaser.register();
            executorService.execute(() -> {
                for (int phase = 0; phase < phases; phase++) {
                    // 等待其它参与者线程到达
                    int j = phaser.arriveAndAwaitAdvance();
                    System.out.println(String.format("currentThread:%s, Executing the task, currentPhase:%s",
                            Thread.currentThread().getName(), j));
                }
            });
        }
        executorService.shutdown();
    }
}
