package com.example.ange.module.baidumap;



import dagger.Subcomponent;


@Subcomponent(
        modules = BaiduMapModule.class)
public interface BaiduMapComponent {
    void inject(BaiduMapActivity activity);
}