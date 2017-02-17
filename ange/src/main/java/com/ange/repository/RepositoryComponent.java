package com.ange.repository;


import android.app.Activity;

import com.ange.SharedPreferences.SharedPreferencesModule;
import com.ange.api.ApiModule;
import com.ange.app.AppModule;
import com.ange.db.DbModule;

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
    void inject(Activity activity);
}
