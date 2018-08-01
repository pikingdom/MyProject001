package com.wifi.xiaomiguide.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesUtil {
	public static String SAVE_WIFI_CONNECTED_INFO = "save_wifi_connected_info";


	public static boolean getBoolean(Context ctx, String key) {
		return getSharedPreferences(ctx).getBoolean(key, false);
	}

	public static boolean getBoolean(Context ctx, String key, boolean defValue) {
		if (ctx == null) {
			return defValue;
		}
		return getSharedPreferences(ctx).getBoolean(key, defValue);
	}

	public static void putBoolean(Context ctx, String key, boolean value) {
		if (ctx == null) {
			return;
		}
		getSharedPreferences(ctx).edit().putBoolean(key, value).commit();
	}
	
	public static long getLong(Context ctx, String key, long defValue) {
		return getSharedPreferences(ctx).getLong(key, defValue);
	}

	public static void putLong(Context ctx, String key, long value) {
		getSharedPreferences(ctx).edit().putLong(key, value).commit();
	}

	public static String getString(Context ctx, String key) {
		return getSharedPreferences(ctx).getString(key, "");
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		return getSharedPreferences(ctx).getString(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		getSharedPreferences(ctx).edit().putString(key, value).commit();
	}


	public static SharedPreferences getSharedPreferences(Context ctx) {
		return ctx.getSharedPreferences("SYSTEM", Activity.MODE_PRIVATE);
	}
}