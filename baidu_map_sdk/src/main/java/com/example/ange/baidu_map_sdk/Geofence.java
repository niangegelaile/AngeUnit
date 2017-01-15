package com.example.ange.baidu_map_sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.nfc.Tag;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.OnGeoFenceListener;
import com.baidu.trace.model.CreateLocalFenceResult;
import com.baidu.trace.model.DeleteLocalFenceResult;
import com.example.ange.baidu_map_sdk.utils.DateUtils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 地理围栏
 */
@SuppressLint("NewApi")
public class Geofence  {

    private static final String TAG="Fence";

    private BaiduMapManager baiduMapManager = null;

    private Context mContext = null;

    // 围栏圆心纬度
    private double latitude = 0;

    // 围栏圆心经度
    private double longitude = 0;

    // 围栏半径
    private static int radius = 100;

    // 顶点数量
    private static int vertexNumber = 4;

    private List<LatLng> vertexs = new ArrayList<LatLng>();

    // 围栏类型（0: 服务端围栏、1：本地围栏）
    private static int fenceType = 0;

    // 围栏形状（0：圆形、1：多边形）
    private static int fenceShape = 0;

    // 围栏编号
    public static int serverFenceId = 0;

    // 围栏覆盖物
    private static Overlay fenceOverlay = null;

    // 围栏覆盖物参数
    private static OverlayOptions fenceOverlayOption = null;

    private List<Overlay> overlays = new ArrayList<Overlay>();

    // 围栏去噪精度
    private static int precision = 30;

    private BitmapDescriptor pointBitmap = null;



    private OnMapClickListener mapClickListener = new OnMapClickListener() {

        public void onMapClick(LatLng arg0) {
            // TODO Auto-generated method stub

            switch (fenceShape) {

                case 1:
                    vertexs.add(arg0);
                    int resourceId;

                    Resources res = mContext.getResources();
                    if (vertexs.size() <= 10) {
                        resourceId =
                                res.getIdentifier("icon_mark" + vertexs.size(), "mipmap", mContext.getPackageName());
                    } else {
                        resourceId = res.getIdentifier("icon_gcoding", "mipmap",
                                mContext.getPackageName());
                    }
                    /*画点*/
                    pointBitmap = BitmapDescriptorFactory
                            .fromResource(resourceId);

                    OverlayOptions overlayOptions = new MarkerOptions().position(arg0)
                            .icon(pointBitmap).zIndex(9).draggable(true);
                    overlays.add(baiduMapManager.getmBaiduMap().addOverlay(overlayOptions));
                    /**/
                    if (vertexs.size() == vertexNumber) {

                        if (null != fenceOverlay) {
                            fenceOverlay.remove();
                        }

                        fenceOverlayOption =
                                new PolygonOptions().points(vertexs).stroke(new Stroke(vertexNumber, 0xAAFF0000))
                                        .fillColor(0x30FFFFFF);
                        addMarker();
                        if(onHasChooseFenceListener!=null){
                            onHasChooseFenceListener.onFinishChoose();
                        }
                    }

                    break;

                default:
                    break;
            }

        }

        public boolean onMapPoiClick(MapPoi arg0) {
            // TODO Auto-generated method stub
            return false;
        }
    };

    public List<LatLng>  getVertexs(){
        return vertexs;
    }


    private GeoFenceBaseDataListener baseDataListener;

    public void setGeoFenceDataListener(GeoFenceBaseDataListener baseDataListener) {
        this.baseDataListener = baseDataListener;
    }

    public interface GeoFenceBaseDataListener{

        void setFenceName(String fenceName);

        void setValidDays(String days);

        void setStartTime(String startTime);

        void setEndTime(String endTime);
    }


