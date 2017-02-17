package com.example.ange.angeunit;

import android.app.Application;

import com.ange.api.ApiModule;
import com.ange.app.AppComponent;
import com.ange.app.AppModule;
import com.ange.app.ComponentHolder;
import com.ange.app.DaggerAppComponent;
import com.ange.db.DbModule;
import com.ange.image.DaggerImageComponent;
import com.ange.image.ImageComponent;
import com.ange.image.ImageModule;
import com.ange.image.ImageUtil;
import com.ange.image.MyImageUtil;
import com.ange.repository.DaggerRepositoryComponent;
import com.ange.repository.RepositoryComponent;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.easeui.controller.EaseUI;



/**
 *
 * Created by liquanan on 2016/10/1.
 */
public class MyApplication extends Application {
    public static MyApplication mApplication;
    private RepositoryComponent mRepositoryComponent;
    private ImageUtil mImageUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        ComponentHolder.setAppComponent(appComponent);
        //资源管理
        mRepositoryComponent= DaggerRepositoryComponent
                .builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .dbModule(new DbModule()).build();
        //图片工具
        ImageComponent imageComponent= DaggerImageComponent
                .builder()
                .imageModule(new ImageModule(new MyImageUtil()))
                .build();
        mImageUtil=imageComponent.getImageUtil();
        SDKInitializer.initialize(this);
        EaseUI.getInstance().init(this, null);
    }

    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryComponent;
    }

    /**
     * 获取图片加载工具
     * @return
     */
    public ImageUtil getImageUtil(){
        return mImageUtil;
    }
}
