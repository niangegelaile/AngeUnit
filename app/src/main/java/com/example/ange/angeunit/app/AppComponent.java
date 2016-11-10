package com.example.ange.angeunit.app;

import android.content.Context;

import com.example.ange.angeunit.api.Api;
import com.example.ange.angeunit.module.login.LoginActivity;
import com.example.ange.angeunit.module.login.LoginPresenter;

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
