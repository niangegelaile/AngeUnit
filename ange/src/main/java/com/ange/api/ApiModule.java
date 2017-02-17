package com.ange.api;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        if (true) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient mOkHttpClient=builder
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .build();
        return mOkHttpClient;
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
                .baseUrl("http://192.168.1.166:8080/employee/")
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
