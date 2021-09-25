package org.xl.java.proxy;

/**
 * @author xulei
 */
public class Eagle implements Bird {

    private final String name;

    public Eagle(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
