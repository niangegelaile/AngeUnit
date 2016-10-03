package com.example.ange.angeunit.dragger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.ange.angeunit.db.DbOpenHelper;
import com.example.ange.angeunit.module.login.LoginPresenter;
import com.squareup.sqlbrite.SqlBrite;

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
 * 由dragger构造的提供各种基础类的对象
 * Created by Administrator on 2016/10/1.
 */
@Module
public class AppModule {

    private final Context mContext;

    public AppModule(Context context){
        this.mContext=context;
    }

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
    public Retrofit providerRetrofit(OkHttpClient okHttpClient,Converter.Factory converFactory,CallAdapter.Factory callAdapterFactory){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("")
                .addConverterFactory(converFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }

    @Singleton
    @Provides
    public Api providerApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    public SharedPreferences providerSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public Context providerContext() {
        return mContext;
    }

    @Provides
    public LoginPresenter providerLoginPresenter(Api api){
        return new LoginPresenter(api);
    }

    @Singleton
    @Provides
    public DbOpenHelper  providerDbOpenHelper(Context context){
        return new DbOpenHelper(context);
    }

    @Provides @Singleton
    public SqlBrite provideSqlBrite() {
        return SqlBrite.create(new SqlBrite.Logger() {
            @Override
            public void log(String message) {
                Log.d("SqlBrite",message);
            }
        });
    }
}
