package com.example.ange.app;

import android.app.Application;

import com.ange.image.DaggerImageComponent;
import com.ange.image.ImageComponent;
import com.ange.image.ImageModule;
import com.ange.image.ImageUtil;
import com.ange.image.MyImageUtil;

import com.baidu.mapapi.SDKInitializer;




/**
 *
 * Created by liquanan on 2016/10/1.
 */
public class MyApplication extends Application {
    public static MyApplication mApplication;

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

        //图片工具
        ImageComponent imageComponent= DaggerImageComponent
                .builder()
                .imageModule(new ImageModule(new MyImageUtil()))
                .build();
        mImageUtil=imageComponent.getImageUtil();
        SDKInitializer.initialize(this);
//        EaseUI.getInstance().init(this, null);
    }
    /**
     * 获取图片加载工具
     * @return
     */
    public ImageUtil getImageUtil(){
        return mImageUtil;
    }
}
