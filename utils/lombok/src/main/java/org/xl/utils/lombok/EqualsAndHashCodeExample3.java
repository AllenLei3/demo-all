package org.xl.utils.lombok;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @author xulei
 */
public class EqualsAndHashCodeExample3 {

    public static void main(String[] args) {
        Employee employee1 = new Employee("a", "1", "A");
        Employee employee2 = new Employee("b", "1", "A");
        System.out.println(employee1.equals(employee2));
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    private static class Person {
        private final String name;
        private final String identity;
    }

    @EqualsAndHashCode(callSuper = true)
    private static class Employee extends Person {
        private String company;

        public Employee(String name, String identity, String company) {
            super(name, identity);
            this.company = company;
        }
    }
}
