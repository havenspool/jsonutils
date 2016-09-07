package com.havens.jsonutils.util;

import com.google.gson.Gson;

/**
 * Created by havens on 2016/6/7.
 */
public class GsonUtil {
    public static final Gson gson = new Gson();

    /**
     * json化对象
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJson(Object obj, Class<?> type){
        return gson.toJson(obj, type);
    }

    public static <T> T fromJson(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }

    public static <T> T fromJson(String json, java.lang.reflect.Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
