package com.example.ange.baidu_map_sdk;

import android.os.Looper;
import android.util.Log;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.TraceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by liquanan on 2016/11/25 0025.
 */

public class TrackUploadHolder {


    private static final  String TAG="TrackUploadHolder";


    private BaiduMapManager baiduMapManager =null;

    private OnLocationListener onLocationListener;

    /**
     * 采集周期（单位 : 秒）
     */
    private int gatherInterval = 10;

    /**
     * 打包周期（单位 : 秒）
     */
    private int packInterval = 30;

    /**
     * 图标
     */
    private static BitmapDescriptor realtimeBitmap;

    private static Overlay overlay = null;

    // 覆盖物
    protected static OverlayOptions overlayOptions;


    private static List<LatLng> pointList = new ArrayList<LatLng>();


    /**
     * 刷新地图线程(获取实时点)
     */
    protected RefreshThread refreshThread = null;

    protected static MapStatusUpdate msUpdate = null;


    private static TrackUploadHolder trackUploadHolder;



    private  TrackUploadHolder(BaiduMapManager mEpwApplication) {

       this.baiduMapManager =mEpwApplication;

        // 初始化监听器
        initListener();

        // 设置采集周期
        setInterval();

        // 设置http请求协议类型
        setRequestType();
    }

    public static synchronized TrackUploadHolder getInstance(BaiduMapManager mEpwApplication){
        if(trackUploadHolder==null){
            trackUploadHolder=new TrackUploadHolder(mEpwApplication);
        }
        return trackUploadHolder;
    }


    public void setOnLocationListener(OnLocationListener onLocationListener){
        this.onLocationListener=onLocationListener;
    }




    /**
     * 初始化监听器
     */
    private void initListener() {
        // 初始化entity监听器
        if (null == entityListener) {
            initOnEntityListener();
        }
    }



    /**
     * 设置采集周期和打包周期
     */
    private void setInterval() {
        baiduMapManager.getClient().setInterval(gatherInterval, packInterval);
    }

    /**
     * 设置请求协议
     */
    protected void setRequestType() {
        int type = 0;
        baiduMapManager.getClient().setProtocolType(type);
    }

    /**
     * 查询实时轨迹
     */
    private void queryRealtimeLoc() {
        baiduMapManager.getClient().queryRealtimeLoc(baiduMapManager.getServiceId(), entityListener);
    }

    /**
     * Entity监听器
     */
    private static OnEntityListener entityListener = null;


    /**
     * 初始化OnEntityListener,（请求回调），（请求位置，轨迹，成功后绘制覆盖点）
     */
    private void initOnEntityListener() {
        entityListener = new OnEntityListener() {

            // 请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                // TODO Auto-generated method stub
                Log.d(TAG, "entity请求失败回调接口消息 : " + arg0);
            }

            @Override
            public void onReceiveLocation(TraceLocation location) {
                // TODO Auto-generated method stub

                if(onLocationListener!=null){
                    onLocationListener.onLocation(location);
                }
                Log.d(TAG,location.toString());
            }

        };
    }

    protected class RefreshThread extends Thread {

        protected boolean refresh = true;

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Looper.prepare();
            while (refresh) {
                // 轨迹服务开启成功后，调用queryEntityList()查询最新轨迹；
                // 未开启轨迹服务时，调用queryRealtimeLoc()进行实时定位。
                    queryRealtimeLoc();
                    Log.d(TAG,"查询实时位置！");
                try {
                    Thread.sleep(gatherInterval * 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    System.out.println("线程休眠失败");
                }
            }
            Looper.loop();
        }
    }

    /**
     * 显示实时轨迹
     *
     *
     */
    public void showRealtimeTrack(double latitude,double longitude) {

        if (Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001) {
//            mHandler.obtainMessage(-1, "当前查询无轨迹点").sendToTarget();
        } else {
            LatLng latLng = new LatLng(latitude, longitude);
                // 绘制实时点
            drawRealtimePoint(latLng,icon_gcoding_id);

        }
    }

    private int icon_gcoding_id;

    public void setIcon_gcoding_id(int icon_gcoding_id) {
        this.icon_gcoding_id = icon_gcoding_id;
    }

    /**
     * 绘制实时点
     *
     * @param point @param icon_gcoding_id 图标参数
     */
    private void drawRealtimePoint(LatLng point,int icon_gcoding_id) {

        if (null != overlay) {
            overlay.remove();
        }

        MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(19).build();

        msUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        if (null == realtimeBitmap) {
            realtimeBitmap = BitmapDescriptorFactory
                    .fromResource(icon_gcoding_id);
        }

        overlayOptions = new MarkerOptions().position(point)
                .icon(realtimeBitmap).zIndex(9).draggable(true);

        addMarker();

    }

    /**
     * 添加地图覆盖物
     */
    public void addMarker() {
        try {
            if (null != msUpdate&&baiduMapManager.getmBaiduMap()!=null) {
                baiduMapManager.getmBaiduMap().setMapStatus(msUpdate);
            }

            // 实时点覆盖物
            if (null != overlayOptions&&baiduMapManager.getmBaiduMap()!=null) {
                overlay = baiduMapManager.getmBaiduMap().addOverlay(overlayOptions);
            }
        }catch (Exception e){
            Log.d(TAG, "addMarker: "+e.toString());
        }


    }

    public void startRefreshThread(boolean isStart) {
        if (null == refreshThread) {
            refreshThread = new RefreshThread();
        }
        refreshThread.refresh = isStart;
        if (isStart) {
            if (!refreshThread.isAlive()) {
                refreshThread.start();
            }
        } else {
            refreshThread = null;
        }
    }

}
