package com.example.ange.angeunit.api;

import com.example.ange.angeunit.module.login.bean.TokenBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/1.
 */
public interface Api {
    @FormUrlEncoded
    @POST("m/token")
    Observable<TokenBean> login(@Field("mobile") String mobile,@Field("password") String password);
}
