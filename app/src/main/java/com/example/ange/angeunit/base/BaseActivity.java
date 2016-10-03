package com.example.ange.angeunit.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ange.angeunit.dragger.AppComponent;
import com.example.ange.angeunit.dragger.ComponentHolder;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/10/1.
 */
public class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription subscriptions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptions=new CompositeSubscription();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscriptions!=null&&!subscriptions.isUnsubscribed()){
            subscriptions.unsubscribe();
        }
    }
}
