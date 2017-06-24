package com.example.ange.module.common;

import com.ange.base.BaseView;
import com.example.ange.module.baidumap.BaiduMapContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
@Module
public class MvpModule {

    private final BaseView mView;


    public MvpModule(BaseView mView) {
        this.mView = mView;

    }

    @Provides
    BaseView provideView() {
        return mView;
    }

}