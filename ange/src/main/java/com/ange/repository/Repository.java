package com.ange.repository;


import com.ange.SharedPreferences.SharedPreferencesUtil;
import com.ange.api.Api;
import com.ange.db.IDB;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
@Singleton
public class Repository {

    private Api mApi;

    private IDB mDb;

    private SharedPreferencesUtil mSp;
    @Inject
    public Repository(Api mApi, IDB mDb,SharedPreferencesUtil mSp) {
        this.mApi = mApi;
        this.mDb = mDb;
        this.mSp=mSp;
    }

    public Api getApi() {
        return mApi;
    }

    public IDB getDb() {
        return mDb;
    }

    public SharedPreferencesUtil getSp(){
        return mSp;
    }





}
