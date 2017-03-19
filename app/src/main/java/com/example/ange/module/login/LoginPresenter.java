package com.example.ange.module.login;

import android.text.TextUtils;

import com.ange.api.Api;
import com.ange.db.IDB;
import com.ange.db.table.Person;
import com.ange.db.table.PersonAndPosition;
import com.ange.db.table.Position;
import com.ange.repository.Repository;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 登录presenter
 * Created by liquanan on 2016/10/1.
 */
public class LoginPresenter  implements LoginContract.Presenter{

    private final Api api;
    private final IDB mDb;
    private final LoginContract.View mView;

//    @Inject//在构造器进行注入
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
