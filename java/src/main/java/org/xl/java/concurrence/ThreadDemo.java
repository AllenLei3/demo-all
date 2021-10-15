package org.xl.java.concurrence;

/**
 * @author xulei
 */
public class ThreadDemo {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("start");
            throw new RuntimeException("timeout");
        });

        thread.setUncaughtExceptionHandler((t, throwable) -> {
            System.out.println("Occur Error with " + t.getName() + ", Cause: " + throwable.getMessage());
        });

        thread.start();
    }
}
