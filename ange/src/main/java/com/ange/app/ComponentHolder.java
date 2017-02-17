package com.ange.app;

/**
 * Created by Administrator on 2016/10/1.
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
