package com.github.restful.client.core.handler;

import com.github.restful.client.core.proxy.OkHttpProxy;
import com.github.restful.client.exception.ServerException;
import com.github.restful.client.model.common.RequestParam;
import com.github.restful.client.model.enums.RequestType;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by wangzhx on 2018/11/6.
 */
// TODO: 2019/02/06 PUT Delete 需测试
@Slf4j
public class RequestHandler<T> {

    private OkHttpProxy okHttpProxy;

    public RequestHandler() {
        okHttpProxy = new OkHttpProxy();
    }

    public T doInvoke(RequestParam requestParam) {
        CoderHandler coderHandler = requestParam.getCoderHandler();
        RequestType requestType = requestParam.getRequestType();
        String url = requestParam.getUrl();
        Map<String, String> headers = requestParam.getHeaders();
        Object body = requestParam.getBody();
        byte[] encode = coderHandler.encode(body);

        T result = coderHandler.decode(getResult(requestType, url, headers, encode), requestParam.getResultType());
        log.info("requestType:{},url:{},headers:{},body:{},result:{}", requestType, url, headers, body, result);
        return result;

    }

    private byte[] getResult(RequestType requestType, String url, Map<String, String> headers, byte[] encode) {
        switch (requestType) {
            case Get:
                return okHttpProxy.doGet(url, headers);
            case Post:
                return okHttpProxy.doPost(url, headers, encode);
            case Put:
                return okHttpProxy.doPut(url, headers, encode);
            case Delete:
                return okHttpProxy.doDelete(url, headers, encode);
            default:
                throw new ServerException(String.format("暂不支持 %s 请求方式", requestType));
        }

    }
}
