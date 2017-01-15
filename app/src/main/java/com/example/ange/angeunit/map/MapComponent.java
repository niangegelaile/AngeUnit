package com.example.ange.angeunit.map;

import com.example.ange.angeunit.module.baidumap.QueryLocationService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = MapModule.class)
public interface MapComponent {
    void inject(QueryLocationService service);
}