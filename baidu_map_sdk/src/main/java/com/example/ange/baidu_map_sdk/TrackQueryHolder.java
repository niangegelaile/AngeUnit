package com.example.ange.baidu_map_sdk;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.trace.OnTrackListener;
import com.example.ange.baidu_map_sdk.utils.GsonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询轨迹
 *
 * Created by liquanan on 2016/11/28 0028.
 */

public class TrackQueryHolder {
    private final static String TAG="TrackQueryHolder";
    private BaiduMapManager mBaiduMapManager =null;



    private int startTime = 0;
    private int endTime = 0;


    // 起点图标
    private static BitmapDescriptor bmStart;
    // 终点图标
    private static BitmapDescriptor bmEnd;

    // 起点图标覆盖物
    private static MarkerOptions startMarker = null;
    // 终点图标覆盖物
    private static MarkerOptions endMarker = null;
    // 路线覆盖物
    public static PolylineOptions polyline = null;

    private static MarkerOptions markerOptions = null;

    /**
     * Track监听器
     */
    protected static OnTrackListener trackListener = null;

    private MapStatusUpdate msUpdate = null;

    private TextView tvDatetime = null;

    private static int isProcessed = 0;

    private int icon_start_id;
    private int icon_end_id;
    private int icon_gcoding_id;




    public TrackQueryHolder(BaiduMapManager baiduMapManager){

        mBaiduMapManager =baiduMapManager;
        // 初始化OnTrackListener
        initOnTrackListener();
    }

    /**
     * 正在查询纠偏后的历史轨迹，请稍候
     */
    public void queryCorrectHistoryTrack(){
        queryHistoryTrack(1, "need_denoise=1,need_vacuate=1,need_mapmatch=1");
    }

    /**
     * 查询历史轨迹
     */
    private void queryHistoryTrack(int processed, String processOption) {

        // entity标识
        String entityName = mBaiduMapManager.getEntityName();
        // 是否返回精简的结果（0 : 否，1 : 是）
        int simpleReturn = 0;
        // 是否返回纠偏后轨迹（0 : 否，1 : 是）
        int isProcessed = processed;
        // 开始时间
        if (startTime == 0) {
            startTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
        }
        if (endTime == 0) {
            endTime = (int) (System.currentTimeMillis() / 1000);
        }
        // 分页大小
        int pageSize = 1000;
        // 分页索引
        int pageIndex = 1;

        mBaiduMapManager.getClient().queryHistoryTrack(mBaiduMapManager.getServiceId(), entityName, simpleReturn,
                isProcessed, processOption,
                startTime, endTime,
                pageSize,
                pageIndex,
                trackListener);
    }

    // 查询里程
    private void queryDistance(int processed, String processOption) {

        // entity标识
        String entityName = mBaiduMapManager.getEntityName();

        // 是否返回纠偏后轨迹（0 : 否，1 : 是）
        int isProcessed = processed;

        // 里程补充
        String supplementMode = "driving";

        // 开始时间
        if (startTime == 0) {
            startTime = (int) (System.currentTimeMillis() / 1000 - 12 * 60 * 60);
        }
        // 结束时间
        if (endTime == 0) {
            endTime = (int) (System.currentTimeMillis() / 1000);
        }

        mBaiduMapManager.getClient().queryDistance(mBaiduMapManager.getServiceId(), entityName, isProcessed, processOption,
                supplementMode, startTime, endTime, trackListener);
    }



    /**
     * 显示历史轨迹
     *
     * @param historyTrack
     */
    private void showHistoryTrack(String historyTrack) {

        HistoryTrackData historyTrackData = GsonService.parseJson(historyTrack,
                HistoryTrackData.class);

        List<LatLng> latLngList = new ArrayList<LatLng>();
        if (historyTrackData != null && historyTrackData.getStatus() == 0) {
            if (historyTrackData.getListPoints() != null) {
                latLngList.addAll(historyTrackData.getListPoints());
            }
            Log.d(TAG,"绘制历史轨迹:"+historyTrack);
            // 绘制历史轨迹
            drawHistoryTrack(latLngList);

        }

    }


    /**
     * 初始化OnTrackListener
     */
    private void initOnTrackListener() {

        trackListener = new OnTrackListener() {

            // 请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                // TODO Auto-generated method stub
//                mBaiduMapManager.getmHandler().obtainMessage(0, "track请求失败回调接口消息 : " + arg0).sendToTarget();
            }

            // 查询历史轨迹回调接口
            @Override
            public void onQueryHistoryTrackCallback(String arg0) {
                // TODO Auto-generated method stub
                super.onQueryHistoryTrackCallback(arg0);
                Log.d(TAG,arg0+startTime+":"+endTime);
                showHistoryTrack(arg0);
            }


            @Override
            public Map<String, String> onTrackAttrCallback() {
                // TODO Auto-generated method stub
                System.out.println("onTrackAttrCallback");
                return null;
            }

        };
    }

    /**
     * 绘制历史轨迹
     *
     * @param points
     */
    public  void drawHistoryTrack(final List<LatLng> points) {
        // 绘制新覆盖物前，清空之前的覆盖物
        mBaiduMapManager.getmBaiduMap().clear();

        if (points.size() == 1) {
            points.add(points.get(0));
        }

        if (points == null || points.size() == 0) {
//            mBaiduMapManager.getmHandler().obtainMessage(0, "当前查询无轨迹点").sendToTarget();
            resetMarker();
        } else if (points.size() > 1) {

            LatLng llC = points.get(0);
            LatLng llD = points.get(points.size() - 1);
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(llC).include(llD).build();

            msUpdate = MapStatusUpdateFactory.newLatLngBounds(bounds);

            bmStart = BitmapDescriptorFactory.fromResource(icon_start_id);
            bmEnd = BitmapDescriptorFactory.fromResource(icon_end_id);

            // 添加起点图标
            startMarker = new MarkerOptions()
                    .position(points.get(points.size() - 1)).icon(bmStart)
                    .zIndex(9).draggable(true);

            // 添加终点图标
            endMarker = new MarkerOptions().position(points.get(0))
                    .icon(bmEnd).zIndex(9).draggable(true);

            // 添加路线（轨迹）
            polyline = new PolylineOptions().width(10)
                    .color(Color.RED).points(points);

            markerOptions = new MarkerOptions();
            markerOptions.flat(true);
            markerOptions.anchor(0.5f, 0.5f);
            markerOptions.icon(BitmapDescriptorFactory
                    .fromResource(icon_gcoding_id));
            markerOptions.position(points.get(points.size() - 1));

            addMarker();

        }

    }

    /**
     * 添加覆盖物
     */
    protected void addMarker() {

        if (null != msUpdate) {
            mBaiduMapManager.getmBaiduMap().animateMapStatus(msUpdate, 2000);
        }

        if (null != startMarker) {
            mBaiduMapManager.getmBaiduMap().addOverlay(startMarker);
        }

        if (null != endMarker) {
            mBaiduMapManager.getmBaiduMap().addOverlay(endMarker);
        }

        if (null != polyline) {
            mBaiduMapManager.getmBaiduMap().addOverlay(polyline);
        }

    }

    /**
     * 重置覆盖物
     */
    private void resetMarker() {
        startMarker = null;
        endMarker = null;
        polyline = null;
    }

    public void setIconId( int icon_start_id, int icon_end_id, int icon_gcoding_id){
        this. icon_start_id=icon_start_id;
        this.icon_end_id=icon_end_id;
        this.icon_gcoding_id=icon_gcoding_id;
    }




}
