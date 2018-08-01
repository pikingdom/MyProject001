package com.coloros.bootreg.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetInfoHelper {


    public static final String SSOID_DEFAULT = "0";

    public static boolean isWifiUploading(Context context) {
        try {
            if (((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo().getTypeName().toLowerCase().equals("wifi")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean is3GUploading(Context context) {
        try {
            NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            String typeName = info.getTypeName().toLowerCase();
            if (typeName.equals(ParUtil.THROW_MOBILE)) {
                typeName = info.getExtraInfo().toLowerCase();
            }
            if ("3gnet".equals(typeName) || "3gwap".equals(typeName)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isConnectNet(Context context) {
        boolean z = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            if (cm.getActiveNetworkInfo() != null) {
                z = cm.getActiveNetworkInfo().isAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public static String netType(Context context) {
        try {
            NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            String typeName = info.getTypeName().toLowerCase();
            if (typeName.equals(ParUtil.THROW_MOBILE)) {
                typeName = info.getExtraInfo().toUpperCase();
            }
            return typeName.toUpperCase();
        } catch (Exception e) {
            return SSOID_DEFAULT;
        }
    }

    public static String getNetType(Context context) {
        String netType = SSOID_DEFAULT;
        try {
            NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (info != null) {
                netType = info.getTypeName().toUpperCase();
                if ("MOBILE".equalsIgnoreCase(netType)) {
                    String extraInfo = info.getExtraInfo();
                    if (!TextUtils.isEmpty(extraInfo)) {
                        netType = extraInfo.toUpperCase();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netType;
    }

    public static int getNetTypeId(Context context) {
        try {
            String typeName = getNetType(context);
            if (typeName.equals("3GNET")) {
                return 3;
            }
            if (typeName.equals("3GWAP")) {
                return 4;
            }
            if (typeName.equals("UNINET")) {
                return 5;
            }
            if (typeName.equals("UNIWAP")) {
                return 6;
            }
            if (typeName.equals("CMNET")) {
                return 7;
            }
            if (typeName.equals("CMWAP")) {
                return 8;
            }
            if (typeName.equals("CTNET")) {
                return 9;
            }
            if (typeName.equals("CTWAP")) {
                return 10;
            }
            if (typeName.equals("WIFI")) {
                return 2;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isMobileActive(Context ctx) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) ctx.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo == null || networkInfo.getType() != 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}