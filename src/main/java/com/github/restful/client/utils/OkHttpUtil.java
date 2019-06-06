package com.github.restful.client.utils;

import com.github.restful.client.exception.ArgValidationException;
import com.github.restful.client.exception.ServerException;
import com.github.restful.client.model.constant.ContentType;
import com.github.restful.client.model.constant.TimeoutConstants;
import lombok.experimental.UtilityClass;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * Created by wangzhx on 2018/11/6.
 */
@UtilityClass
public class OkHttpUtil {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(TimeoutConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TimeoutConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TimeoutConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .build();
    private static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);

    public String doGet(String url, Map<String, String> headers) {
        if (StringUtils.isBlank(url)) {
            log.error("请求url不能为空");
            throw new ArgValidationException("url为空");
        }

        Call call = OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String result = Objects.isNull(response.body()) ? null : response.body().string();
                log.info("url:{},headers:{},result:{}", url, FastJsonUtil.toJsonWithNull(headers), result);
                return result;
            } else {
                log.error("doGet调用失败:code:{},message:{},url:{},headers:{}",
                        response.code(), response.message(), url, headers.toString());
                throw new ServerException("doGet调用失败");
            }

        } catch (IOException e) {
            log.error("doGet调用失败:code:{},message:{},url:{},headers:{}", url, headers.toString());
            throw new ServerException("doGet调用失败");
        }
    }

    public String doPost(String url, Map<String, String> headers, String content) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(content)) {
            log.error("请求url:{}、body:{}不能为空", url, content);
            throw new ArgValidationException("url||body为空");
        }

        RequestBody body = RequestBody.create(MediaType.parse(ContentType.APPLICATION_JSON), content);
        Call call = OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(body)
                .build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String result = Objects.isNull(response.body()) ? null : response.body().string();
                log.info("url:{},headlers:{},arg:{},result:{}", url, FastJsonUtil.toJsonWithNull(headers), content, result);
                return result;
            } else {
                log.error("doPost调用失败:code:{},message:{},url:{},headers:{},body:{}",
                        response.code(), response.message(), url, headers.toString(), content);
                throw new ServerException("doPost调用失败");
            }

        } catch (IOException e) {
            log.error("doPost调用失败,url:{},headers:{},body:{}", url, headers.toString(), content);
            throw new ServerException("doPost调用失败");
        }
    }

    public String doPut(String url, Map<String, String> headers, String content) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(content)) {
            log.error("请求url:{}、body:{}不能为空", url, content);
            throw new ArgValidationException("url||body为空");
        }
        RequestBody body = RequestBody.create(MediaType.parse(ContentType.APPLICATION_JSON), content);
        Call call = OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .put(body)
                .build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String result = Objects.isNull(response.body()) ? null : response.body().string();
                log.info("url:{},headlers:{},arg:{},result:{}", url, FastJsonUtil.toJsonWithNull(headers), content, result);
                return result;
            } else {
                log.error("doPut调用失败:code:{},message:{},url:{},headers:{},body:{}",
                        response.code(), response.message(), url, headers.toString(), content);
                throw new ServerException("doPut调用失败");
            }
        } catch (IOException e) {
            log.error("doPut调用失败,url:{},headers:{},body:{}", url, headers.toString(), content);
            throw new ServerException("doPut调用失败");
        }
    }

    public String doDelete(String url, Map<String, String> headers, String content) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(content)) {
            log.error("请求url:{}、body:{}不能为空", url, content);
            throw new ArgValidationException("url||body为空");
        }
        RequestBody body = RequestBody.create(MediaType.parse(ContentType.APPLICATION_JSON), content);
        Call call = OK_HTTP_CLIENT.newCall(new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .delete(body)
                .build());
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String result = Objects.isNull(response.body()) ? null : response.body().string();
                log.info("url:{},headlers:{},arg:{},result:{}", url, FastJsonUtil.toJsonWithNull(headers), content, result);
                return result;
            } else {
                log.error("doDelete调用失败:code:{},message:{},url:{},headers:{},body:{}",
                        response.code(), response.message(), url, headers.toString(), content);
                throw new ServerException("doDelete调用失败");
            }
        } catch (IOException e) {
            log.error("doDelete调用失败,url:{},headers:{},body:{}", url, headers.toString(), content);
            throw new ServerException("doDelete调用失败");
        }
    }
}
