package com.github.restful.client.core;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by wangzhx on 2018/12/1.
 */
@Data
public class RequestProxyFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clientType;

    @Override
    public T getObject() throws Exception {
        return RequestProxy.INSTANCE.getClientProxy(clientType);
    }

    @Override
    public Class<T> getObjectType() {
        return clientType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
