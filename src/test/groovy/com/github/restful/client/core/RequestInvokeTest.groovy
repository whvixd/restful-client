package com.github.restful.client.core

import com.github.restful.client.model.annotation.*
import com.google.common.collect.Maps
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by wangzhx on 2019/3/19.
 * todo 1. 协议默认是json解析，后面需要支持 字符串类型
 * todo 2. RequestHeader 只能接收Map类型，需支持单个类型
 */
class RequestInvokeTest extends Configuration {

    @Autowired
    HelloClient helloClient

    @RequestMapping(path = "127.0.0.1:8080", coder = HelloCoderHandler.class)
    interface HelloClient {
        @RequestGet(path = "/hello/get")
        String helloGet(@RequestHeader Map<String, String> headers)

        @RequestPost(path = "/hello/post")
        String helloPost(@RequestHeader Map<String, String> headers, @RequestBody Map<String, Object> body)
    }

    def "hello get"() {
        when:
        def result = helloClient.helloGet(Maps.newHashMap())
        then:
        result == excepted
        where:
        _ || excepted
        _ || "{\"message\":\"Hello Get\"}"
    }

    def "hello post"() {
        when:
        def result = helloClient.helloPost((Maps.newHashMap()), Maps.newHashMap())
        then:
        result == excepted
        where:
        _ || excepted
        _ || "{\"message\":\"Hello Post\"}"
    }

}
