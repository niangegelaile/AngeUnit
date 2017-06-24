package com.example.ange.module.login;

import android.text.TextUtils;

import com.ange.base.BaseView;
import com.ange.db.IDB;
import com.example.ange.api.Api;
import com.example.ange.app.Repository;
import com.example.ange.db.Person;
import com.example.ange.db.PersonAndPosition;
import com.example.ange.db.Position;
import com.example.ange.module.common.MvpPresenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 登录presenter
 * Created by liquanan on 2016/10/1.
 */
public class LoginPresenter extends MvpPresenter implements LoginContract.Presenter{


    private final LoginContract.View mView;

    @Inject//在构造器进行注入
    LoginPresenter(Repository repository, BaseView mView){
        super(repository);
        this.mView=(LoginContract.View)mView;
    }

    /**
     * 该方法会在对象创建后调用
     */
    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    public void login(String account,String password){

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
