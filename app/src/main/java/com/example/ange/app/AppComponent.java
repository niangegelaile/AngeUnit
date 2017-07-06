package com.example.ange.app;

import android.content.Context;

import com.ange.SharedPreferences.SharedPreferencesModule;
import com.ange.db.DbModule;
import com.ange.http.HttpModule;
import com.example.ange.api.ApiModule;
import com.example.ange.db.DbOpenModule;
import com.example.ange.module.common.MvpComponent;
import com.example.ange.module.common.MvpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 功能：把资源类放到app组件里，每一个activity组件是app组件的子组件
 *
 * Created by ange on 2016/10/1.
 */
@Component(modules = {AppModule.class, HttpModule.class, ApiModule.class, DbModule.class, SharedPreferencesModule.class, DbOpenModule.class})
@Singleton
public interface AppComponent {
    Context getContext();
    MvpComponent activityComponent(MvpModule mvpModule);
}
