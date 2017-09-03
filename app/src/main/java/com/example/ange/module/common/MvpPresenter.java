package com.example.ange.module.common;

import com.ange.SharedPreferences.Sp;
import com.ange.component.ComponentHolder;
import com.ange.db.IDB;
import com.example.ange.api.Api;

/**
 * Created by liquanan on 2017/6/24.
 * email :1369650335@qq.com
 */

public class MvpPresenter  {

    protected final Api api;
    protected final IDB mDb;
    protected  final Sp sp;

    public MvpPresenter(){
        this.api= ComponentHolder.getAppComponent().getRetrofit().create(Api.class);
        this.mDb=ComponentHolder.getAppComponent().getDb();
        this.sp=ComponentHolder.getAppComponent().getSp();
    }
}
