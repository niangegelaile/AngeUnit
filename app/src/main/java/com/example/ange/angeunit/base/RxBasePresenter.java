package com.example.ange.angeunit.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/10/2.
 */
public abstract class RxBasePresenter {
    protected CompositeSubscription mSubscriptions;

    public void subscribe(){

    }


    public void unsubscribe(){
        if(mSubscriptions!=null&&!mSubscriptions.isUnsubscribed()){
            mSubscriptions.unsubscribe();
        }
    }
}
