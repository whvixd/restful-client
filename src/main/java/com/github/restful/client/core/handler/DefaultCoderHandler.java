package com.github.restful.client.core.handler;

import com.github.restful.client.utils.FastJsonUtil;

import java.lang.reflect.Type;

/**
 * Created by wangzhx on 2020/3/20.
 */
public class DefaultCoderHandler implements CoderHandler {
    @Override
    public <T> byte[] encode(T o) {
        return FastJsonUtil.toJson(o);
    }

    @Override
    public <T> T decode(byte[] bytes, Type type) {
        return FastJsonUtil.fromJson(bytes, type);
    }
}
