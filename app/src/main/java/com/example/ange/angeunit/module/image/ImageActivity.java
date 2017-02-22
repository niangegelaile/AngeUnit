package com.example.ange.angeunit.module.image;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ange.base.BaseActivity;
import com.ange.image.ImageLoader;
import com.example.ange.angeunit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImageActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
//        Glide.with(this)
//                .load("http://p0.qhimg.com/d/360browser/20130204/wallpaper4.jpg")
//                .into(iv);
        ImageLoader.getInstance().displayImage(this,"http://p0.qhimg.com/d/360browser/20130204/wallpaper4.jpg",iv);
    }

    @Override
    protected void acceptIntent(Intent intent) {

    }

    @Override
    protected void buildComponentForInject() {

    }
}
