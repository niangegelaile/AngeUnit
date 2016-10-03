package com.example.ange.angeunit.dagger;

import com.example.ange.angeunit.dragger.AppComponent;
import com.example.ange.angeunit.dragger.AppModule;
import com.example.ange.angeunit.dragger.ComponentHolder;
import com.example.ange.angeunit.dragger.DaggerAppComponent;

import org.robolectric.RuntimeEnvironment;

import static org.mockito.Mockito.spy;
/**
 * Created by Administrator on 2016/10/3.
 */

public class TestUtil {
    public static final AppModule appModule=spy(new AppModule(RuntimeEnvironment.application));

    public static void setupDagger(){
       AppComponent appComponent= DaggerAppComponent.builder().appModule(appModule).build();
        ComponentHolder.setAppComponent(appComponent);
    }




}
