package com.example.ange.app;

import android.app.Application;





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
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        ComponentHolder.setAppComponent(appComponent);

    }

}
