package com.example.ange.angeunit.api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api dagger module 用于提供网络请求
 * Created by liquanan on 2016/10/9.
 */
@Module
public  final class ApiModule {
    /**
     * 提供okhttp
     * @return
     */
    @Provides
    @Singleton
    public OkHttpClient providerOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 提供json 解析的factory
     * @return
     */
    @Provides
    @Singleton
    public Converter.Factory providerConverterFactory(){
        return GsonConverterFactory.create();
    }

    /**
     * 提供rxjava支持
     * @return
     */
    @Provides
    @Singleton
    public CallAdapter.Factory providerCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }

    /**
     * 提供 网络请求
     * @param okHttpClient
     * @param converFactory
     * @param callAdapterFactory
     * @return
     */
    @Singleton
    @Provides
    public Retrofit providerRetrofit(OkHttpClient okHttpClient, Converter.Factory converFactory, CallAdapter.Factory callAdapterFactory){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(converFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }

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
}
