package com.hissenseguide.listener;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int AGREE_TERMS = 1;//使用条款
    public static final int LOGIN_HISENSE_ACCOUNT = 2;//登录海信账号
    public static final int DATA_SETTING = 3;//数据连接
    public static final int FEATURED = 4;//特色功能
    public static final int WIFI_VIEW = 5;//wifi视图
    public static final int FINISH_VIEW = 10;//完成视图
    public static final int GUIDE_OVER = 11;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
