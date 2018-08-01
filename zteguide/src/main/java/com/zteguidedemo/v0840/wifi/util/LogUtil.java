package com.zteguidedemo.v0840.wifi.util;

import android.util.Log;

/**
 * 对日志进行管理
 * 在DeBug模式开启，其它模式关闭
 * @author Llf
 */
public class LogUtil {

    public static boolean isDebug=false;

    /**
     * 错误
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg){
        if(isDebug){
            Log.e(tag, msg+"");
        }
    }
    /**
     * 信息
     * @param tag
     * @param msg
     */
    public static void i(String tag,String msg){
        if(isDebug){
            Log.i(tag, msg+"");
        }
    }

    /**
     * 警告
     * @param tag
     * @param msg
     */
    public static void w(String tag,String msg) {
        if (isDebug) {
            Log.w(tag, msg + "");
        }
    }
}
