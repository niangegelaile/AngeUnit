package com.example.ange.angeunit.dagger;

import com.example.ange.angeunit.app.AppComponent;
import com.example.ange.angeunit.app.AppModule;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.app.DaggerAppComponent;

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
