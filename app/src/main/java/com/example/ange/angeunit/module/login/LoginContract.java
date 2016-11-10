package com.example.ange.angeunit.module.login;

import com.example.ange.angeunit.base.BasePresenter;
import com.example.ange.angeunit.base.BaseView;
import com.example.ange.angeunit.db.table.PersonAndPosition;

import java.util.List;

/**
 * Created by Administrator on 2016/10/2.
 */
public interface LoginContract {
    interface View extends BaseView<Presenter>{
        void setPersonInfoView(List<PersonAndPosition> personAndPositions);
    }
    interface Presenter extends BasePresenter{
        void queryPersonPosition();
        void insertInfo(String name, String pos);
        void deleteInfo(long id);
    }
}
