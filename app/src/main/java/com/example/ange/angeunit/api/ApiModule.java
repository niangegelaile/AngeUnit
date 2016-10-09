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
 * Created by Administrator on 2016/10/9.
 */
@Module
public  final class ApiModule {
    @Provides
    @Singleton
    public OkHttpClient providerOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Converter.Factory prividerConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public CallAdapter.Factory prividerCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }

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

    @Singleton
    @Provides
    public Api providerApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }






}
