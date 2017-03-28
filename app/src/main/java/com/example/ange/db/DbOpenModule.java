package com.example.ange.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
@Module
public final class DbOpenModule {

    @Singleton
    @Provides
    public SQLiteOpenHelper providerDbOpenHelper(Context context){
        return new DbOpenHelper(context);
    }


}
