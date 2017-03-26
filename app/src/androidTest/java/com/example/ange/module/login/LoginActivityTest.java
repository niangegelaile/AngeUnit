package com.example.ange.module.login;




import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ange.R;
import com.example.ange.app.AppComponent;
import com.example.ange.app.AppModule;
import com.example.ange.app.ComponentHolder;
import com.example.ange.app.DaggerAppComponent;
import com.example.ange.app.MyApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by liquanan on 2017/3/19.
 * email :1369650335@qq.com
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Test
    public void testOnCreate(){
//        AppModule mockAppModule = Mockito.mock(AppModule.class);
//        LoginPresenter mockLoginPresenter = Mockito.mock(LoginPresenter.class);
//        LoginModule mockLoginModule=Mockito.mock(LoginModule.class);
//        Mockito.when(mockLoginModule.providePresenter(any(Repository.class),any(LoginContract.View.class))).thenReturn(mockLoginPresenter);  //当mockAppModule的provideLoginPresenter()方法被调用时，让它返回mockLoginPresenter
//        AppComponent appComponent = DaggerAppComponent.builder().appModule(mockAppModule).build();  //用mockAppModule来创建DaggerAppComponent
//        ComponentHolder.setAppComponent(appComponent);  //假设你的Component是放在ComponentHolder里面的
//
//        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
//        ((EditText) loginActivity.findViewById(R.id.et_account)).setText("xiaochuang");
//        ((EditText) loginActivity.findViewById(R.id.et_pass)).setText("xiaochuang is handsome");
//        loginActivity.findViewById(R.id.but_login).performClick();
//
//        Mockito.verify(mockLoginPresenter).login("xiaochuang", "xiaochuang is handsome");

//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.example.ange", appContext.getPackageName());


    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

//    @Before
//    public void setUp() throws Exception {
//        AppComponent component = DaggerAppComponent.builder().appModule(new AppModule(getAppFromInstrumentation())).build();
//
//        ComponentHolder.setAppComponent(component);
//
//
//    }

    private MyApplication getAppFromInstrumentation() {
        return (MyApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }


    private static final String STRING_TO_BE_TYPED = "Peter";

    @Test
    public  void testLogin(){
        onView(withId(R.id.et_account)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.et_pass)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.but_login)).perform(click());
    }







}
