package org.xl.java.concurrence;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author xulei
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        DelayQueue<User> queue = new DelayQueue<>();
        // 创建延迟任务
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            User user = new User("task:" + i, random.nextInt(500));
            queue.offer(user);
        }
        // 获取任务
        User user;
        try {
            for (int i = 0; i < 10; i++) {
                user = queue.take();
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    private static class User implements Delayed {

        private final String name;

        // timestamp
        private final long delayTime;

        private final long expire;

        public User(String name, long delay) {
            this.name = name;
            this.delayTime = delay;
            this.expire = System.currentTimeMillis() + delay;
        }

        /**
         * 剩余时间=到期时间-当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "User{name=" + name + ", delayTime=" + delayTime + ", expire=" + expire + "}";
        }
    }
}
