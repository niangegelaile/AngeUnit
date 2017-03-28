package com.ange.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * 数据库
 * Created by Administrator on 2016/10/9.
 */
@Module
public final class DbModule {


    @Provides @Singleton
    public BriteDatabase provideBriteDatabase(SqlBrite sqlBrite, SQLiteOpenHelper openHelper){
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }
    @Provides @Singleton
    public SqlBrite provideSqlBrite() {
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Log.d("SqlBrite",message);
            }
        });
    }


    @Provides @Singleton
    public IDB provideDb(BriteDatabase briteDatabase){
        return new MDb(briteDatabase);
    }
}
