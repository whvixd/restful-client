package com.github.restful.client.core

import com.github.restful.client.model.annotation.RequestBody
import com.github.restful.client.model.annotation.RequestGet
import com.github.restful.client.model.annotation.RequestHeader
import com.github.restful.client.model.annotation.RequestMapping
import com.github.restful.client.model.annotation.RequestPost
import com.google.common.collect.Maps
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spark.Spark
import spock.lang.Specification

/**
 * Created by wangzhx on 2019/3/19.
 * todo 1. 协议默认是json解析，后面需要支持 字符串类型
 * todo 2. RequestHeader 只能接收Map类型，需支持单个类型
 */
@ContextConfiguration(locations = "classpath:spring/restful-client.xml")
class RequestInvokeDemo extends Specification {

    @Autowired
    HelloClient helloClient

    static {
        Spark.setPort(8080)
        Spark.get("/hello/get", { req, res -> "{\"message\":\"Hello Get\"}" })
        Spark.post("/hello/post", { req, res ->
            def body = req.body()
            print(body)
            return "{\"message\":\"Hello Post\"}"
        })
    }

    @RequestMapping
    interface HelloClient {
        @RequestGet(path = "/hello/get")
        String helloGet(@RequestHeader Map<String, String> headers)

        @RequestPost(path = "/hello/post")
        String helloPost(@RequestHeader Map<String, String> headers, @RequestBody Map<String, Object> body)
    }

    def "hello"() {
        given:
        def get = helloClient.helloGet(Maps.newHashMap())
        println get
        def post = helloClient.helloPost((Maps.newHashMap()), Maps.newHashMap())
        println post

    }
}
