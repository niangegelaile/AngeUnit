package com.example.ange.angeunit.module.login;


import dagger.Subcomponent;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */

@Subcomponent(
        modules = LoginModule.class)
public interface LoginComponent {
        void inject(LoginActivity loginActivity);
}
