package com.example.ange.angeunit;

import android.app.Application;

import com.example.ange.angeunit.api.ApiModule;
import com.example.ange.angeunit.app.AppComponent;
import com.example.ange.angeunit.app.AppModule;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.app.DaggerAppComponent;
import com.example.ange.angeunit.db.DbModule;
import com.example.ange.angeunit.repository.DaggerRepositoryComponent;
import com.example.ange.angeunit.repository.RepositoryComponent;

/**
 *
 * Created by liquanan on 2016/10/1.
 */
public class MyApplication extends Application {
    public static MyApplication mApplication;
    private RepositoryComponent mRepositoryComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        ComponentHolder.setAppComponent(appComponent);
        mRepositoryComponent= DaggerRepositoryComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .dbModule(new DbModule()).build();
    }

    public RepositoryComponent getmRepositoryComponent() {
        return mRepositoryComponent;
    }
}
