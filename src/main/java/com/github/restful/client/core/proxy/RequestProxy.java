package com.github.restful.client.core.proxy;

import com.github.restful.client.core.handler.RequestHandler;
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
    private RequestHandler requestHandler;

    private RequestProxy() {
        requestHandler = new RequestHandler();
    }

    public <T> T invoke(Class<T> clientType) {
        return clientType.isInterface()
                ? getProxyByInterface(clientType) : getProxyByClazz(clientType);
    }

    private <T> T getProxyByInterface(Class<T> clientType) {
        return Reflection.newProxy(clientType, (proxy, method, args) ->
                requestHandler.doInvoke(RequestParam.getRequestParam(method, args)));
    }


    private <T> T getProxyByClazz(Class<T> clientType) {
        return (T) Enhancer.create(clientType, (MethodInterceptor) (proxy, method, args, methodProxy) ->
                requestHandler.doInvoke(RequestParam.getRequestParam(method, args)));
    }

    private <T> T getJdkClientProxy(Class<T> clientType) {
        return (T) Proxy.newProxyInstance(clientType.getClassLoader(), new Class[]{clientType},
                (proxy, method, args) -> requestHandler.doInvoke(RequestParam.getRequestParam(method, args)));
    }


}
