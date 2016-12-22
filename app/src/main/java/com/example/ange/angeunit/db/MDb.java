package com.example.ange.angeunit.db;

import android.content.ContentValues;
import android.database.Cursor;

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
    public <T>List<T> find(Class t, String s) throws IllegalAccessException, InstantiationException,
            NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        List<T> result=new ArrayList<>();
        Cursor cursor= db.query(s);
        Object tableO=t.newInstance();
        Field  fmap= t.getField("MAPPER");
        Object mapperObject=fmap.get(tableO);
        Class mapC=fmap.getClass();
        Method method=mapC.getDeclaredMethod("map",Cursor.class);
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Object object=method.invoke(mapperObject,cursor);
                result.add((T)object);
            }
        }
        method.invoke(mapperObject,cursor);
        return result;
    }

    @Override
    public void update(String s) {
        db.execute(s);
    }

    @Override
    public void delete(String s) {
        db.execute(s);
    }

    @Override
    public long add(String tableName,ContentValues contentValues) {
        return  db.insert(tableName,contentValues);
    }
}
