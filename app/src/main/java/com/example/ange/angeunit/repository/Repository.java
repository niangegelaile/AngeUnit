package com.example.ange.angeunit.repository;

import com.example.ange.angeunit.SharedPreferences.SharedPreferencesUtil;
import com.example.ange.angeunit.api.Api;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
@Singleton
public class Repository {

    private Api mApi;

    private BriteDatabase mDb;

    private SharedPreferencesUtil mSp;
    @Inject
    public Repository(Api mApi, BriteDatabase mDb,SharedPreferencesUtil mSp) {
        this.mApi = mApi;
        this.mDb = mDb;
        this.mSp=mSp;
    }

    public Api getApi() {
        return mApi;
    }

    public BriteDatabase getDb() {
        return mDb;
    }

    public SharedPreferencesUtil getSp(){
        return mSp;
    }





}
