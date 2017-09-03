package com.example.ange.app;

import android.app.Application;

import com.ange.component.ComponentHolder;
import com.baidu.mapapi.SDKInitializer;
import com.example.ange.db.DbOpenHelper;


/**
 *
 * Created by liquanan on 2016/10/1.
 */
public class MyApplication extends Application {
    public static MyApplication mApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        ComponentHolder.init(this,new DbOpenHelper(this),"https://www.baidu.com");
        SDKInitializer.initialize(this);

    }

}
