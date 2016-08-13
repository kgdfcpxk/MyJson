package com.example.myjson.factory;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public class JsonFactory {
    /**
     * 对象转Json
     * @param object
     * @return
     */
    public static String toJson(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * Json转对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json,Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }

    /**
     * Json转泛型集合
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toListBean(String json,Class<T> clazz){
        return JSON.parseArray(json,clazz);
    }
}
