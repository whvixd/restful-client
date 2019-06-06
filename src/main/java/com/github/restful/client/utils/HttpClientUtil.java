package com.github.restful.client.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhx on 2018/09/09.
 * <p>
 * 封装了HttpClient 工具包中的get/post请求
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final HttpClient HTTP_CLIENT = HttpClients.createDefault();

    /**
     * GET
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        HttpGet get = new HttpGet(url);

        return getHttpResponseResult(get);
    }

    /**
     * POST
     *
     * @param url
     * @param body
     * @param headers
     * @return
     */
    public static String doPost(String url, String body, Header... headers) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        HttpPost post = HttpPostBuilder.custom()
                .setUrl(url)
                .setConfig(requestConfig)
                .setEntity(new ByteArrayEntity(body.getBytes()))
                .setHeaders(headers)
                .builder();

        return getHttpResponseResult(post);

    }


    public static String doPost(String url, String body, Map<String, String> headers) {
        return doPost(url, body, headersMap2Array(headers));
    }


    /**
     * 构建HttpPost
     */
    static class HttpPostBuilder {
        private final static HttpPost POST = new HttpPost();
        private final static HttpPostBuilder HTTP_POST_BUILDER = new HttpPostBuilder();

        public static HttpPostBuilder custom() {
            POST.setConfig(null);
            POST.setEntity(null);
            POST.setHeader(null);
            POST.setHeaders(null);
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setUrl(final String uri) {
            if (StringUtils.isNoneBlank(uri)) {
                POST.setURI(URI.create(uri));
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setConfig(final RequestConfig config) {
            if (POST.getConfig() == null) {
                POST.setConfig(config);
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setEntity(final HttpEntity entity) {
            if (POST.getEntity() == null && entity != null) {
                POST.setEntity(entity);
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder addHeader(final Header header) {
            if (header != null) {
                POST.addHeader(header);
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setHeader(final String headerName, final String headerValue) {
            if (StringUtils.isNoneBlank(headerName + headerValue)) {

                POST.setHeader(headerName, headerValue);
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setHeaders(final Header[] headers) {
            if (headers != null && POST.getAllHeaders().length == 0 && headers.length != 0) {
                POST.setHeaders(headers);
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPostBuilder setHeaders(Map<String, String> headers) {
            if (headers != null && headers.size() != 0) {
                List<Header> headerList = Lists.newArrayList();
                headers.forEach((name, value) ->
                        headerList.add(new BasicHeader(name, value)));
                setHeaders(headerList.toArray(new Header[headerList.size()]));
            }
            return HTTP_POST_BUILDER;
        }

        public HttpPost builder() {
            return POST;
        }

    }

    private static String getHttpResponseResult(HttpRequestBase request) {
        try {
            HttpResponse response = HTTP_CLIENT.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static Header[] headersMap2Array(Map<String, String> headers) {
        List<Header> headerList = Lists.newArrayList();
        headers.forEach((name, value) ->
                headerList.add(new BasicHeader(name, value)));
        return headerList.toArray(new Header[headerList.size()]);
    }

}
