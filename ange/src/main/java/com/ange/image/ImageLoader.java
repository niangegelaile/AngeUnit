package com.ange.image;


import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImageLoader {

    public static void displayImage(String url, ImageView imageView){
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
    }

}
