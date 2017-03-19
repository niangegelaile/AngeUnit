package com.example.ange.module.login;




import android.widget.EditText;

import com.ange.repository.Repository;
import com.example.ange.R;
import com.example.ange.app.AppComponent;
import com.example.ange.app.AppModule;
import com.example.ange.app.ComponentHolder;
import com.example.ange.app.DaggerAppComponent;
import com.example.ange.test.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;

/**
 * Created by liquanan on 2017/3/19.
 * email :1369650335@qq.com
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginActivityTest {

    @Test
    public void testOnCreate(){
        AppModule mockAppModule = Mockito.mock(AppModule.class);
        LoginPresenter mockLoginPresenter = Mockito.mock(LoginPresenter.class);
        LoginModule mockLoginModule=Mockito.mock(LoginModule.class);
        Mockito.when(mockLoginModule.providePresenter(any(Repository.class),any(LoginContract.View.class))).thenReturn(mockLoginPresenter);  //当mockAppModule的provideLoginPresenter()方法被调用时，让它返回mockLoginPresenter
        AppComponent appComponent = DaggerAppComponent.builder().appModule(mockAppModule).build();  //用mockAppModule来创建DaggerAppComponent
        ComponentHolder.setAppComponent(appComponent);  //假设你的Component是放在ComponentHolder里面的

//        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
//        ((EditText) loginActivity.findViewById(R.id.et_account)).setText("xiaochuang");
//        ((EditText) loginActivity.findViewById(R.id.et_pass)).setText("xiaochuang is handsome");
//        loginActivity.findViewById(R.id.but_login).performClick();
//
//        Mockito.verify(mockLoginPresenter).login("xiaochuang", "xiaochuang is handsome");




    }



}
