package com.onlinebookstore.annotation;

import java.lang.annotation.*;

/**
 * 增强的@RequestBody注解，可以同时转换多个json对象为javabean
 * @author rkc
 * @date 2020/11/7 22:12
 * @version 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonObject {
    String value();

    boolean required() default true;

    String defaultValue() default "";
}
