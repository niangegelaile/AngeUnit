package com.ange.image;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
@Component(modules = {ImageModule.class})
@Singleton
public interface ImageComponent {
    ImageUtil getImageUtil();
}
