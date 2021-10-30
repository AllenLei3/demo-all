package org.xl.utils.lombok;

import lombok.Builder;
import lombok.ToString;

/**
 * @author xulei
 */
public class BuilderDemo {

    @Builder(toBuilder = true)
    @ToString
    private static class Person {
        private final String name;
        private final String identity;
        @Builder.Default
        private final long birthday = System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Person person = Person.builder()
                .name("xl")
                .identity("123").build();
        System.out.println(person);

        person = person.toBuilder()
                .identity("456")
                .birthday(111).build();
        System.out.println(person);
    }
}
