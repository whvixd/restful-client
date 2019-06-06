package com.github.restful.client.model.common;

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

        RequestGet requestGet = method.getAnnotation(RequestGet.class);
        RequestPost requestPost = method.getAnnotation(RequestPost.class);
        RequestPut requestPut = method.getAnnotation(RequestPut.class);
        RequestDelete requestDelete = method.getAnnotation(RequestDelete.class);
        RequestParam requestParam = new RequestParam();
        requestParam.setResultType(method.getGenericReturnType());
        if (Objects.nonNull(requestGet)) {
            requestParam.setRequestType(RequestType.Get);
            requestParam.setArgs(method, args, requestParam);
            requestParam.setUrl(StringUtil.analysisPathAndQueryParam(
                    StringUtil.getFullUrl(ipAndPort, requestGet.path()),
                    requestParam.getPathParam(),
                    requestParam.getQueryParam()));

        } else if (Objects.nonNull(requestPost)) {
            requestParam.setRequestType(RequestType.Post);
            requestParam.setArgs(method, args, requestParam);
            requestParam.setUrl(StringUtil.analysisPathAndQueryParam(
                    StringUtil.getFullUrl(ipAndPort, requestPost.path()),
                    requestParam.getPathParam(),
                    requestParam.getQueryParam()));

        } else if (Objects.nonNull(requestPut)) {
            requestParam.setRequestType(RequestType.Put);
            requestParam.setArgs(method, args, requestParam);
            requestParam.setUrl(StringUtil.analysisPathAndQueryParam(
                    StringUtil.getFullUrl(ipAndPort, requestPut.path()),
                    requestParam.getPathParam(),
                    requestParam.getQueryParam()));

        } else if (Objects.nonNull(requestDelete)) {
            requestParam.setRequestType(RequestType.Delete);
            requestParam.setArgs(method, args, requestParam);
            requestParam.setUrl(StringUtil.analysisPathAndQueryParam(
                    StringUtil.getFullUrl(ipAndPort, requestDelete.path()),
                    requestParam.getPathParam(),
                    requestParam.getQueryParam()));

        }
        return requestParam;
    }


    /**
     * 1. 从method中获取params
     * 2. 从param中获取注解
     * 3. 被修饰的注解参数set到requestParam中
     *
     * @param method
     * @param args
     * @param requestParam
     */
    private void setArgs(Method method, Object[] args, RequestParam requestParam) {
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
                requestParam.setHeaders((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestPathParam) && Objects.nonNull(args[i]) && args[i] instanceof Map) {
                requestParam.setPathParam((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestQueryParam) && Objects.nonNull(args[i]) && args[i] instanceof Map) {
                requestParam.setQueryParam((Map<String, String>) args[i]);
            } else if (Objects.nonNull(requestBody) && Objects.nonNull(args[i])) {
                requestParam.setBody(args[i]);
            }
        }
    }


}
