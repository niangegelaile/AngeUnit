package com.example.ange.angeunit.module.baidumap;


import com.ange.repository.RepositoryComponent;
import com.ange.utils.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies ={RepositoryComponent.class} ,
        modules = BaiduMapModule.class)
public interface BaiduMapComponent {
    void inject(BaiduMapActivity activity);
}