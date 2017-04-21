package com.zfy.simplemall.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by ZFY on 2017/04/21.
 *
 * @function:Json数据的工具类
 */

public class JsonUtils {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public static Gson getGson() {
        return gson;
    }


    public static <T> T fromJson(String json, Class<T> clz) {

        return gson.fromJson(json, clz);
    }


    public static <T> T fromJson(String json, Type type) {

        return gson.fromJson(json, type);
    }


    public static String toJSON(Object object) {

        return gson.toJson(object);
    }
}
