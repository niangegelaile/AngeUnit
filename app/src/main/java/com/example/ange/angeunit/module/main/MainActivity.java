package com.example.ange.angeunit.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.ange.angeunit.R;
import com.example.ange.angeunit.base.BaseActivity;
import com.example.ange.angeunit.module.baidumap.BaiduMapActivity;
import com.example.ange.angeunit.module.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liquanan on 2017/1/15.
 * email :1369650335@qq.com
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.but_db)
    Button butDb;
    @BindView(R.id.but_map)
    Button butMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void acceptIntent(Intent intent) {

    }

    @Override
    protected void buildComponentForInject() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.but_db, R.id.but_map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_db:
                Intent intentDb=new Intent(this, LoginActivity.class);
                startActivity(intentDb);
                break;
            case R.id.but_map:
                Intent intentMap=new Intent(this, BaiduMapActivity.class);
                startActivity(intentMap);
                break;
        }
    }
}
