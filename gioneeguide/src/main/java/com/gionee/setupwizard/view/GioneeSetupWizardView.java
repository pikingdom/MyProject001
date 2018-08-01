package com.gionee.setupwizard.view;

import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;

/**
 * 作者：xiaomao on 2017/7/14.
 */

public class GioneeSetupWizardView extends LinearLayout{
    public static final int LANGUAGE_VIEW = 0;//语言视图
    public static final int WIFI_VIEW = 1;//wifi视图
    public static final int TIME_VIEW = 2;//时间视图
    public static final int INPUT_METHOD_VIEW = 3;//输入法视图
    public static final int ACCOUNT_VIEW = 4;//账号视图
    public static final int FINISH_VIEW = 5;//完成视图
    public static final int OVER = 6;//完成整个流程结束

    private Context context;
    private IGuideCallback iGuideCallback;
    public GioneeSetupWizardView(Context context,IGuideCallback iGuideCallback) {
        super(context);
        this.context = context;
        this.iGuideCallback = iGuideCallback;
        this.setBackgroundColor(Color.parseColor("#fffbfbfb"));
        setCurrentlyShowView(LANGUAGE_VIEW);
    }

    /**
     * 选择当前显示的视图
     */
    public void setCurrentlyShowView(int type){
        View contentView = null;
        switch (type){
            case LANGUAGE_VIEW://语言视图
                contentView = new LanguageView(context,this);
                break;
            case WIFI_VIEW://wifi视图
                LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//开了GPS就给wifi界面，没开就直接跳过
                    contentView = new WifiSettingView(context,this);
                    break;
                }
            case TIME_VIEW://时间视图
                contentView = new TimeSettingView(context,this);
                break;
            case INPUT_METHOD_VIEW://输入法视图
                contentView = new InputMethodView(context,this);
                break;
            case ACCOUNT_VIEW://账号视图
                contentView = new AccountView(context,this);
                break;
            case FINISH_VIEW://完成视图
                contentView = new FinishView(context,this);
                break;
            default:
                contentView = null;
                break;
        }
        if (contentView == null && iGuideCallback != null){
            iGuideCallback.onComplete();
            return;
        }
        removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(contentView,lp);
    }
}
