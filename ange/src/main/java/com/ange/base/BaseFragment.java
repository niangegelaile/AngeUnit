package com.ange.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ange.R;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;


/**
 * 功能 rxjava 订阅，取消订阅，页面刷新
 * Created by ange on 2016/12/26 0026.
 */

public abstract class BaseFragment extends Fragment {

    //判断当前页面是否需要刷新
    protected boolean needToFresh;
    //判断当前页面是否显示
    protected boolean isVisibleToUser;
    protected List<Subscription> subscriptions=new ArrayList<>();
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerRefreshPage();
    }

    public void showTip(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showTip(String msg,int code){
        showTip(msg);
    }

    public void load(boolean flag){
        BaseActivity activity= (BaseActivity) getActivity();
        if(activity!=null){
            activity.loading(flag);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscriptions!=null){
            for(Subscription sb:subscriptions){
                if(sb!=null&&!sb.isUnsubscribed()){
                    sb.unsubscribe();
                }
            }
        }

    }
    public void addSubscription(Subscription sb){
        if(subscriptions==null){
            subscriptions=new ArrayList<>();
        }

        subscriptions.add(sb);
    }

    //注册页面刷新
    protected void registerRefreshPage() {

    }
    /**
     * 当切换生活馆时，页面对用户可见就会刷新页面
     */
    protected void refreshPage(){

    }
    /**
     * 收到刷新消息调用这个方法
     */
    private void refresh(){
        needToFresh=true;
        if(isVisibleToUser){
            refreshPage();
            needToFresh=false;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            //TODO 显示
            if(needToFresh){
                refreshPage();
                needToFresh=false;
            }
            isVisibleToUser=true;
        }else {
            //TODO 不显示
            isVisibleToUser=false;
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            //TODO 显示
            if(needToFresh){
                refreshPage();
                needToFresh=false;
            }
            this.isVisibleToUser=true;
        }else {
            //TODO 不显示
            this.isVisibleToUser=false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            //TODO 显示
            if(needToFresh){
                refreshPage();
                needToFresh=false;
            }
            isVisibleToUser=true;
        }else {
            //TODO 不显示
            isVisibleToUser=false;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        //TODO 不显示
        isVisibleToUser=false;
    }


}

