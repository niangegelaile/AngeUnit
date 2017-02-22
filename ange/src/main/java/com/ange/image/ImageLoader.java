package com.ange.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImageLoader {


    private ImageUtil mImageUtil;

    private static ImageLoader mImageLoader;

    public static synchronized ImageLoader getInstance(){

        if(mImageLoader==null){
            mImageLoader=new ImageLoader();
        }
        return mImageLoader;
    }


    private ImageLoader(){
        ImageComponent imageComponent=DaggerImageComponent.builder().imageModule(new ImageModule(new MyImageUtil())).build();
        mImageUtil=imageComponent.getImageUtil();
    }

    public void displayImage(Context context,String url, ImageView imageView){
        mImageUtil.loadImage(context,imageView,url);
    }

}
