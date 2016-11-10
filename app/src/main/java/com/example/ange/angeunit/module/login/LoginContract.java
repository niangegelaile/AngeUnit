package com.example.ange.angeunit.module.login;

import com.example.ange.angeunit.base.BasePresenter;
import com.example.ange.angeunit.base.BaseView;
import com.example.ange.angeunit.db.table.PersonAndPosition;

import java.util.List;

/**
 * 登录 mvp 契约
 * Created by liquanan on 2016/10/2.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter>{
        /**
         * 显示查询信息
         * @param personAndPositions
         */
        void setPersonInfoView(List<PersonAndPosition> personAndPositions);
    }

    interface Presenter extends BasePresenter{

        /**
         * 查询信息
         */
        void queryPersonPosition();

        /**
         * 插入信息
         * @param name
         * @param pos
         */
        void insertInfo(String name, String pos);

        /**
         * 删除信息
         * @param id
         */
        void deleteInfo(long id);
    }
}
