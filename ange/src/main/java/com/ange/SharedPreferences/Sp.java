package com.ange.SharedPreferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sharePreferences工具类
 * Created by Administrator on 2016/12/19 0019.
 */

public class Sp {
    public static final String USER="USER";
    public static final String DepartmentBean="DepartmentBean";
    public static  final String LifeHouseBean="LifeHouseBean";
    private SharedPreferences mSharedPreferences;

    public Sp(SharedPreferences mSharedPreferences){
        this.mSharedPreferences=mSharedPreferences;
    }

    /**
     * 提取储存数据对象
     * @param key
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public  <T> T getObjectFromShare(@NonNull String key) {
        try {
            String payCityMapBase64 = mSharedPreferences.getString(key, "");
            if (payCityMapBase64.length() == 0) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(payCityMapBase64, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象到sp公共方法,注：若要使用这个方法bean类请务必继承 Serializable接口进行数列化 add by ssy
     * 2014.09.07
     * @param key
     * @param t
     * @return
     */

    public <T> boolean saveObjectToShare(@NonNull String key,@NonNull T t) {
        try {
            // 存储
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            ByteArrayOutputStream toByte = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(toByte);
            oos.writeObject(t);
            // 对byte[]进行Base64编码
            String payCityMapBase64 = new String(Base64.encode(toByte.toByteArray(), Base64.DEFAULT));
            editor.putString(key, payCityMapBase64);
           return editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存基本类型的数据
     * @param key
     * @param value
     * @return
     */
    public boolean saveNormalInfo(String key,Object value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if(value instanceof String){
            editor.putString(key, (String) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }

        return editor.commit();
    }

    /**
     * 删除信息
     * @param key
     * @return
     */
    public boolean removeInfo(String key){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public SharedPreferences getSharedPreferences(){
        return  mSharedPreferences;
    }

}
