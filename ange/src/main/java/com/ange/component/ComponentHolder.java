package com.ange.component;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.ange.SharedPreferences.SharedPreferencesModule;
import com.ange.db.DbModule;
import com.ange.http.HttpModule;

/**
 * 把app组件放到静态内存，方便取出使用
 * Created by ange on 2016/10/1.
 */
public class ComponentHolder {
    private static AppComponent mAppComponent;
    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }

    public static void init(Context context, SQLiteOpenHelper sqLiteOpenHelper,String baseUrl){
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(context))
                .dbModule(new DbModule(sqLiteOpenHelper))
                .httpModule(new HttpModule(baseUrl))
                .sharedPreferencesModule(new SharedPreferencesModule())
                .build();
        ComponentHolder.setAppComponent(appComponent);
    }





}
