package com.adapter.honoradapter.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public class UIUtil {
    private static Context mContext;
    private static double sDeviceSize = -1.0d;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static Context getAppContext() {
        return mContext;
    }

    public static void setAppContext(Context context) {
        mContext = context;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static Thread getUIThread() {
        return Looper.getMainLooper().getThread();
    }

    public static boolean isOnUIThread() {
        return Thread.currentThread() == getUIThread();
    }

    public static void runOnUIThread(Runnable action) {
        if (isOnUIThread()) {
            action.run();
        } else {
            getHandler().post(action);
        }
    }

    public static void runOnUIThreadDelay(Runnable action, long delayMillis) {
        getHandler().postDelayed(action, delayMillis);
    }

    public static void hideNavigationBar(Window window) {
        if (5890 != window.getDecorView().getSystemUiVisibility()) {
            Log.d("HwStartupGuide", "hideNavigationBar");
            window.getDecorView().setSystemUiVisibility(5890);
        }
    }

    public static void showNavigationBar(Window window) {
        if (5376 != window.getDecorView().getSystemUiVisibility()) {
            Log.d("HwStartupGuide", "showNavigationBar");
            window.getDecorView().setSystemUiVisibility(5376);
        }
    }

    public static void setupWindowFullScreen(Window window) {
        window.getDecorView().setSystemUiVisibility(5894);
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            statusBarHeight = context.getResources().getDimensionPixelSize(((Integer) c.getField("status_bar_height").get(c.newInstance())).intValue());
        } catch (RuntimeException e) {
            Log.e("HwStartupGuide", "RuntimeException", e);
        } catch (Exception e2) {
            Log.e("HwStartupGuide", "Exception", e2);
        }
        Log.i("HwStartupGuide", "statusBarHeight = " + statusBarHeight);
        return statusBarHeight;
    }

    private static double calculateDeviceSize(Context context) {
        if (-1.0d != sDeviceSize) {
            return sDeviceSize;
        }
        if (context == null) {
            return -1.0d;
        }
        WindowManager manager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getRealMetrics(displayMetrics);
        sDeviceSize = Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
        Log.i("HwStartupGuide", "calculateDeviceSize() sDeviceSize:" + sDeviceSize);
        return sDeviceSize;
    }

    public static double getDeviceSize(Context context) {
        if (-1.0d == sDeviceSize) {
            return calculateDeviceSize(context);
        }
        return sDeviceSize;
    }

    public static boolean isPadScreenDevice(Context context) {
        Log.i("HwStartupGuide", "isPadScreenDevice --sDeviceSize =  " + sDeviceSize);
        return getDeviceSize(context) > 8.0d;
    }

    public static boolean isLandScreen(Context context) {
        boolean island = false;
        if (context.getResources().getConfiguration().orientation == 2) {
            island = true;
        }
        Log.i("HwStartupGuide", "isLandScreen---island = " + island);
        return island;
    }
}