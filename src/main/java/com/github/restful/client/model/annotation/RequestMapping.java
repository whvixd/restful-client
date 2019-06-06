package com.github.restful.client.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于接口请求的ip地址
 * <p>
 * Created by wangzhx on 2018/12/1.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RequestMapping {
    String path() default "127.0.0.1:8080";

    String message() default "";
}
