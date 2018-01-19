package com.example.ldjg.pigknowclient.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ldjg on 2018/1/7.
 */

public class Gettime {
    public static String getthistime() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }

    public static String getMonthDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);    //得到前一个月
        String start = format.format(c.getTime())+" 00:00:00";
        return start;
    }

    public static String getWeekDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -6);
        String start = format.format(c.getTime())+" 00:00:00";
        return start;
    }

    public static String getthisdate() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }
}
