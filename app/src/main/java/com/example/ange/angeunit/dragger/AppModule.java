package com.example.ange.angeunit.dragger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ange.angeunit.api.Api;
import com.example.ange.angeunit.api.ApiModule;
import com.example.ange.angeunit.db.DbModule;
import com.example.ange.angeunit.module.login.LoginPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 由dragger构造的提供各种基础类的对象
 * Created by Administrator on 2016/10/1.
 */
@Module(
        includes = {DbModule.class, ApiModule.class}
)
public class AppModule {

    private final Context mContext;

    public AppModule(Context context){
        this.mContext=context;
    }

    @Singleton
    @Provides
    public SharedPreferences providerSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public Context providerContext() {
        return mContext;
    }

    @Provides
    public LoginPresenter providerLoginPresenter(Api api){
        return new LoginPresenter(api);
    }

}
