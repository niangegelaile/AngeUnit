package com.ange.db;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

/**
 * db接口层
 * Created by Administrator on 2016/12/22 0022.
 */

public interface IDB {

    <T>List<T> find(Class t, String s) ;

    void update(@NonNull String table, @NonNull ContentValues values,
                @Nullable String whereClause, @Nullable String... whereArgs);

    void delete(String table, String where, String... args);

    long add(String tableName, ContentValues contentValues);

    BriteDatabase getBriteDatabase();
}
