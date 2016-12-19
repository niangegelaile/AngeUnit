package com.example.ange.angeunit.repository;

import com.example.ange.angeunit.SharedPreferences.SharedPreferencesModule;
import com.example.ange.angeunit.api.ApiModule;
import com.example.ange.angeunit.app.AppModule;
import com.example.ange.angeunit.db.DbModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DbModule.class, SharedPreferencesModule.class})
public interface RepositoryComponent {
    Repository getRepository();
}
