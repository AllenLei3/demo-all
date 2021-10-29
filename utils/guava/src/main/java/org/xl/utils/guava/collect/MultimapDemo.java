package org.xl.utils.guava.collect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Map;

/**
 * @author xulei
 */
public class MultimapDemo {

    public static void main(String[] args) {
        Multimap<String, String> multimap = HashMultimap.create();

        multimap.put("a", "1");
        multimap.put("a", "2");
        multimap.put("a", "3");

        multimap.put("b", "3");
        multimap.put("b", "4");
        multimap.put("b", "5");
        multimap.put("b", "6");

        System.out.println(multimap.size());
        System.out.println(multimap.isEmpty());
        System.out.println(multimap.containsKey("a"));
        System.out.println(multimap.containsKey("c"));
        System.out.println(multimap.containsValue("1"));
        System.out.println(multimap.containsValue("4"));
        System.out.println(multimap.containsValue("7"));

        System.out.println(multimap.containsEntry("a", "3"));
        System.out.println(multimap.containsEntry("b", "3"));

        System.out.println(multimap.values());

        for (Map.Entry<String, String> entry : multimap.entries()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        System.out.println(multimap.asMap());
    }
}
