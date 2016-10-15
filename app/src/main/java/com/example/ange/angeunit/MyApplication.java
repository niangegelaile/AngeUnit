package com.example.ange.angeunit;

import android.app.Application;

import com.example.ange.angeunit.app.AppComponent;
import com.example.ange.angeunit.app.AppModule;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.app.DaggerAppComponent;

/**
 * Created by Administrator on 2016/10/1.
 */
public class MyApplication extends Application {
    public static MyApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        ComponentHolder.setAppComponent(appComponent);
    }

}
