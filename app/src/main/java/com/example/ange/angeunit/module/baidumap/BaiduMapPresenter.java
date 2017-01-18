package com.example.ange.angeunit.module.baidumap;

import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.example.ange.angeunit.SharedPreferences.SharedPreferencesUtil;
import com.example.ange.angeunit.api.Api;
import com.example.ange.angeunit.db.IDB;
import com.example.ange.angeunit.repository.Repository;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class BaiduMapPresenter implements BaiduMapContract.Presenter {

    private final Api api;
    private final IDB mDb;
    private final SharedPreferencesUtil mSp;
    private final BaiduMapContract.View mView;
    private LBSTraceClient mLbsTraceClient;
    @Inject
    //在构造器进行注入
    BaiduMapPresenter(Repository repository, BaiduMapContract.View mView) {
        this.api = repository.getApi();
        this.mDb = repository.getDb();
        this.mSp = repository.getSp();
        this.mView = mView;
    }

    /**
     * 该方法会在对象创建后调用
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }


    void setLbsTraceClient(LBSTraceClient mLbsTraceClient){
        this.mLbsTraceClient=mLbsTraceClient;
        Log.d("LBSTraceClient","hascode BaiduMapPresenter:"+mLbsTraceClient.hashCode());
    }












}