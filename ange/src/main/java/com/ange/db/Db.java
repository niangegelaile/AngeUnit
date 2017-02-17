package com.ange.db;

import android.database.Cursor;

/**
 * 用于便捷读取字段
 * Created by Administrator on 2016/10/3.
 */
public final class Db {
    public final static int BOOLEAN_FALSE=0;
    public final static int BOOLEAN_TRUE=1;

    public static String getString(Cursor cursor,String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }
    private Db(){throw new AssertionError("No instance!");}
}
