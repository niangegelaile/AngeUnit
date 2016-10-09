package com.example.ange.angeunit.module.login;

import com.example.ange.angeunit.base.RxBasePresenter;
import com.example.ange.angeunit.base.RxBus;
import com.example.ange.angeunit.api.Api;
import com.example.ange.angeunit.module.login.bean.TokenBean;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/10/1.
 */
public class LoginPresenter extends RxBasePresenter implements LoginContract.Presenter{

    private final Api api;
    private CompositeSubscription subscriptions;
    public LoginPresenter(Api api){
        this.api=api;
        subscriptions=new CompositeSubscription();
    }



    public void login(String account,String password){
       Subscription sb= api.login(account,password)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        RxBus.getDefault().post("onCompleted");
//                        Toast.makeText(MyApplication.mApplication,"onCompleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxBus.getDefault().post(e.toString());
//                        Toast.makeText(MyApplication.mApplication,"onError",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {

                    }
                });
        subscriptions.add(sb);

    }

    public String getVerCode(){
        return "I am ange";
    }





}
