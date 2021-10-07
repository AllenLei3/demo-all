package org.xl.java.grammar.annotation;

import java.lang.annotation.*;

/**
 * @author xulei
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Adapt {

    Class<?> value();
}
