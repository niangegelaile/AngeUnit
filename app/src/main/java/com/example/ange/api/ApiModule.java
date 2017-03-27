package com.example.ange.api;

import com.example.ange.BuildConfig;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by liquanan on 2017/3/27.
 * email :1369650335@qq.com
 */
@Module
public class ApiModule {




    /**
     * 提供api
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    public Api providerApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    public String provideBaseUrl(){
        return "http://192.168.1.166:8080/employee/";
    }




}
