package com.github.restful.client.core

import com.github.restful.client.BaseTest
import org.springframework.test.context.ContextConfiguration

import static spark.Spark.*

/**
 * Created by wangzhx on 2020/3/20.
 */
@ContextConfiguration(locations = "classpath:spring/restful-client.xml")
class Configuration extends BaseTest{
    def setupSpec(){
        port(8080)
        get("/hello/get", { req, res -> "{\"message\":\"Hello Get\"}" })
        post("/hello/post", { req, res ->
            def body = req.body()
            print(body)
            return "{\"message\":\"Hello Post\"}"
        })
    }

    def cleanupSpec(){
        stop()
    }

}
