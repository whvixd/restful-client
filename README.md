# restful-client
提供简单Rest调用注解

### 请求Ip地址
1. @RequestMapping

### Rest请求方式
1. @RequestGet
2. @RequestPost
3. @RequestPut
4. @RequestDelete

### 请求参数
1. @RequestHeader
2. @RequestPathParam
3. @RequestQueryParam

### 使用方式:
1. 添加Maven依赖

```
<groupId>com.github.whvixd</groupId>
<artifactId>restful-client</artifactId>
<version>1.0-SNAPSHOT</version>
```

2. 添加spring bean配置:

```
    <bean id="requestInvokeTest" class="com.github.restful.client.core.RequestProxyFactoryBean"
              p:clientType="com.github.restful.client.core.RequestInvokeTest"/>
```