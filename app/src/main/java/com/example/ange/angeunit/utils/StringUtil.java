package com.example.ange.angeunit.utils;

/**
 * Created by Administrator on 2016/10/2.
 */
public class StringUtil {

    public String add(String... s1){
        StringBuilder sb=new StringBuilder();
        for(String s:s1){
            sb.append(s);
        }
       return sb.toString();
    }

    public static String combination(String... s1){
        StringBuilder sb=new StringBuilder();
        for(String s:s1){
            sb.append(s);
        }
        return sb.toString();
    }
}
