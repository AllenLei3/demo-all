package org.xl.utils.guava.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

/**
 * @author xulei
 */
public class MultisetDemo {

    public static void main(String[] args) {
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("b", 2);
        multiset.setCount("a", 4);

        System.out.println(multiset.size());
        System.out.println(multiset.count("a"));
        System.out.println(multiset.elementSet());

        for (Multiset.Entry<String> entry : multiset.entrySet()) {
            entry.getCount();
            entry.getElement();
        }

        multiset.forEachEntry((element, size) -> System.out.println("element is:" + element + ", size is:" + size));
    }
}
