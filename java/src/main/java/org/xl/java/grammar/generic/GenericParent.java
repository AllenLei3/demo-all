package org.xl.java.grammar.generic;

/**
 * @author xulei
 */
public class GenericParent<T extends Number> {

    private T name;

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }
}
