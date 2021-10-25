package org.xl.java.concurrence;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author xulei
 */
public class ConcurrentSkipListMapDemo {

    public static void main(String[] args) {
        ConcurrentSkipListMap<User, String> map = new ConcurrentSkipListMap<>();
        map.put(new User("b"), "b");
        map.put(new User("aa"), "aa");
        map.forEach((k, v) -> System.out.println("k is:" + k.name + ", v is:" + v));
    }

    private static class User implements Comparable<User> {

        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(User o) {
            return o.name.length() - name.length();
        }
    }
}
