package org.xl.java.utils.io;

import java.util.Scanner;

/**
 * @author xulei
 */
public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String line = scanner.next();
            System.out.println(line);
        }
    }
}
