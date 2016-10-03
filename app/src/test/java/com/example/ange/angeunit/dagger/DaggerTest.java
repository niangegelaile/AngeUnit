package com.example.ange.angeunit.dagger;

import android.widget.EditText;

import com.example.ange.angeunit.BuildConfig;
import com.example.ange.angeunit.R;
import com.example.ange.angeunit.dragger.Api;
import com.example.ange.angeunit.module.login.LoginActivity;
import com.example.ange.angeunit.module.login.LoginPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Administrator on 2016/10/3.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class DaggerTest {
    @Test
    public void test_login(){
        TestUtil.setupDagger();
        LoginPresenter mockLoginPresenter= Mockito.mock(LoginPresenter.class);
        Mockito.when(TestUtil.appModule.providerLoginPresenter(any(Api.class))).thenReturn(mockLoginPresenter);
        LoginActivity loginActivity= Robolectric.setupActivity(LoginActivity.class);
        ((EditText)loginActivity.findViewById(R.id.et_account)).setText("13750523051");
        ((EditText)loginActivity.findViewById(R.id.et_pass)).setText("12345678");
        loginActivity.findViewById(R.id.but_login).performClick();
        verify(mockLoginPresenter).login("13750523051","12345678");
    }




}
