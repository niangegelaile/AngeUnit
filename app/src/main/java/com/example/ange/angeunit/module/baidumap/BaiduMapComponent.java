package com.example.ange.angeunit.module.baidumap;

import com.example.ange.angeunit.repository.RepositoryComponent;
import com.example.ange.angeunit.utils.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies ={RepositoryComponent.class} ,
        modules = BaiduMapModule.class)
public interface BaiduMapComponent {
    void inject(BaiduMapActivity activity);
}