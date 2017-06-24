package com.example.ange.module.common;


import com.example.ange.module.login.LoginActivity;

import dagger.Subcomponent;


@Subcomponent(
        modules = MvpModule.class)
public interface MvpComponent {
    void inject(LoginActivity activity);
}