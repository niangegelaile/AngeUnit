package com.example.ange.angeunit.module.login;

import com.example.ange.angeunit.repository.RepositoryComponent;
import com.example.ange.angeunit.utils.ActivityScope;

import dagger.Component;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
@ActivityScope
@Component(dependencies = RepositoryComponent.class,
        modules = LoginModule.class)
public interface LoginComponent {
        void inject(LoginActivity loginActivity);
}
