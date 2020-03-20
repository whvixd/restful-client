# restful-client
> RPC调用工具

### 请求地址及编码, @see RequestMapping
> 如 @RequestMapping(path = "127.0.0.1:8080", coder = HelloCoderHandler.class)

### Rest请求方式
1. @RequestGet
2. @RequestPost
3. @RequestPut
4. @RequestDelete

> 如 @RequestPost(path = "/hello/post")

### 请求参数
1. @RequestHeader 请求头
2. @RequestPathParam 路径参数
3. @RequestQueryParam query参数
4. @RequestBody 请求体

### 使用方式:
1. 添加Maven依赖

```
<groupId>com.github.whvixd</groupId>
<artifactId>restful-client</artifactId>
<version>1.0-SNAPSHOT</version>
```

2. 添加spring bean配置:

> restful-client.xml

```
    <bean id="requestInvokeTest" class="com.github.restful.client.core.spring.RequestProxyFactoryBean"
              p:clientType="com.github.restful.client.core.RequestInvokeTest"/>
```

### 案例:
@see restful-client/src/test/groovy/com/github/restful/client/core/RequestInvokeTest.groovy