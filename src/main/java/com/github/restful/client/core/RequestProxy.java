package com.github.restful.client.core;

import com.github.restful.client.core.impl.RequestServiceImpl;
import com.github.restful.client.model.common.RequestParam;
import com.google.common.reflect.Reflection;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Proxy;

/**
 * Created by wangzhx on 2018/12/1.
 */
@Slf4j
@SuppressWarnings("all")
public enum RequestProxy {
    INSTANCE;

    private RequestService requestService = new RequestServiceImpl();

    public <T> T getClientProxy(Class<T> clientType) {
        /**
         * 1. 获取 @RequestMapping 的请求地址
         * 2. 获取 请求方式 @RequestGet
         * 3. 获取请求头，请求体，解析pathParam、queryParam
         */
        return Reflection.newProxy(clientType, (proxy, method, args) -> requestService.invoke(RequestParam.getRequestParam(method, args)));
    }

    public <T> T getCglibClientProxy(Class<T> clientType) {
        return (T) Enhancer.create(clientType, (MethodInterceptor) (proxy, method, args, methodProxy) ->
                requestService.invoke(RequestParam.getRequestParam(method, args)));
    }

    public <T> T getJdkClientProxy(Class<T> clientType) {
        return (T) Proxy.newProxyInstance(clientType.getClassLoader(), new Class[]{clientType},
                (proxy, method, args) -> requestService.invoke(RequestParam.getRequestParam(method, args)));
    }


}
