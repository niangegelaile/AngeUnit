package com.example.ange.angeunit.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import rx.subscriptions.CompositeSubscription;

/**
 * 自定义activity基类
 * Created by liquanan on 2016/10/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription subscriptions;//用于收集订阅

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentForInject();
        subscriptions=new CompositeSubscription();
        acceptIntent(getIntent());
        setSystemBarTint();
    }

    /**
     * 设置沉浸式状态栏
     *
     */
    private void setSystemBarTint(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#00000000"));
    }

    /**
     * 设置沉浸式状态栏
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    /**
     * 处理传递过来的Intent
     * @param intent
     */
    protected abstract void acceptIntent(Intent intent);

    /**
     * 用于生成component注入presenter
     */
    protected abstract void buildComponentForInject();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscriptions!=null&&!subscriptions.isUnsubscribed()){
            subscriptions.unsubscribe();
        }
    }
}
