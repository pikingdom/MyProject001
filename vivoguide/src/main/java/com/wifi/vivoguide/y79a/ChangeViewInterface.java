package com.wifi.vivoguide.y79a;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int FINGLE_ACTION_1 = 1;//手势动作1
    public static final int FINGLE_ACTION_2 = 2;//手势动作2
    public static final int SERVICE_DES = 3;//服务说明
    public static final int WIFI_VIEW = 4;//wifi视图
    public static final int LOGIN_VIVO_ACCOUNT = 5;//登录vivo账号
    public static final int SELECT_SERVICE = 6;//选择服务
    public static final int IMPORT_DATA = 7;//导入数据
    public static final int FINISH_VIEW = 10;//完成视图
    public static final int GUIDE_OVER = 11;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
