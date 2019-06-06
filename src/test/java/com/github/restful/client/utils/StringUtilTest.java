package com.github.restful.client.utils;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wangzhx on 2018/12/2.
 */
@RunWith(Parameterized.class)
@AllArgsConstructor
public class StringUtilTest {
    private String url;
    private String expectedUrl;

    @Parameters
    public static Collection prepareData() {
        return Arrays.asList(new String[][]{
                {"172.0.0.1:8080/login/{user}/{password}?user=w&pwd=12", "172.0.0.1:8080/login/tom/123456?user=w&pwd=12"},
                {"172.0.0.1:8080/login/{user}/{password}", "172.0.0.1:8080/login/tom/123456"},
                {"172.0.0.1:8080/login/{user}/{password}?user={user}&pwd={pwd}", "172.0.0.1:8080/login/tom/123456?user=test&pwd=test"},
                {"172.0.0.1:8080/login/", "172.0.0.1:8080/login/"}
        });
    }

    @Test
    public void testAnalysisPathAndQueryParam() {
        Map<String, String> pathParam = Maps.newHashMap();
        pathParam.put("user", "tom");
        pathParam.put("password", "123456");
        Map<String, String> queryParam = Maps.newHashMap();
        queryParam.put("user", "test");
        queryParam.put("pwd", "test");
        String actualUrl = StringUtil.analysisPathAndQueryParam(url, pathParam, queryParam);
        Assert.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testCheckUrl() {
        Assert.assertTrue(StringUtil.checkUrl(""));
    }
}
