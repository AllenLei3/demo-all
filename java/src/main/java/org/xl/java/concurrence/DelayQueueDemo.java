package org.xl.java.concurrence;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author xulei
 */
public class DelayQueueDemo {

    private static final DelayQueue<User> queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        User user1 = new User("A", System.currentTimeMillis() + 10);
        User user2 = new User("B", System.currentTimeMillis() + 20);
        queue.put(user1);
        queue.put(user2);

        User user = queue.take();
        System.out.println(user.name);
    }

    @Getter
    @Setter
    private static class User implements Delayed {

        private String name;

        // timestamp
        private long birthday;

        private long init;

        public User(String name, long birthday) {
            this.name = name;
            this.birthday = birthday;
            this.init = System.currentTimeMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(init - birthday, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (birthday - o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
