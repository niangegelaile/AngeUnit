package com.example.ange.module.common;

import com.ange.SharedPreferences.SharedPreferencesUtil;
import com.ange.base.BaseView;
import com.ange.db.IDB;
import com.example.ange.api.Api;
import com.example.ange.app.Repository;

/**
 * Created by liquanan on 2017/6/24.
 * email :1369650335@qq.com
 */

public class MvpPresenter  {

    protected final Api api;
    protected final IDB mDb;
    protected  final SharedPreferencesUtil sp;

    public MvpPresenter(Repository repository,BaseView view){
        this.api=repository.getApi();
        this.mDb=repository.getDb();
        this.sp=repository.getSp();
    }



}
