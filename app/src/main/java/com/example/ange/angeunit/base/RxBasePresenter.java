package com.example.ange.angeunit.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.subscriptions.CompositeSubscription;

/**
 * email :
 * Created by liquanan on 2016/10/2.
 */
public abstract class RxBasePresenter<T> {

    protected CompositeSubscription mSubscriptions;

    protected Reference<T> mViewReference;

    /**
     * 与view绑定
     * @param view
     */
    public void attachView(T view){
        mViewReference=new WeakReference<T>(view);

    }

    /**
     * 与view解绑
     */
    public void detachView(){
        if(mViewReference!=null){
            mViewReference.clear();
            mViewReference=null;
        }
    }

    /**
     * 是否处于绑定状态
     * @return
     */
    public boolean isViewAttached(){
        return mViewReference!=null&&mViewReference.get()!=null;
    }

    /**
     * rxjava 订阅状态解除
     */
    public void unsubscribe(){
        if(mSubscriptions!=null&&!mSubscriptions.isUnsubscribed()){
            mSubscriptions.unsubscribe();
        }
    }
}
