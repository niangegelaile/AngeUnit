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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by liquanan on 2017/1/15.
 * email :1369650335@qq.com
 */
public class MainActivity extends BaseActivity {
    private static final int REQUEST_IMAGE = 764;
    List<String> imgsHolder=new ArrayList<>();
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



    @OnClick({R.id.but_db, R.id.but_map,R.id.but_photo})
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
            case R.id.but_photo:
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
                .count(9) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
                    .origin(imgsHolder) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(this, REQUEST_IMAGE);

                break;
        }
    }
}
