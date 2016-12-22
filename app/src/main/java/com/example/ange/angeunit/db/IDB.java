package com.example.ange.angeunit.db;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public interface IDB {

    <T>List<T> find(Class t, String s) throws IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException;

    void update(String s);

    void delete(String s);

    long add(String tableName,ContentValues contentValues);
}
