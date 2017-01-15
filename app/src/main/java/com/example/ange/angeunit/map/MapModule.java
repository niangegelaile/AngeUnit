package com.example.ange.angeunit.map;

import android.content.Context;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;
import com.example.ange.angeunit.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
@Module
public final class MapModule {

    private final static long serviceId= BuildConfig.BAIDU_TRACE_SERVICE_ID;//鹰眼服务id

    private final String entityName;//百度鹰眼服务的每个用户的唯一标识

    private final static int traceType=2 ;//轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）

    /**
     * 采集周期（单位 : 秒）
     */
    private final static int gatherInterval = 10;

    /**
     * 打包周期（单位 : 秒）
     */
    private final static int packInterval = 30;

    private final static int protocolType=0;

    private final Context mContext;

    public MapModule(String entityName,Context mContext){
        this.entityName=entityName;
        this.mContext=mContext;
    }
    @Provides
    public Context providerContext() {
        return mContext;
    }
    /**
     *初始化轨迹服务客户端
     * @param context
     * @return
     */
    @Provides
    @Singleton
    public LBSTraceClient provideLBSTraceClient(Context context){

        LBSTraceClient client=new LBSTraceClient(context);
        // 设置定位模式
        client.setLocationMode(LocationMode.High_Accuracy);
        //设置采集周期和打包周期
        client.setInterval(gatherInterval, packInterval);
        //设置请求协议
        client.setProtocolType(protocolType);
        return client;
    }

    /**
     * 初始化轨迹服务
     * @param context
     * @return
     */
    @Provides
    @Singleton
    public Trace provideTrace(Context context){
        return new Trace(context, serviceId, this.entityName, traceType);
    }

}
