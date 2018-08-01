package com.zteguidedemo.v0840;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int WIFI_VIEW = 1;//wifi视图
    public static final int SIM_VIEW = 2;//SIM卡视图
    public static final int MYPHONE_VIEW = 3;//我的手机视图
    public static final int ACCOUNT_VIEW = 4;//账号视图
    public static final int FINGERPRINT_VIEW = 5;//指纹视图
    public static final int FINISH_VIEW = 6;//完成视图
    public static final int GUIDE_OVER = 7;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
