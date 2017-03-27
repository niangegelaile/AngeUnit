package com.example.ange.module.login;





import com.example.ange.app.Repository;

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

    @Provides
    LoginPresenter providePresenter(Repository repository, LoginContract.View view){

        return new LoginPresenter(repository,view);
    }

}
