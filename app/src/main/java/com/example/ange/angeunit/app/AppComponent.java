package com.example.ange.angeunit.app;

import android.content.Context;

import com.ange.SharedPreferences.SharedPreferencesModule;
import com.ange.api.ApiModule;
import com.ange.db.DbModule;
import com.example.ange.angeunit.module.baidumap.BaiduMapComponent;
import com.example.ange.angeunit.module.baidumap.BaiduMapModule;
import com.example.ange.angeunit.module.login.LoginComponent;
import com.example.ange.angeunit.module.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/10/1.
 */
@Component(modules = {AppModule.class, ApiModule.class, DbModule.class, SharedPreferencesModule.class})
@Singleton
public interface AppComponent {
    Context getContext();
    LoginComponent activityComponent(LoginModule loginModule);
    BaiduMapComponent activityComponent(BaiduMapModule baiduMapModule);

}
