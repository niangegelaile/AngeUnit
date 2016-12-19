package com.example.ange.angeunit.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 图片加载类
 * Created by Administrator on 2016/12/19 0019.
 */

public class MyGilde implements ImageUtil {

    @Override
    public void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }
}
