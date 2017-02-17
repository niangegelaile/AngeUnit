package com.ange.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by liquanan on 2016/12/19 0019.
 */
@Module
public final class SharedPreferencesModule {

    private final static String PREFERENCE_NAME="SP_file_name";
    @Singleton
    @Provides
    public SharedPreferences providerSharedPreferences(Context context){
        return context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE);
    }
    @Singleton
    @Provides
    public SharedPreferencesUtil providerSharedPreferencesUtil(SharedPreferences sharedPreferences){
        return new SharedPreferencesUtil(sharedPreferences);
    }

}
