package com.ange.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonService {

    public static <T> T parseJson(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("解析json失败");
        }
        return t;
    }
    public static <T> T parseJson(String jsonString, Type clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("解析json失败");
        }
        return t;
    }
    public static String  parseString(Object o){
        Gson gson = new Gson();
        return  gson.toJson(o);
    }
}
