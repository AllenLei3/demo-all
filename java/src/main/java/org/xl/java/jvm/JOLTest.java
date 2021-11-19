package org.xl.java.jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author xulei
 */
public class JOLTest {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new B()).toPrintable());
    }
}
