package com.example.ange.module.baidumap;

import android.app.Application;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ange.base.RxBus;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.example.ange.map.DaggerMapComponent;
import com.example.ange.map.MapComponent;
import com.example.ange.map.MapModule;

import javax.inject.Inject;

/**
 * 每隔一段时间查询一次位置
 * Created by Administrator on 2017/1/15 0015.
 */

public class QueryLocationService extends IntentService {

    private static final int gatherInterval = 20;//查询周期
    private static final String TAG = "QueryLocationService";
    public static  final String EXTRA_Refresh="refresh";
    protected boolean refresh = true;
    @Inject
    LBSTraceClient mLbsTraceClient;
    @Inject
    Trace mTrace;
    public QueryLocationService(){
        this("QueryLocationService");
    }
    public QueryLocationService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Application application= getApplication();
        Log.d(TAG,"hashCode"+application.hashCode());
        MapComponent mapComponent = DaggerMapComponent
                .builder()
                .mapModule(new MapModule("QueryLocationService",application)).build();
        mapComponent.inject(this);
        Log.d("LBSTraceClient","hascode QueryLocationService:"+mLbsTraceClient.hashCode());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        refresh= intent.getBooleanExtra(EXTRA_Refresh,true);
        while (refresh) {
            // 轨迹服务开启成功后，调用queryEntityList()查询最新轨迹；
            // 未开启轨迹服务时，调用queryRealtimeLoc()进行实时定位。
            queryRealTimeLoc();
            Log.d(TAG, "查询实时位置！");
            try {
                Thread.sleep(gatherInterval * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println("线程休眠失败");
            }
        }

    }

    /**
     * 请求实时位置
     */
    private void queryRealTimeLoc() {
        mLbsTraceClient. queryRealtimeLoc(mTrace.getServiceId(), new OnEntityListener() {
            @Override
            public void onRequestFailedCallback(String s) {
                Log.d(TAG,s);
            }
            @Override
            public void onReceiveLocation(TraceLocation location) {
                // TODO Auto-generated method stub
                Log.d(TAG,location.toString());
                RxBus.getDefault().post(location);
            }
        });
    }

}
