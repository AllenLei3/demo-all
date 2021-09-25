package org.xl.java.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xulei
 */
public class ProxyTest {

    public static void main(String[] args) {
        Bird eagle = new Eagle("老鹰");
        Bird proxy = (Bird) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{Bird.class},
                new BirdProxy(eagle));
        System.out.println(proxy.getName());
        System.out.println(Proxy.isProxyClass(proxy.getClass()));

        Class<?> proxyClz = Proxy.getProxyClass(ClassLoader.getSystemClassLoader(), Bird.class);
        System.out.println(proxyClz.getName());
    }
}
