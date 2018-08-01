package com.hissenseguide.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import java.util.List;

/**
 * Created by xuqunxing on 2017/11/23.
 */

public class Utils {

    public static boolean readSIMCard(Activity activity) {
        TelecomManager telecomManager = (TelecomManager) activity.getSystemService(Context.TELECOM_SERVICE);// 取得相关系统服务
        if(telecomManager != null){
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
            if (telecomManager == null || phoneAccountHandleList.size() <= 0) {
                System.out.println("请确认sim卡是否插入或者sim卡暂时不可用！");
                return false;
            } else {
                System.out.println("有SIM卡");
                return true;
            }
        }
        return false;
    }

    /**
     * 是否开启 wifi true：开启 false：关闭
     *
     * 一定要加入权限： <uses-permission
     * android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
     * <uses-permission
     * android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>
     * @param isEnable
     */
    public static void setWifi(Activity activity,boolean isEnable) {
        WifiManager mWm = null;
        if (mWm == null) {
            mWm = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
           // return;
        }
        System.out.println("wifi===="+mWm.isWifiEnabled());
        if (isEnable) {// 开启wifi
            if (!mWm.isWifiEnabled()) {
                mWm.setWifiEnabled(true);
            }
        } else {
            // 关闭 wifi
            if (mWm.isWifiEnabled()) {
                mWm.setWifiEnabled(false);
            }
        }
    }

    //是否连接WIFI
    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }

        return false ;
    }
}
