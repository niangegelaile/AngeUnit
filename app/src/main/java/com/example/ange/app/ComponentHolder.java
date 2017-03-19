package com.example.ange.app;

/**
 * 把app组件放到静态内存，方便取出使用
 * Created by ange on 2016/10/1.
 */
public class ComponentHolder {
    private static AppComponent mAppComponent;
    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        mAppComponent = appComponent;
    }
}
