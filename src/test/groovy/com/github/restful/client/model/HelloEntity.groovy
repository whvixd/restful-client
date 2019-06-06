package com.github.restful.client.model

import spock.lang.Specification

/**
 * Created by wangzhx on 2019/3/19.
 */
class HelloEntity extends Specification{

    static {
        setPort(9999)
        get("/hello", { req, res -> "Hello World" })
        post("/helloParam", { req, res ->
            def body = req.body()
            print(body)
            return "Hello Param"
        })
    }
}
