package com.example.ange.angeunit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ange.angeunit.utils.StringUtil;

/**
 * Created by Administrator on 2016/10/3.
 */
public final class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String CREATE_ACCOUNT=
            StringUtil.combination("CREATE TABLE ",Account.TABLE_NAME,"("
                    ,Account.ID," INTEGER NOT NULL PRIMARY KEY,"
                    ,Account.MOB," TEXT NOT NULL,"
                    , Account.PASS," TEXT NOT NULL"
            ,")");
    public DbOpenHelper(Context context) {
        super(context, "token.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
