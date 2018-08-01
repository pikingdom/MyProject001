package com.wifi.xiaomiguide;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int MIUI_VIEW = 0;
    public static final int LANGUAGE_VIEW = 1;//语言视图

    public static final int WIFI_VIEW = 2;//wifi视图
    public static final int WIFI_CHECK_SET_INFO_VIEW = 3;//wifi查看设置信息视图
    public static final int USAGE_PRIVACY_VIEW = 4;
    public static final int SIM_CAR_VIEW = 5;
    public static final int NET_CONMUNICATION_VIEW = 6;
    public static final int OTHER_SETTING_VIEW = 7;
    public static final int PERSONNAL_STYLE = 8;//个性化风格

    public static final int FINISH_VIEW = 10;//完成视图
    public static final int GUIDE_OVER = 11;//完成整个流程结束

    public void setCurrentlyShowView(int viewType, Object bean);
    public void saveReturnDirection(float type);

}
