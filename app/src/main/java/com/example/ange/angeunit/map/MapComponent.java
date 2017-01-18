package com.example.ange.angeunit.map;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.example.ange.angeunit.module.baidumap.QueryLocationService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = MapModule.class)
public interface MapComponent {
    void inject(QueryLocationService service);
    LBSTraceClient getLBSTraceClient();
    Trace getTrace();
}