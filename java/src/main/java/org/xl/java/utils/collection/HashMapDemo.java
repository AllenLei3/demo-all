package org.xl.java.utils.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap常用操作示例
 *
 * @author xulei
 */
public class HashMapDemo {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("user", "xl");
        map.put("age", "25");
        map.put("hobby", null);

        // 返回指定键所映射到的值，如果key不存在则返回defaultValue
        System.out.println(map.getOrDefault("user", "NoOne"));
        System.out.println(map.getOrDefault("hobby", "NoOne"));
        System.out.println(map.getOrDefault("name", "NoOne"));

        map.compute("name", (k, v) -> k + v);
        System.out.println(map.get("name"));

        map.computeIfAbsent("name", s -> s + "1");
        System.out.println(map.get("name"));

        map.computeIfPresent("name", (k, v) -> k + v);
        System.out.println(map.get("name"));

        map.merge("age", "100", (oldV, newV) -> oldV + newV);
        System.out.println(map.get("age"));

        map.replaceAll((k, v) -> "1");
        System.out.println(map.get("user"));
        System.out.println(map.get("age"));
        System.out.println(map.get("name"));

    }
}
