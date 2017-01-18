package com.example.ange.angeunit.module.baidumap;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
@Module
public class BaiduMapModule {

    private final BaiduMapContract.View mView;


    public BaiduMapModule(BaiduMapContract.View mView) {
        this.mView = mView;

    }

    @Provides
    BaiduMapContract.View provideView() {
        return mView;
    }

}