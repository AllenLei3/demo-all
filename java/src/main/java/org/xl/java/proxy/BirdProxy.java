package org.xl.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xulei
 */
public class BirdProxy implements InvocationHandler {

    private final Bird bird;

    public BirdProxy(Bird bird) {
        this.bird = bird;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Get Bird Name");
        return method.invoke(bird, args);
    }
}
