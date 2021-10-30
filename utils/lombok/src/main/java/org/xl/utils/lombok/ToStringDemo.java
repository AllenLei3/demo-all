package org.xl.utils.lombok;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * @author xulei
 */
public class ToStringDemo {

    public static void main(String[] args) {
        Employee employee = new Employee("a", "1", "A");
        System.out.println(employee.toString());
    }

    @ToString(onlyExplicitlyIncluded = true)
    @AllArgsConstructor
    private static class Person {
        @ToString.Include(rank = 1)
        private final String name;
        @ToString.Include(rank = 2)
        private final String identity;
        private final String other;
    }

    @ToString(includeFieldNames = false, callSuper = true)
    private static class Employee extends Person {
        private final String company;
        @ToString.Exclude
        private final String exclude;

        public Employee(String name, String identity, String company) {
            super(name, identity, "other");
            this.company = company;
            this.exclude = "exclude";
        }
    }
}
