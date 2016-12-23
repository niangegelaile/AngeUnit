package com.example.ange.angeunit.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class MDb implements IDB {

    private BriteDatabase db;

    public MDb(BriteDatabase db){
        this.db=db;
    }

    @Override
    public <T>List<T> find(Class t, String s){
        List<T> result=new ArrayList<>();
        Cursor cursor= db.query(s);
        try {
            Field  fmap= t.getField("MAPPER");//获取静态变量的field
            Object mapperObject=fmap.get(t);//获取静态变量
            Class mapC=mapperObject.getClass();//获取到静态变量的class
            Method method=mapC.getDeclaredMethod("map",Cursor.class);//获取Method
            if(cursor!=null&&cursor.getCount()>0){
                while (cursor.moveToNext()){
                    Object object=method.invoke(mapperObject,cursor);//调用方法
                    result.add((T)object);
                }
                cursor.close();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update(@NonNull String table, @NonNull ContentValues values,
                       @Nullable String whereClause, @Nullable String... whereArgs) {
        db.update(table, values,whereClause, whereArgs);
    }

    @Override
    public void delete(String table,String where,String... args) {
        db.delete(table,where,args);
    }

    @Override
    public long add(String tableName,ContentValues contentValues) {
        return  db.insert(tableName,contentValues);
    }

    @Override
    public BriteDatabase getBriteDatabase() {
        return db;
    }
}
