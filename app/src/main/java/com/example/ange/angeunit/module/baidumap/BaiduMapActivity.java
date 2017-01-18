package com.example.ange.angeunit.module.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.TraceLocation;
import com.example.ange.angeunit.MyApplication;
import com.example.ange.angeunit.R;
import com.example.ange.angeunit.base.BaseActivity;
import com.example.ange.angeunit.base.RxBus;
import com.example.ange.angeunit.map.DaggerMapComponent;
import com.example.ange.angeunit.map.MapComponent;
import com.example.ange.angeunit.map.MapModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 使用百度地图进行定位
 * Created by Administrator on 2017/1/15 0015.
 */

public class BaiduMapActivity extends BaseActivity implements BaiduMapContract.View{
    private final static String TAG="BaiduMapActivity";
    @BindView(R.id.map)
    MapView map;
    BaiduMap mBaiduMap;
    Subscription sb;
    @Inject
    BaiduMapPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
        ButterKnife.bind(this);
        mBaiduMap= map.getMap();
        map.showZoomControls(false);
        if(savedInstanceState!=null){
            map.onCreate(this,savedInstanceState);
        }
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
                        Log.d(TAG,"RXBUS:"+traceLocation.toString());
                        drawRealTimePoint(traceLocation.getLatitude(),traceLocation.getLongitude());
                    }
                });
    }

    @Override
    protected void acceptIntent(Intent intent) {

    }

    @Override
    protected void buildComponentForInject() {
        BaiduMapComponent baiduMapComponent= DaggerBaiduMapComponent
                            .builder()
                            .repositoryComponent(((MyApplication)getApplication())
                            .getRepositoryComponent())
                            .baiduMapModule(new BaiduMapModule(this)).build();
                baiduMapComponent.inject(this);
        MapComponent mapComponent = DaggerMapComponent
                .builder()
                .mapModule(new MapModule("QueryLocationService",getApplication())).build();

        mPresenter.setLbsTraceClient(mapComponent.getLBSTraceClient());
    }


    BitmapDescriptor realtimeBitmap;
    MarkerOptions overlayOptions;
    /**
     * 显示实时位置
     */
    public void drawRealTimePoint(double latitude,double longitude) {

        if (!(Math.abs(latitude - 0.0) < 0.000001 && Math.abs(longitude - 0.0) < 0.000001)) {
            LatLng latLng = new LatLng(latitude, longitude);
            mBaiduMap.clear();
            if(realtimeBitmap==null){
                realtimeBitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_gcoding);
            }

            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    //要移动的点
                    .target(latLng)
                    //放大地图到20倍
                    .zoom(20)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
             overlayOptions = new MarkerOptions().position(latLng)
                    .icon(realtimeBitmap).zIndex(9).draggable(true);
            mBaiduMap.addOverlay(overlayOptions);
        }
    }

    @Override
    public void setPresenter(BaiduMapContract.Presenter presenter) {

    }

    @Override
    public void loading() {

    }

    @Override
    public void finishLoading() {

    }

    @Override
    public void loadFail() {

    }
}
