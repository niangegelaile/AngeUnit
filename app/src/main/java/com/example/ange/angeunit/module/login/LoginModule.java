package com.example.ange.angeunit.module.login;



import dagger.Module;
import dagger.Provides;

/**
 * Created by liquanan on 2016/11/10.
 * email :1369650335@qq.com
 */
@Module
public class LoginModule {

    private final LoginContract.View mView;


    public LoginModule(LoginContract.View mView) {
        this.mView = mView;

    }

    @Provides
    LoginContract.View provideView(){
        return mView;
    }

}
