package com.gionee.setupwizard.s10bl;

/**
 * 选择视图接口
 * 作者：xiaomao on 2017/7/20.
 */

public interface ChangeViewInterface {
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int WIFI_VIEW = 1;//SIM卡视图
    public static final int GIONEE_ACCOUNT = 2;//金立会员
    public static final int TERM_OF_USE = 3;//使用条款
    public static final int FINISH_VIEW = 4;//完成视图
    public static final int OVER = 5;//完成整个流程结束
    public void setCurrentlyShowView(int viewType);
}
