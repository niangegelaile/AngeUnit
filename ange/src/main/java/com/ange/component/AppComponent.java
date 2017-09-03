package com.ange.component;

import android.content.Context;

import com.ange.SharedPreferences.SharedPreferencesModule;
import com.ange.SharedPreferences.Sp;
import com.ange.db.DbModule;
import com.ange.db.IDB;
import com.ange.http.HttpModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * 功能：把资源类放到app组件里，每一个activity组件是app组件的子组件
 *
 * Created by ange on 2016/10/1.
 */
@Component(modules = {AppModule.class, HttpModule.class, DbModule.class, SharedPreferencesModule.class})
@Singleton
public interface AppComponent {
    Context getContext();
    Retrofit getRetrofit();
    IDB getDb();
    Sp getSp();
}
