package com.example.ange.module.baidumap;

import android.util.Log;


import com.baidu.trace.LBSTraceClient;
import com.example.ange.module.common.MvpPresenter;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class BaiduMapPresenter extends MvpPresenter implements BaiduMapContract.Presenter {
    private final BaiduMapContract.View mView;
    private LBSTraceClient mLbsTraceClient;

    BaiduMapPresenter(BaiduMapContract.View mView) {
        super();
        this.mView = mView;
    }
    /**
     * 该方法会在对象创建后调用
     */
    void setupListeners() {
        mView.setPresenter(this);
    }

    void setLbsTraceClient(LBSTraceClient mLbsTraceClient){
        this.mLbsTraceClient=mLbsTraceClient;
        Log.d("LBSTraceClient","hascode BaiduMapPresenter:"+mLbsTraceClient.hashCode());
    }



}