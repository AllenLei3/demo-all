package org.xl.java.grammar.annotation;

import java.lang.annotation.*;

/**
 * @author xulei
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.SOURCE)
public @interface CheckGetter {}
