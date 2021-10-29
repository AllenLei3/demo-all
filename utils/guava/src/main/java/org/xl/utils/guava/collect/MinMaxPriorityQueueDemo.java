package org.xl.utils.guava.collect;

import com.google.common.collect.MinMaxPriorityQueue;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author xulei
 */
public class MinMaxPriorityQueueDemo {

    public static void main(String[] args) {
        MinMaxPriorityQueue<User> users = MinMaxPriorityQueue
                .orderedBy((Comparator<User>) (o1, o2) -> o1.getAge() - o2.getAge())
                .maximumSize(3).create();

        users.add(new User(1));
        users.add(new User(2));
        users.add(new User(3));
        users.add(new User(4));

        System.out.println(Arrays.toString(users.toArray()));
    }

    @Getter
    @Setter
    private static class User {

        private int age;

        public User(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    '}';
        }
    }
}
