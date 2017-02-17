package com.ange.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class TimeUtils {
    public static final String TIME_FORMAT_yyyy_MM_dd_hh_mm_ss="yyyy-MM-dd HH:mm:ss";

    public static int getCurrentMonth(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int month = c.get(Calendar.MONTH)+1;
        return month;
    }

    /**
     * 获取当前年
     * @return
     */
    public static int getCurrentYear(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getCurrentDay(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 判断是否闰年
     * @return
     */

    public static boolean isLeapYear(){
       int year= getCurrentYear();

        if((year%4==0&&year%100!=0)||year%400==0){
            return true;
        }else {
            return false;
        }
    }

    public static int getDayOfMonth(){
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day=aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }

    public static String getCurrentTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String d=new SimpleDateFormat(TIME_FORMAT_yyyy_MM_dd_hh_mm_ss).format(c.getTime());
        return d;
    }

    public static String getTime(long time){

        String d=new SimpleDateFormat(TIME_FORMAT_yyyy_MM_dd_hh_mm_ss).format(new Date(time));
        return d;
    }



}
