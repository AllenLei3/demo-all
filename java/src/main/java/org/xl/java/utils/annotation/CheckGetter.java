package org.xl.java.utils.annotation;

import java.lang.annotation.*;

/**
 * @author xulei
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.SOURCE)
public @interface CheckGetter {}
