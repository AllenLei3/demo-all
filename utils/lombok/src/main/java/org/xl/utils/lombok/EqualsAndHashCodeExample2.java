package org.xl.utils.lombok;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @author xulei
 */
public class EqualsAndHashCodeExample2 {

    public static void main(String[] args) {
        Person person1 = new Person("a", "1");
        Person person2 = new Person("b", "1");
        System.out.println(person1.equals(person2));
    }

    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @AllArgsConstructor
    private static class Person {
        private final String name;

        @EqualsAndHashCode.Include
        private final String identity;
    }
}
