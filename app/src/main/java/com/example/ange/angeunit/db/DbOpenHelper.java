package com.example.ange.angeunit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ange.angeunit.db.table.Person;
import com.example.ange.angeunit.db.table.Position;
import com.example.ange.angeunit.module.login.LoginContract;
import com.example.ange.angeunit.utils.StringUtil;

/**
 * Created by Administrator on 2016/10/3.
 */
public final class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 4;


    public DbOpenHelper(Context context) {
        super(context, "token.db", null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Position.CREATE_TABLE);
        db.execSQL(Person.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("alter table "+Person.TABLE_NAME+" drop column "+"phone");sqlite不支持删除列
        db.execSQL("create table person2 as select _id, name, pid from "+Person.TABLE_NAME);//先创建一个备份
        db.execSQL("drop table if exists "+Person.TABLE_NAME);//把旧的表删掉
        db.execSQL("alter table person2 rename to "+Person.TABLE_NAME );//把备份更名为原来表的命
        db.execSQL("alter table person add column phone TEXT");
//        onCreate(db);
    }
}
