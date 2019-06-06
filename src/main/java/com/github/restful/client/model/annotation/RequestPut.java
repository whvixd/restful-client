package com.github.restful.client.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * put请求方式
 * <p>
 * Created by wangzhx on 2018/11/6.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RequestPut {
    String path();

    String message() default "";
}
