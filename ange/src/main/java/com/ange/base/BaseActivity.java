package com.ange.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ange.R;
import com.ange.utils.SubscriptionCollectUtil;
import com.ange.widget.CustomProgressDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 自定义activity基类
 * Created by liquanan on 2016/10/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected SystemBarTintManager tintManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentForInject();
        acceptIntent(getIntent());
        setSystemBarTint(true);
    }

    /**
     * 设置沉浸式状态栏
     *
     */
    protected void setSystemBarTint(boolean flag){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(flag);
        }
        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(flag);
        tintManager.setNavigationBarTintEnabled(flag);
        tintManager.setTintResource(R.color.colorPrimaryDark);
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


    public void showTip(String msg){Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();}


    @Override
    protected void onDestroy() {
        if(isPop()){
            SubscriptionCollectUtil.popAll();
        }
        super.onDestroy();

    }

    protected boolean isPop(){
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,
                R.anim.alpha_right_out);
    }

    protected CustomProgressDialog mProgressDialog;
    public void loading(boolean show){
        if(mProgressDialog==null){
            mProgressDialog=new CustomProgressDialog(this);
        }
        if(show){
            mProgressDialog.show();
        }else {
            mProgressDialog.dismiss();
        }

    }

    public void showTip(String msg,int code){
        showTip(msg);
    }

}
