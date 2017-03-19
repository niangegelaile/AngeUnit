package com.example.ange.module.login;


import dagger.Subcomponent;

/**
 * activity子组件
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */

@Subcomponent(
        modules = LoginModule.class)
public interface LoginComponent {
        void inject(LoginActivity loginActivity);

}
