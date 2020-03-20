package com.github.restful.client.model.common;

import com.github.restful.client.core.handler.CoderHandler;
import com.github.restful.client.exception.ServerException;
import com.github.restful.client.model.annotation.*;
import com.github.restful.client.model.constant.Constants;
import com.github.restful.client.model.enums.RequestType;
import com.github.restful.client.utils.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * Created by wangzhx on 2018/12/1.
 */
@Slf4j
@Data
public class RequestParam {
    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求类型
     */
    private RequestType requestType;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求路径参数
     */
    private Map<String, String> pathParam;

    /**
     * query路径参数
     */
    private Map<String, String> queryParam;

    /**
     * 请求体
     */
    private Object body;

    /**
     * 返回值类型
     */
    private Type resultType;

    /**
     * 编码解析器
     */
    private CoderHandler coderHandler;

    /**
     * 将method中被修饰的注解塞到RequestParam中
     *
     * @param method
     * @param args
     * @return
     */
    public static RequestParam getRequestParam(Method method, Object[] args) {
        Class<?> declaringClass = method.getDeclaringClass();
        RequestMapping requestMapping = declaringClass.getAnnotation(RequestMapping.class);
        if (Objects.isNull(requestMapping)) {
            log.error("未添加@RequestMapping");
            throw new ServerException("接口未添加@RequestMapping");
        }

        String ipAndPort = requestMapping.path();
        ipAndPort = ipAndPort.startsWith(Constants.HTTP) ? ipAndPort : Constants.HTTP.concat(ipAndPort);
        if (!StringUtil.checkUrl(ipAndPort)) {
            log.error("ip:{} 格式不对", ipAndPort);
            throw new ServerException("ip格式错误");
        }

        return doGetRequestParam(method, args, ipAndPort, requestMapping.coder());
    }

    private static RequestParam doGetRequestParam(Method method, Object[] args, String ipAndPort, Class<? extends CoderHandler> coder) {
        RequestParam requestParam = new RequestParam();

        RequestGet requestGet = method.getAnnotation(RequestGet.class);
        RequestPost requestPost = method.getAnnotation(RequestPost.class);
        RequestPut requestPut = method.getAnnotation(RequestPut.class);
        RequestDelete requestDelete = method.getAnnotation(RequestDelete.class);

        requestParam.setArgs(method, args);
        requestParam.setResultType(method.getReturnType());
        String path = null;

        if (Objects.nonNull(requestGet)) {
            requestParam.setRequestType(RequestType.Get);
            path = requestGet.path();

        } else if (Objects.nonNull(requestPost)) {
            requestParam.setRequestType(RequestType.Post);
            path = requestPost.path();

        } else if (Objects.nonNull(requestPut)) {
            requestParam.setRequestType(RequestType.Put);
            path = requestPut.path();

        } else if (Objects.nonNull(requestDelete)) {
            requestParam.setRequestType(RequestType.Delete);
            path = requestDelete.path();

        }
        requestParam.setUrl(StringUtil.analysisPathAndQueryParam(
                StringUtil.getFullUrl(ipAndPort, path),
                requestParam.getPathParam(),
                requestParam.getQueryParam()));

        requestParam.setCoderHandler(coder);
        return requestParam;
    }


    /**
     * 1. 从method中获取params
     * 2. 从param中获取注解
     * 3. 被修饰的注解参数set到requestParam中
     *
     * @param method
     * @param args
     */
    private void setArgs(Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 0 || args.length == 0) {
            return;
        }
        for (int i = 0; i < parameters.length; i++) {
            RequestHeader requestHeader = parameters[i].getAnnotation(RequestHeader.class);
            RequestPathParam requestPathParam = parameters[i].getAnnotation(RequestPathParam.class);
            RequestQueryParam requestQueryParam = parameters[i].getAnnotation(RequestQueryParam.class);
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);

            if (Objects.nonNull(requestHeader) && Objects.nonNull(args[i]) && args[i] instanceof Map) {
                this.setHeaders((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestPathParam) && Objects.nonNull(args[i]) && args[i] instanceof Map) {
                this.setPathParam((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestQueryParam) && Objects.nonNull(args[i]) && args[i] instanceof Map) {
                this.setQueryParam((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestBody) && Objects.nonNull(args[i])) {
                this.setBody(args[i]);
            }
        }
    }

    private void setCoderHandler(Class<? extends CoderHandler> clazz) {
        try {
            this.coderHandler = clazz.newInstance();
        } catch (Exception e) {
            log.error("coderHandler instance error ", e);
            throw new ServerException(e);
        }
    }

}
