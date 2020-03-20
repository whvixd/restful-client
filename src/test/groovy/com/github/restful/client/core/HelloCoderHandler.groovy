package com.github.restful.client.core

import com.github.restful.client.core.handler.CoderHandler
import com.github.restful.client.utils.FastJsonUtil
import com.github.restful.client.utils.GsonUtil

import java.lang.reflect.Type

/**
 * Created by wangzhx on 2020/3/20.
 */
class HelloCoderHandler implements CoderHandler {
    @Override
    def <T> byte[] encode(T o) {
        return GsonUtil.toJson(o)
    }

    @Override
    def <T> T decode(byte[] bytes, Type type) {
        return FastJsonUtil.fromJson(bytes, type)
    }
}
