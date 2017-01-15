package com.example.ange.baidu_map_sdk;



import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.Trace;

import java.lang.ref.WeakReference;

/**
 * 百度地图管理类
 * Created by Administrator on 2016/11/25 0025.
 */

public class BaiduMapManager {

    private static BaiduMapManager mBaiduMapManager;
    /**
     * 轨迹服务
     */
    private Trace trace = null;

    /**
     * 轨迹服务客户端
     */
    private LBSTraceClient client = null;

    /**
     * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
     */
    private static int serviceId = 0 ;


    /**
     * entity标识
     */
    private static String entityName = null;

    /**
     * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
     */
    private int traceType = 2;

    private MapView bmapView = null;

    private BaiduMap mBaiduMap = null;


    private BaiduMapManager(Context mContext,String entityName,int serviceId){

        this.entityName=entityName;
        this.serviceId=serviceId;

        // 初始化轨迹服务客户端
        client = new LBSTraceClient(mContext);

        // 初始化轨迹服务
        trace = new Trace(mContext, serviceId, this.entityName, traceType);

        // 设置定位模式
        client.setLocationMode(LocationMode.High_Accuracy);

    }
    public synchronized static BaiduMapManager getInstance(Context mContext,String entityName,int serviceId){

        if(mBaiduMapManager==null){
            mBaiduMapManager=new BaiduMapManager(mContext,entityName,serviceId);
        }
        return mBaiduMapManager;
    }

    public synchronized static BaiduMapManager getInstance(Context mContext){

        if(mBaiduMapManager==null){
            mBaiduMapManager=new BaiduMapManager(mContext,entityName,serviceId);
        }
        return mBaiduMapManager;
    }
    public void initBmap(MapView bmapView) {
        this.bmapView = bmapView;
        this.mBaiduMap = bmapView.getMap();
        this.bmapView.showZoomControls(false);
    }

    public Trace getTrace() {
        return trace;
    }

    public LBSTraceClient getClient() {
        return client;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getEntityName() {
        return entityName;
    }

    public MapView getBmapView() {
        return bmapView;
    }

    public BaiduMap getmBaiduMap() {
        return mBaiduMap;
    }


    public void setEntityName(String entityName){
        this.entityName=entityName;
        trace.setEntityName(entityName);
    }

    public void setServiceId(int serviceId){
        this.serviceId=serviceId;
        trace.setServiceId(serviceId);
    }


}
