package org.xl.utils.lombok;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xulei
 */
public class BuilderDemo2 {
    @Builder
    @ToString
    private static class Person {
        @Singular("hobby")
        private List<String> hobby;
    }

    public static void main(String[] args) {
        List<String> hobby = new ArrayList<>();
        hobby.add("football");
        Person person = Person.builder()
                .hobby(hobby)
                .hobby("basketball")
//                .clearHobby()
                .build();
        System.out.println(person);
    }
}
