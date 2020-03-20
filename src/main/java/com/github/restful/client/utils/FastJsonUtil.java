package com.github.restful.client.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by wangzhx on 2018/7/26.
 */
@UtilityClass
public class FastJsonUtil {
    public String toJsonString(Object object) {
        return JSONObject.toJSONString(object);
    }

    public String toJsonStringWithNull(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    public byte[] toJson(Object object) {
        return JSONObject.toJSONBytes(object);
    }

    public byte[] toJsonWithNull(Object object) {
        return JSONObject.toJSONBytes(object, SerializerFeature.WriteMapNullValue);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    /**
     * @param json json字符串
     * @param type 序列化的实体类
     * @param <T>  类型
     * @return 类
     */
    public <T> T fromJson(String json, Type type) {
        return JSONObject.parseObject(json, type,
                Feature.SupportNonPublicField, Feature.AllowComment, Feature.InitStringFieldAsEmpty, Feature.DisableFieldSmartMatch);
    }

    /**
     * 支持范型json转成bean
     *
     * @param json json字符串
     * @param <T>  类型
     * @return 类
     */
    @Deprecated
    public <T> T fromJson(String json) {
        return JSONObject.parseObject(json, new TypeReference<T>() {
                }.getType(),
                Feature.SupportNonPublicField, Feature.AllowComment, Feature.InitStringFieldAsEmpty, Feature.DisableFieldSmartMatch);
    }

    public <T> T fromJson(byte[] bytes, Type type) {
        return JSONObject.parseObject(bytes, type,
                Feature.SupportNonPublicField, Feature.AllowComment, Feature.InitStringFieldAsEmpty, Feature.DisableFieldSmartMatch);
    }

    /**
     * String json = "[\"a\",\"b\",\"c\"]";
     * List stringList = FastjsonUtil.fromArrayJson(json,String.class);
     *
     * @param arrayJson 数组类型json
     * @param clazz     序列化的实体类
     * @param <T>       类型
     * @return List
     */
    public <T> List<T> fromArrayJson(String arrayJson, Class<T> clazz) {
        return JSONArray.parseArray(arrayJson, clazz);
    }

    /**
     * @param arrayJson 数组类型json
     * @param type      序列化的实体类
     * @return List
     */
    public List fromArrayJson(String arrayJson, Type... type) {
        return JSONArray.parseArray(arrayJson, type);
    }
}
