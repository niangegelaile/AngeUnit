package com.example.ange.angeunit;

import android.app.Application;

import com.example.ange.angeunit.api.ApiModule;
import com.example.ange.angeunit.app.AppComponent;
import com.example.ange.angeunit.app.AppModule;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.app.DaggerAppComponent;
import com.example.ange.angeunit.db.DbModule;
import com.example.ange.angeunit.image.DaggerImageComponent;
import com.example.ange.angeunit.image.ImageComponent;
import com.example.ange.angeunit.image.ImageModule;
import com.example.ange.angeunit.image.ImageUtil;
import com.example.ange.angeunit.image.MyGilde;
import com.example.ange.angeunit.repository.DaggerRepositoryComponent;
import com.example.ange.angeunit.repository.RepositoryComponent;

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
                .imageModule(new ImageModule(new MyGilde()))
                .build();
        mImageUtil=imageComponent.getImageUtil();
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
