package com.onlinebookstore.annotation;

import java.lang.annotation.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/9 18:54
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonObject {
    String value();

    boolean required() default true;

    String defaultValue() default "";
}
