package com.github.restful.client.core.impl;

import com.github.restful.client.core.RequestService;
import com.github.restful.client.exception.ServerException;
import com.github.restful.client.model.common.RequestParam;
import com.github.restful.client.utils.FastJsonUtil;
import com.github.restful.client.utils.OkHttpUtil;

/**
 * Created by wangzhx on 2018/11/6.
 */
// TODO: 2019/02/06 PUT Delete 需测试
public class RequestServiceImpl<T> implements RequestService {

    @Override
    public T invoke(RequestParam requestParam) {
        switch (requestParam.getRequestType()) {
            case Get:
                return FastJsonUtil.fromJson(OkHttpUtil.doGet(requestParam.getUrl(), requestParam.getHeaders()), requestParam.getResultType());
            case Post:
                return FastJsonUtil.fromJson(OkHttpUtil.doPost(requestParam.getUrl(), requestParam.getHeaders(), FastJsonUtil.toJson(requestParam.getBody())), requestParam.getResultType());
            case Put:
                return FastJsonUtil.fromJson(OkHttpUtil.doPut(requestParam.getUrl(), requestParam.getHeaders(), FastJsonUtil.toJson(requestParam.getBody())), requestParam.getResultType());
            case Delete:
                return FastJsonUtil.fromJson(OkHttpUtil.doDelete(requestParam.getUrl(), requestParam.getHeaders(), FastJsonUtil.toJson(requestParam.getBody())), requestParam.getResultType());
            default:
                throw new ServerException(String.format("暂不支持 %s 请求方式", requestParam.getRequestType()));
        }
    }
}
