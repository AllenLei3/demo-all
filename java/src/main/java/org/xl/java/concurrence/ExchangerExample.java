package org.xl.java.concurrence;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xulei
 */
public class ExchangerExample {

    private static final Integer WORK_COUNT = 2;

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < WORK_COUNT; i++) {
            executorService.execute(() -> {
                String threadName = Thread.currentThread().getName();
                try {
                    String afterObj = exchanger.exchange(threadName);
                    System.out.println(String.format("currentThread %s , before exchange %s , after exchange %s",
                            Thread.currentThread().getName(), threadName, afterObj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
