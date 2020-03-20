package com.github.restful.client.utils;

import com.google.gson.*;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;

@UtilityClass
public class GsonUtil {

    /**
     * js中1 与1.0一样的类型（number）
     * 前端转来 1，可能在此方法中转为了 1.0
     * Double longValue() 强转为long类型
     */
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
        if (src == src.longValue()) {
            return new JsonPrimitive(src.longValue());
        }
        return new JsonPrimitive(src);
    }).create();

    /**
     * 传递字段为null
     */
    private static Gson gsonWithNull = new GsonBuilder().serializeNulls().registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
        if (src == src.longValue()) {
            return new JsonPrimitive(src.longValue());
        }
        return new JsonPrimitive(src);
    }).create();

    /**
     * gson格式化
     */
    private static Gson toJsonPrettily = new GsonBuilder().setPrettyPrinting().create();

    /**
     * json 序列化为 对象
     *
     * @param json  json
     * @param clazz 类型
     * @param <T>   范型
     * @return 返回的类型
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(byte[] bytes, Type type) {
        return gson.fromJson(new String(bytes), type);
    }

    /**
     * json文件 序列化为 对象
     *
     * @param fileName 文件名
     * @param clazz    类型
     * @param <T>      范型
     * @return 返回的类型
     * @throws IOException
     */
    public static <T> T fromJsonWhithFile(String fileName, Class<T> clazz) throws IOException {
        return gson.fromJson(IOUtils.toString(GsonUtil.class.getClassLoader().getResourceAsStream(fileName)), clazz);
    }

    public static <T> T fromJsonWithFile(String fileName, Type type) throws IOException {
        return gson.fromJson(IOUtils.toString(GsonUtil.class.getClassLoader().getResourceAsStream(fileName)), type);
    }

    /**
     * 对象 序列化为 json
     *
     * @param object
     * @return jsonString
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * file为null
     *
     * @param object
     * @return
     */
    public static String toJsonWithNull(Object object) {
        return gsonWithNull.toJson(object);
    }

    /**
     * 格式化json
     *
     * @param object
     * @return jsonString
     */
    public static String toPrettyJson(Object object) {
        return toJsonPrettily.toJson(object);
    }
}
