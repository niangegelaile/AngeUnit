package com.example.ange.angeunit.repository;

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
    @Inject
    public Repository(Api mApi, BriteDatabase mDb) {
        this.mApi = mApi;
        this.mDb = mDb;
    }

    public Api getmApi() {
        return mApi;
    }

    public void setmApi(Api mApi) {
        this.mApi = mApi;
    }

    public BriteDatabase getmDb() {
        return mDb;
    }

    public void setmDb(BriteDatabase mDb) {
        this.mDb = mDb;
    }
}
