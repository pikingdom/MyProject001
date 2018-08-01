package com.huawei.hwstartupguide;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int SIM_VIEW = 1;//SIM卡视图
    public static final int PROTOCOL_VIEW = 2;//协议视图
    public static final int ACCESS_SWITCH_VIEW = 3;//权限开关
    public static final int SERVICE_ACCESS_EXPLAIN_VIEW = 4;//服务权限说明
    public static final int WIFI_VIEW = 5;//wifi视图
    public static final int DATA_MIGRATION_VIEW = 6;//数据迁移视图
    public static final int CLOUD_SERVICE_VIEW = 7;//云服务视图
    public static final int FINGERPRINT_TOUCH_VIEW = 8;//指纹触控视图
    public static final int FINGERPRINT_CHECK_VIEW = 9;//指纹校验视图
    public static final int FINGERPRINT_BEFORE_START_VIEW = 10;//启用指纹前请设置锁屏密码
    public static final int FINISH_VIEW = 11;//完成视图
    public static final int GUIDE_OVER = 12;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
