package org.xl.java.utils.annotation;

import java.lang.annotation.*;

/**
 * @author xulei
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Adapt {

    Class<?> value();
}
