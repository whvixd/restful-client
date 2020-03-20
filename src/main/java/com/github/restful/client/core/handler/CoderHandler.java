package com.github.restful.client.core.handler;

import java.lang.reflect.Type;

/**
 * Created by wangzhx on 2020/3/20.
 */
public interface CoderHandler {
    <T> byte[] encode(T o);

    <T> T decode(byte[] bytes, Type type);
}
