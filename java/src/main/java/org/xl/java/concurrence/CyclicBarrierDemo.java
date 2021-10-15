package org.xl.java.concurrence;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xulei
 */
public class CyclicBarrierDemo implements Runnable {

    public static class Worker implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        public Worker(CyclicBarrier barrier) {
            this.cyclicBarrier = barrier;
        }

        @Override
        public void run() {
            try {
                int costTime = new Random().nextInt(10) * 1000;
                Thread.sleep(costTime);
                System.out.println("work done! cost:" + costTime);
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        System.out.println("all work already done!");
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new CyclicBarrierDemo());
        Worker worker = new Worker(cyclicBarrier);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            pool.execute(worker);
        }
        pool.shutdown();
    }
}
