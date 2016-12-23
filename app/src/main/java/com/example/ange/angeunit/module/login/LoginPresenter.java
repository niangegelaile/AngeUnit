package com.example.ange.angeunit.module.login;

import android.database.Cursor;
import android.text.TextUtils;

import com.example.ange.angeunit.base.RxBus;
import com.example.ange.angeunit.api.Api;
import com.example.ange.angeunit.db.Db;
import com.example.ange.angeunit.db.IDB;
import com.example.ange.angeunit.db.table.Person;
import com.example.ange.angeunit.db.table.PersonAndPosition;
import com.example.ange.angeunit.db.table.Position;
import com.example.ange.angeunit.module.login.bean.TokenBean;
import com.example.ange.angeunit.repository.Repository;
import com.example.ange.angeunit.utils.SubscriptionCollectUtil;
import com.squareup.sqlbrite.BriteDatabase;


import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 登录presenter
 * Created by liquanan on 2016/10/1.
 */
public class LoginPresenter  implements LoginContract.Presenter{

    private final Api api;
    private final IDB mDb;
    private final LoginContract.View mView;

    @Inject//在构造器进行注入
     LoginPresenter(Repository repository, LoginContract.View mView){
        this.api=repository.getApi();
        this.mDb=repository.getDb();
        this.mView=mView;
    }

    /**
     * 该方法会在对象创建后调用
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    public void login(String account,String password){
       Subscription sb= api.login(account,password)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        RxBus.getDefault().post("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        RxBus.getDefault().post(e.toString());
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {

                    }
                });
        SubscriptionCollectUtil.push(sb);
    }


    @Override
    public void queryPersonPosition() {
                mDb.getBriteDatabase().createQuery(Arrays.asList("person","position"),"SELECT * from person a,position b where a.pid = b.pid")
                .mapToList(PersonAndPosition.RXMAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PersonAndPosition>>() {
                    @Override
                    public void call(List<PersonAndPosition> personAndPositions) {
                        mView.setPersonInfoView(personAndPositions);
                    }
                });
    }

    @Override
    public void insertInfo(String name, String position) {
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(position)){
            return;
        }
        List<Position> positions=null;
        positions= mDb.find(Position.class,"select * from position where "+ Position.PNAME+" = "+"\""+position+"\"");
        long pid;
        if(positions==null||positions.size()<1){
            pid=mDb.add(Position.TABLE_NAME,Position.FACTORY.marshal().pname(position).asContentValues());
        }else {
            pid= positions.get(0).pid();
        }
        long id = mDb.add(Person.TABLE_NAME, Person.FACTORY.marshal().name(name).pid(pid).asContentValues());
    }

    @Override
    public void deleteInfo(long id) {
        mDb.delete(Person.TABLE_NAME,Person._ID +" = ?",String.valueOf(id));
    }
}
