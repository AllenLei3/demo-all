package org.xl.java.concurrence;

import java.util.concurrent.CompletableFuture;

/**
 * @author xulei
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future1");
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("future2");
        });
        future1.applyToEither(future2, f -> {
            System.out.println("thenApply");
            return null;
        });
        System.out.println("main");
        Thread.sleep(100000);
    }
}
