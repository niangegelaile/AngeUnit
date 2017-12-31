package com.example.ange.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ange.base.BaseActivity;
import com.example.ange.R;


import com.example.ange.module.image.ImageActivity;
import com.example.ange.module.add.AddActivity;
import com.example.ange.module.list.ListActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;




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



    @OnClick({R.id.but_db, R.id.but_map,R.id.but_photo,R.id.but_hyphenate,R.id.but_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_db:
                Intent intentDb=new Intent(this, ListActivity.class);
                startActivity(intentDb);
                break;
            case R.id.but_map:

                break;
            case R.id.but_photo:


                break;
            case R.id.but_hyphenate:
//                Intent hyphenate=new Intent(this, HLoginActivity.class);
//                startActivity(hyphenate);
                break;
            case R.id.but_image:
                Intent image=new Intent(this, ImageActivity.class);
                startActivity(image);
                break;
        }
    }
}
