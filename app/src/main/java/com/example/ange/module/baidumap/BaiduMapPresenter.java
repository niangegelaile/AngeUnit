package com.example.ange.module.baidumap;

import android.util.Log;

import com.ange.base.BaseView;
import com.baidu.trace.LBSTraceClient;
import com.example.ange.app.Repository;
import com.example.ange.module.common.MvpPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class BaiduMapPresenter extends MvpPresenter implements BaiduMapContract.Presenter {


    private final BaiduMapContract.View mView;
    private LBSTraceClient mLbsTraceClient;
    @Inject
    public BaiduMapPresenter(Repository repository,BaseView view) {
        super(repository,view);
        this.mView= (BaiduMapContract.View) view;
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