    /**
     * 创建服务端多边形围栏（若创建围栏时，还未创建entity，请先调用addEntity(...)添加entity）
     */
    private void createServerVertexesFence(String mfenceName,String startTime,String endTime,String mValidDays,String monitoredPersons) {

        // 创建者（entity标识）
        String creator = baiduMapManager.getEntityName();
        // 围栏名称
        String fenceName = mfenceName;
        // 围栏描述
        String fenceDesc = "test";
        // 监控对象列表（多个entityName，以英文逗号"," 分割）

        Log.d(TAG,"创建人："+creator+" 监控对象:"+monitoredPersons);
        // 观察者列表（多个entityName，以英文逗号"," 分割）
        String observers = baiduMapManager.getEntityName();
        // 生效时间列表（表示几点几分 到 几点几分围栏生效，示例为08点00分 到 23点00分）
//        String validTimes = "0800,2300";
        String validTimes = startTime+","+endTime;
        // 生效周期
        int validCycle = 5;
        // 围栏生效日期
        String validDate = "";
        // 生效日期列表
        String validDays = mValidDays;
        // 坐标类型 （1：GPS经纬度，2：国测局经纬度，3：百度经纬度）
        int coordType = 3;
        // 报警条件（1：进入时触发提醒，2：离开时触发提醒，3：进入离开均触发提醒）
        int alarmCondition = 2;
        // 顶点列表
        StringBuilder vertexsStr = new StringBuilder();
        for (LatLng ll : vertexs) {
            vertexsStr.append(ll.longitude).append(",").append(ll.latitude).append(";");
        }
        baiduMapManager.getClient().createVertexesFence(baiduMapManager.getServiceId(), creator, fenceName, fenceDesc,
                monitoredPersons, observers,
                validTimes, validCycle, validDate, validDays, coordType,
                vertexsStr.substring(0, vertexsStr.length() - 1), precision,
                alarmCondition,
                geoFenceListener);
    }

    /**
     * 删除服务端围栏
     */
    public  void deleteServerFence(int fenceId) {
        baiduMapManager.getClient().deleteFence(baiduMapManager.getServiceId(), fenceId, geoFenceListener);
    }

    /**
     * 更新服务端多边形围栏
     */
    private void updateServerVertexesFence(String mfenceName,String startTime,String endTime,String mValidDays,String monitoredPersons) {
        // 围栏名称
        String fenceName = mfenceName;
        // 围栏ID
        int fenceId = Geofence.serverFenceId;
        // 围栏描述
        String fenceDesc = "test fence";
        // 监控对象列表（多个entityName，以英文逗号"," 分割）

        // 观察者列表（多个entityName，以英文逗号"," 分割）
        String observers = baiduMapManager.getEntityName();
        // 生效时间列表（表示几点几分 到 几点几分围栏生效，示例为08点00分 到 23点00分）
        String validTimes = startTime+","+endTime;
        // 生效周期
        int validCycle = 5;
        // 围栏生效日期
        String validDate = "";
        // 生效日期列表
        String validDays = mValidDays;
        // 坐标类型 （1：GPS经纬度，2：国测局经纬度，3：百度经纬度）
        int coordType = 3;
        // 顶点列表
        StringBuilder vertexsStr = new StringBuilder();
        for (LatLng ll : vertexs) {
            vertexsStr.append(ll.longitude).append(",").append(ll.latitude).append(";");
        }
        System.out.println("vertexs : " + vertexsStr.substring(0, vertexsStr.length() - 1));
        // 报警条件（1：进入时触发提醒，2：离开时触发提醒，3：进入离开均触发提醒）
        int alarmCondition = 2;
        Log.d(TAG,"创建人："+observers+" 监控对象:"+monitoredPersons);
        baiduMapManager.getClient().updateVertexesFence(baiduMapManager.getServiceId(), fenceName, fenceId, fenceDesc,
                monitoredPersons, observers,
                validTimes, validCycle, validDate, validDays, coordType,
                vertexsStr.substring(0, vertexsStr.length() - 1), precision, alarmCondition,
                geoFenceListener);
    }

    /**
     * 查询服务端围栏列表
     */
    public void queryServerFenceList() {
        // 创建者（entity标识）
        String creator = baiduMapManager.getEntityName();
        // 围栏ID列表
        String fenceIds = "";
        Log.d(TAG,"创建者:"+creator);
        baiduMapManager.getClient().queryFenceList(baiduMapManager.getServiceId(), creator, fenceIds, geoFenceListener);
    }

