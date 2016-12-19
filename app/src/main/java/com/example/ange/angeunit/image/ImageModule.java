package com.example.ange.angeunit.image;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
@Module
public final class ImageModule {
    private final ImageUtil imageUtil;
    public ImageModule(ImageUtil imageUtil){
        this.imageUtil=imageUtil;
    }

    @Singleton
    @Provides
    public ImageUtil providerImageUtil(){
        return imageUtil;
    }
}
