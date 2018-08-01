package com.adapter.honoradapter.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by xuqunxing on 2017/7/20.
 */
public class OOBEUtils {

    public static boolean isInternal() {
        //return "zh".equals(SystemProperties.get("ro.product.locale.language")) ? "CN".equals(SystemProperties.get("ro.product.locale.region")) : false;
        return false;
    }

    public static void killApp(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        try {
            am.getClass().getDeclaredMethod("forceStopPackage", new Class[]{String.class}).invoke(am, new Object[]{context.getPackageName()});
        } catch (IllegalArgumentException e) {
            Log.e("HwStartupGuide", "IllegalArgumentException ", e);
        } catch (NoSuchMethodException e2) {
            Log.e("HwStartupGuide", "NoSuchMethodException ", e2);
        } catch (IllegalAccessException e3) {
            Log.e("HwStartupGuide", "IllegalAccessException ", e3);
        } catch (InvocationTargetException e4) {
            Log.e("HwStartupGuide", "InvocationTargetException ", e4);
        }
    }

    public static boolean hasPackageInfo(PackageManager manager, String name) {
        try {
            manager.getPackageInfo(name, PackageManager.GET_META_DATA);//128
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean hasIntentActivity(PackageManager manager, Intent intent) {
        List<ResolveInfo> homes = manager.queryIntentActivities(intent, 0);
        if (homes == null || homes.size() == 0) {
            return false;
        }
        return true;
    }

    public static boolean isHasHwID(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm != null ? hasIntentActivity(pm, new Intent("com.huawei.hwid.START_BY_OOBE")) : false;
    }

    public static boolean isPhonefinderInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent("com.huawei.remotecontrol.intent.action.ACTIVATION");
        intent.setPackage("com.huawei.hidisk");
        boolean hasIntentActivity = pm != null ? hasIntentActivity(pm, intent) : false;
        Log.i("HwStartupGuide", "OOBEUtils : isHasPhonefinderActivation = " + hasIntentActivity);
        return hasIntentActivity;
    }

    public static boolean isHasPhonefinderStart(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent("com.huawei.remotecontrol.intent.action.PHONEFINDER_OOBE");
        intent.setPackage("com.huawei.hidisk");
        boolean hasIntentActivity = pm != null ? hasIntentActivity(pm, intent) : false;
        Log.i("HwStartupGuide", "OOBEUtils : isHasPhonefinderStart = " + hasIntentActivity);
        return hasIntentActivity;
    }




}
