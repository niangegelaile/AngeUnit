package com.example.ange.angeunit.module.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.TraceLocation;
import com.example.ange.angeunit.R;
import com.example.ange.angeunit.base.BaseActivity;
import com.example.ange.angeunit.base.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/15 0015.
 */

public class BaiduMapActivity extends BaseActivity {

    @BindView(R.id.map)
    MapView map;
    BaiduMap mBaiduMap;
    Subscription sb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        if(savedInstanceState!=null){
            map.onCreate(this,savedInstanceState);
        }


        ButterKnife.bind(this);
        mBaiduMap= map.getMap();
        map.showZoomControls(false);
        Intent intent=new Intent(this,QueryLocationService.class);
        startService(intent);
    }



    @Override
    protected void onStart() {
        super.onStart();

        if(sb==null||sb.isUnsubscribed()){
            registerBus();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(sb!=null&&!sb.isUnsubscribed()){
            sb.unsubscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    private void registerBus() {
       sb= RxBus.getDefault()
                .toObservable(TraceLocation.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TraceLocation>() {
                    @Override
                    public void call(TraceLocation traceLocation) {
                        drawRealtimePoint(traceLocation.getLatitude(),traceLocation.getLongitude());
                    }
                });
    }

    @Override
    protected void acceptIntent(Intent intent) {

    }

    @Override
    protected void buildComponentForInject() {

    }

    @Override
    protected void loadData() {

    }
    BitmapDescriptor realtimeBitmap;
    MarkerOptions overlayOptions;
    /**
     * 显示实时位置
     */
    public void drawRealtimePoint(double latitude,double longitude) {

        if (!(Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001)) {
            LatLng latLng = new LatLng(latitude, longitude);
            mBaiduMap.clear();
            if(realtimeBitmap==null){
                realtimeBitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding);
            }
             overlayOptions = new MarkerOptions().position(latLng)
                    .icon(realtimeBitmap).zIndex(9).draggable(true);
            mBaiduMap.addOverlay(overlayOptions);
        }
    }
}
