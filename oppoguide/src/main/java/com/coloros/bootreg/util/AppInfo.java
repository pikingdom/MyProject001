package com.coloros.bootreg.util;

import android.text.TextUtils;



import org.json.JSONException;
import org.json.JSONObject;

public class AppInfo {
    public String appCode = "";
    public int appVersion;
    public String packageName;
    public String secreKey = "";

    public AppInfo(String appcode, String screctkey, String pkgName, int appVersion) {
        this.appCode = appcode;
        this.secreKey = screctkey;
        this.packageName = pkgName;
        this.appVersion = appVersion;
    }

    public AppInfo() {
        
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getSecreKey() {
        return this.secreKey;
    }

    public void setSecreKey(String secreKey) {
        this.secreKey = secreKey;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public static AppInfo fromJson(String body) {
        if (TextUtils.isEmpty(body)) {
            return null;
        }
        AppInfo info = new AppInfo();
        try {
            JSONObject jsonObject = new JSONObject(body);
            if (!(jsonObject.isNull("appCode") || jsonObject.get("appCode") == JSONObject.NULL)) {
                info.setAppCode(jsonObject.getString("appCode"));
            }
            if (!(jsonObject.isNull("secreKey") || jsonObject.get("secreKey") == JSONObject.NULL)) {
                info.setSecreKey(jsonObject.getString("secreKey"));
            }
            if (!(jsonObject.isNull("packageName") || jsonObject.get("packageName") == JSONObject.NULL)) {
                info.setPackageName(jsonObject.getString("packageName"));
            }
            if (jsonObject.isNull("appVersion") || jsonObject.get("appVersion") == JSONObject.NULL) {
                return info;
            }
            info.setAppVersion(jsonObject.getInt("appVersion"));
            return info;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(AppInfo entity) {
        String str = null;
        if (entity != null) {
            try {
                JSONObject jObject = new JSONObject();
                jObject.put("appCode", entity.getAppCode());
                jObject.put("secreKey", entity.getSecreKey());
                jObject.put("packageName", entity.getPackageName());
                jObject.put("appVersion", entity.getAppVersion());
                str = jObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public int getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }
}