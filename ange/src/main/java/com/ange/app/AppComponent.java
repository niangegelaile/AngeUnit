package com.ange.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/10/1.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    Context getContext();
}
