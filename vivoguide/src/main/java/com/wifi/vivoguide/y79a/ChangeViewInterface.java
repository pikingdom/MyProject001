package com.wifi.vivoguide.y79a;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int SERVICE_DES = 1;//服务说明
    public static final int WIFI_VIEW = 2;//wifi视图
    public static final int LOGIN_VIVO_ACCOUNT = 3;//登录vivo账号
    public static final int SELECT_SERVICE = 4;//选择服务
    public static final int IMPORT_DATA = 5;//导入数据
    public static final int FINISH_VIEW = 10;//完成视图
    public static final int GUIDE_OVER = 11;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
