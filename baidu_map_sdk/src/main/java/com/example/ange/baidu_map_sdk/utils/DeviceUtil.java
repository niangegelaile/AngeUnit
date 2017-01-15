package com.example.ange.baidu_map_sdk.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2016/11/29 0029.
 */

public class DeviceUtil {
   public static String getDeviceId(Context context){
       TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
       return tm.getDeviceId();
    }


}
