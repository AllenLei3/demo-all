package org.xl.utils.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author xulei
 */
public class BiMapDemo {

    public static void main(String[] args) {
        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("a", "1");
        biMap.put("b", "2");

        biMap.inverse().forEach((v, k) -> System.out.println("v is:" + v + ", k is:" + k));
    }
}
