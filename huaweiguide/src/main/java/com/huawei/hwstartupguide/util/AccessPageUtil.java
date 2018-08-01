package com.huawei.hwstartupguide.util;

import android.content.Context;
import android.content.Intent;

public class AccessPageUtil {

    /**
     * 数据迁移-从手机助手恢复
     *
     * @param context
     * @return
     */
    public static boolean OOBEMigrateFromPhoneHelper(Context context) {
        try {
            Intent intent = new Intent("com.hicloud.android.clone.action.hisuit.startup");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 数据迁移-从旧设备恢复
     *
     * @param context
     * @return
     */
    public static boolean OOBEMigrateFromClone(Context context) {
        try {
            Intent intent = new Intent("com.hicloud.android.clone.action.receive.startup");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
