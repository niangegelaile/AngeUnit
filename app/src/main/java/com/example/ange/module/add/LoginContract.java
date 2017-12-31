package com.example.ange.module.add;


import com.ange.base.BasePresenter;
import com.ange.base.BaseView;
import com.example.ange.db.PersonAndPosition;

import java.util.List;

/**
 * 登录 mvp 契约
 * Created by liquanan on 2016/10/2.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {
        /**
         * 显示查询信息
         * @param personAndPositions
         */
        void setPersonInfoView(List<PersonAndPosition> personAndPositions);
    }

    interface Presenter extends BasePresenter {

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
