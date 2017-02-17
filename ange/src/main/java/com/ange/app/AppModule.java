package com.ange.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * 由dragger构造的提供各种基础类的对象
 * Created by Administrator on 2016/10/1.
 */
@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context context){
        this.mContext=context;
    }

    @Provides
    public Context providerContext() {
        return mContext;
    }
}