    /**
     * 监控状态（服务端围栏）
     */
    private void monitoredStatus() {
        // 围栏ID
        int fenceId = Geofence.serverFenceId;
        // 监控对象列表（多个entityName，以英文逗号"," 分割）
        String monitoredPersons = baiduMapManager.getEntityName();
        baiduMapManager.getClient().queryMonitoredStatus(baiduMapManager.getServiceId(), fenceId, monitoredPersons,
                geoFenceListener);
    }



    // 地理围栏监听器
    private static OnGeoFenceListener geoFenceListener = null;
    /**
     * 初始化OnGeoFenceListener
     */
    private void initOnGeoFenceListener() {
        // 初始化geoFenceListener
        geoFenceListener = new OnGeoFenceListener() {

            // 请求失败回调接口
            @Override
            public void onRequestFailedCallback(String arg0) {
                Log.d(TAG,arg0);
            }


            // 创建服务端多边形围栏回调接口
            @Override
            public void onCreateVertexesFenceCallback(String arg0) {
                // TODO Auto-generated method stub
                Log.d(TAG,"创建服务端多边形围栏回调接口:"+arg0);
                JSONObject dataJson;
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        serverFenceId = dataJson.getInt("fence_id");
                        if(onFenceListener!=null){
                            onFenceListener.onCreate(serverFenceId);
                        }
                        Log.d(TAG,"服务端多边形围栏创建成功，围栏编号 : " + serverFenceId);
                    } else {
                        baiduMapManager.getmBaiduMap().clear();
                        addMarker();
                        Log.d(TAG, "创建服务端多边形围栏回调接口消息 : " + arg0);
                        if(onFenceListener!=null){
                            onFenceListener.onFailed("关爱对象没有开启定位服务");
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "解析创建服务端多边形围栏回调消息失败");
                }
            }

            // 更新服务端多边形围栏回调接口
            @Override
            public void onUpdateVertexesFenceCallback(String arg0) {
                // TODO Auto-generated method stub
                Log.d(TAG, "更新服务端多边形围栏回调接口消息 : " + arg0);
                JSONObject dataJson;
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        serverFenceId=dataJson.getInt("fence_id");
                        if(onFenceListener!=null){
                            onFenceListener.onUpdate(serverFenceId);
                        }
                    }else {
                        if(onFenceListener!=null){
                            onFenceListener.onFailed("关爱对象没有开启定位服务");
                        }
                    }
                } catch (JSONException e) {}

            }

            // 删除服务端围栏回调接口
            @Override
            public void onDeleteFenceCallback(String arg0) {
                // TODO Auto-generated method stub
                Log.d(TAG, "删除服务端围栏回调接口消息 : " + arg0);
                JSONObject dataJson;
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        for (Overlay overlay : overlays) {
                            overlay.remove();
                        }
                        cancelSetCircularFence();
                        queryServerFenceList();
                        serverFenceId = 0;
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    System.out.println("解析删除服务端围栏回调接口消息失败");
                }


            }

            // 查询服务端围栏列表回调接口
            @Override
            public void onQueryFenceListCallback(String arg0) {
                // TODO Auto-generated method stub

                Log.d(TAG,"fenceList : " + arg0);

                JSONObject dataJson;
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        if (dataJson.has("size")) {
                            JSONArray jsonArray = dataJson.getJSONArray("fences");
                            JSONObject jsonObj = jsonArray.getJSONObject(0);

                            int shape = jsonObj.getInt("shape");
                            if (1 == shape) {
                                JSONObject center = jsonObj.getJSONObject("center");
                                latitude = center.getDouble("latitude");
                                longitude = center.getDouble("longitude");
                                LatLng latLng = new LatLng(latitude, longitude);

                                if (0 == serverFenceId) {
                                    radius = (int) (jsonObj.getDouble("radius"));
                                    fenceOverlayOption = new CircleOptions().fillColor(0x000000FF).center(latLng)
                                            .stroke(new Stroke(5, Color.rgb(0xff, 0x00, 0x33)))
                                            .radius(radius);
                                }
                            } else if (2 == shape) {
                                Log.d(TAG,"fence_id : " + serverFenceId);
                                JSONArray vertexArray = jsonObj.getJSONArray("vertexes");
                                for (int i = 0; i < vertexArray.length(); ++i) {
                                    JSONObject vertex = vertexArray.getJSONObject(i);
                                    longitude = vertex.getDouble("longitude");
                                    latitude = vertex.getDouble("latitude");
                                    LatLng latLng = new LatLng(latitude, longitude);
                                    if (0 == serverFenceId) {
                                        vertexs.add(latLng);
                                    }
                                }
                                /*...........生成围栏。。。。。。。。*/
                                if (0 == serverFenceId) {
                                    fenceOverlayOption =
                                            new PolygonOptions().points(vertexs)
                                                    .stroke(new Stroke(vertexArray.length(), 0xAAFF0000))
                                                    .fillColor(0x30FFFFFF);
                                }
                            }

                            if (0 == serverFenceId) {
                                addMarker();
                            }

                            serverFenceId = jsonObj.getInt("fence_id");
                            if(baseDataListener!=null){
                                Log.d(TAG,"."+serverFenceId);
                                String mFenceName=jsonObj.getString("name");
                                baseDataListener.setFenceName(mFenceName);
                                Log.d(TAG,"."+mFenceName);
                                JSONArray valid_times= jsonObj.getJSONArray("valid_times");
                                Log.d(TAG,"."+valid_times);
                                JSONObject valid_time=valid_times!=null&&valid_times.length()>0?valid_times.getJSONObject(0):null;
                                Log.d(TAG,"."+valid_time);
                                String startTime=null;
                                String endTime=null;
                                String valid_day=null;
                                if(valid_time!=null){
                                     startTime=valid_time.getString("begin_time");
                                     endTime=valid_time.getString("end_time");
                                    baseDataListener.setStartTime(startTime);
                                    baseDataListener.setEndTime(endTime);
                                }
                                JSONArray valid_days=jsonObj.getJSONArray("valid_days");
                                if(valid_days!=null){
                                     valid_day=valid_days.toString();
                                    baseDataListener.setValidDays(valid_day);
                                }
                                Log.d(TAG,mFenceName+","+startTime+","+endTime+","+valid_day);

                            }

                            Log.d(TAG,"fence_id : " + serverFenceId);

                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG,"解析围栏列表回调消息失败");
                }

            }

            // 查询历史报警回调接口（服务端围栏）
            @Override
            public void onQueryHistoryAlarmCallback(String arg0) {
                // TODO Auto-generated method stub
                StringBuffer historyAlarm = new StringBuffer();
                JSONObject dataJson;
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        int size = dataJson.getInt("size");
                        for (int i = 0; i < size; ++i) {
                            JSONArray jsonArray = dataJson.getJSONArray("monitored_person_alarms");
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            String mPerson = jsonObj.getString("monitored_person");
                            int alarmSize = jsonObj.getInt("alarm_size");
                            JSONArray jsonAlarms = jsonObj.getJSONArray("alarms");
                            historyAlarm.append("监控对象[" + mPerson + "]报警信息\n");
                            for (int j = 0; j < alarmSize && j < jsonAlarms.length(); ++j) {
                                String action = jsonAlarms.getJSONObject(j).getInt("action") == 1 ? "进入" : "离开";
                                String date = DateUtils.getDate(jsonAlarms.getJSONObject(j).getInt("time"));
                                historyAlarm.append(date + " 【" + action + "】围栏\n");
                            }
                        }
                        if (TextUtils.isEmpty(historyAlarm)) {
                            Log.d(TAG, "未查询到历史报警信息");
                        } else {
                           Log.d(TAG, historyAlarm.toString());
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "解析查询历史报警回调消息失败");
                }

            }

            // 查询监控对象状态回调接口
            @Override
            public void onQueryMonitoredStatusCallback(String arg0) {
                // TODO Auto-generated method stub

                JSONObject dataJson;
                StringBuffer status = new StringBuffer();
                try {
                    dataJson = new JSONObject(arg0);
                    if (null != dataJson && dataJson.has("status") && 0 == dataJson.getInt("status")) {
                        int size = dataJson.getInt("size");
                        for (int i = 0; i < size; ++i) {
                            JSONArray jsonArray = dataJson.getJSONArray("monitored_person_statuses");
                            JSONObject jsonObj = jsonArray.getJSONObject(0);
                            String mPerson = jsonObj.getString("monitored_person");
                            int mStatus = jsonObj.getInt("monitored_status");
                            if (1 == mStatus) {
                                status.append("监控对象[ " + mPerson + " ]在围栏内\n");
                            } else if (2 == mStatus) {
                                status.append("监控对象[ " + mPerson + " ]在围栏外\n");
                            } else {
                                status.append("监控对象[ " + mPerson + " ]状态未知\n");
                            }
                        }
                        Log.d(TAG,  status.toString());
                    } else {
                        Log.d(TAG,"查询监控对象状态回调消息 : " + arg0);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "解析查询监控对象状态回调消息失败");
                }
            }
        };
    }




    /**
     * 取消设置围栏
     */
    public void cancelSetCircularFence(){
        vertexs.clear();

        serverFenceId=0;
        baiduMapManager.getmBaiduMap().setOnMapClickListener(null);
    }
    /**
     * 确定设置围栏
     */
    public void sureSetCircularFence(String mfenceName,String startTime,String endTime,String mValidDays, String monitoredPersons,OnFenceListener onFenceListener){
        this.onFenceListener=onFenceListener;
        for (Overlay overlay : overlays) {
            overlay.remove();
        }
        switch (fenceType) {

            // 服务端围栏
            case 0:
                if (0 == serverFenceId) {
                    Log.d(TAG,"百度创建围栏");
                        createServerVertexesFence(mfenceName,startTime,endTime,mValidDays,monitoredPersons);
                } else {
                    Log.d(TAG,"百度更新围栏");
                        updateServerVertexesFence(mfenceName,startTime,endTime,mValidDays,monitoredPersons);
                }
                break;
            default:
                break;
        }

        baiduMapManager.getmBaiduMap().setOnMapClickListener(null);
    }


    /**
     * 添加地图覆盖物（围栏）
     */
    protected void addMarker() {
        try {
            // 围栏覆盖物
            if (null != fenceOverlayOption) {
                fenceOverlay = baiduMapManager.getmBaiduMap().addOverlay(fenceOverlayOption);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public Geofence(Context context, BaiduMapManager trackApp) {
        initOnGeoFenceListener();
        this.baiduMapManager = trackApp;
        this.mContext = context;
        fenceOverlayOption=null;
        serverFenceId=0;
    }

    /**
     * 开始设置围栏
     */
    public void setfence(){
        vertexs.clear();
        fenceShape=1;
        fenceOverlayOption=null;
        if(fenceOverlay!=null){
            fenceOverlay.remove();
        }
        baiduMapManager.getmBaiduMap().setOnMapClickListener(mapClickListener);
    }

    /**
     * 自定义围栏创建或更新
     */
    public interface OnFenceListener{

        void onCreate(int fenceId);

        void onUpdate(int fenceId);

        void onFailed(String s);
    }

    private OnFenceListener onFenceListener;

    public interface OnHasChooseFenceListener{
        void onFinishChoose();
    }

    private OnHasChooseFenceListener onHasChooseFenceListener;

    public void setOnHasChooseFenceListener(OnHasChooseFenceListener onHasChooseFenceListener){
        this.onHasChooseFenceListener=onHasChooseFenceListener;

    }

    public void drawServerFence(List<LatLng> points,int fenceId){
        for (int i = 0; i < points.size(); ++i) {
            LatLng latLng = points.get(i);
            if (0 == serverFenceId) {
                vertexs.add(latLng);
            }
        }
                                /*...........生成围栏。。。。。。。。*/
        if (0 == serverFenceId) {
            fenceOverlayOption =
                    new PolygonOptions().points(vertexs)
                            .stroke(new Stroke(points.size(), 0xAAFF0000))
                            .fillColor(0x30FFFFFF);
        }
        if (0 == serverFenceId) {
            addMarker();
        }
        serverFenceId=fenceId;
    }






}
