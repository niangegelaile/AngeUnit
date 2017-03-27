package com.example.ange.api;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 请求接口
 * Created by Administrator on 2016/10/1.
 */
public interface Api {





    @FormUrlEncoded
    @POST(HttpUrls.UPDATE_PASSWORD)
    Observable<ApiResponse> updatePassword(@Field("p") String jsonParam);
}
