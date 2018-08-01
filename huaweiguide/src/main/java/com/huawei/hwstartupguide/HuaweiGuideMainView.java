package com.huawei.hwstartupguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import com.adapter.honoradapter.view.DataMigration;
import com.adapter.honoradapter.view.FingerprintTouchView;
import com.adapter.honoradapter.view.FingerprintVerifyView;
import com.adapter.honoradapter.view.LanguageSelectView;
import com.adapter.honoradapter.view.SimCarView;
import com.adapter.honoradapter.view.TermsAndConditionsView;
import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.bootreg.util.SetupWizardBranchController;
import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;
import com.huawei.hwstartupguide.ui.wifi.WifiSettingView;

/**
 * 华为引导主界面
 * 作者：xiaomao on 2017/7/20.
 */

public class HuaweiGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface{
    private Context context;
    private IGuideCallback iGuideCallback;
    private  View contentView = null;

    public HuaweiGuideMainView(Context context,IGuideCallback iGuideCallback) {
        super(context);
        this.context = context;
        this.iGuideCallback = iGuideCallback;
        this.setBackgroundColor(Color.parseColor("#fffcfcfc"));
        setCurrentlyShowView(LANGUAGE_VIEW);
    }

    /**
     * 选择当前显示的视图
     */
    @Override
    public void setCurrentlyShowView(int type){

        //是否有指纹硬件支持
        boolean supportedFingerPrint = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService("fingerprint");
                supportedFingerPrint = fingerprintManager.isHardwareDetected();
            } catch (Exception e) {
            }
        }

        switch (type){
            case LANGUAGE_VIEW://语言视图
                contentView = new LanguageSelectView(context,this);
                break;
            case SIM_VIEW://SIM卡视图
                contentView = new SimCarView(context,this);
                break;
            case PROTOCOL_VIEW://协议视图
                contentView = new TermsAndConditionsView(context,this);
                break;
            case ACCESS_SWITCH_VIEW://权限开关
                //llf
                contentView = new ApplicationAuthorityView(context,this);
                break;
            case SERVICE_ACCESS_EXPLAIN_VIEW://服务权限说明
                contentView = new ServiceAuthorityView(context,this);
                break;
            case WIFI_VIEW://wifi视图
                contentView = new WifiSettingView(context,this);
                break;
            case DATA_MIGRATION_VIEW://数据迁移视图
                if(SetupWizardBranchController.is2345(context)){
                    contentView = new DataMigration(context,this);
                    break;
                }
            case CLOUD_SERVICE_VIEW://云服务视图
                contentView = new YunServiceView(context,this);
                break;
            case FINGERPRINT_TOUCH_VIEW://指纹触控视图
                if (SetupWizardBranchController.is2345(context) && supportedFingerPrint) {
                    contentView = new FingerprintTouchView(context, this);
                    break;
                }
            case FINGERPRINT_CHECK_VIEW://指纹校验视图
                if (SetupWizardBranchController.is2345(context) && supportedFingerPrint) {
                    contentView = new FingerprintVerifyView(context, this);
                    break;
                }
            case FINGERPRINT_BEFORE_START_VIEW://启用指纹前请设置锁屏密码
//                contentView = new FingerprintBeforeStartView(context,this);
//                break;
            case FINISH_VIEW://完成视图
                contentView = new CompletePage(context,this);
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

    @Override
    public void onBackPressed() {
        if (contentView  != null && contentView instanceof LanguageSelectView){
            ((LanguageSelectView)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof SimCarView){
            ((SimCarView)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof TermsAndConditionsView){
            ((TermsAndConditionsView)contentView).onBackPressed();
        } else if (contentView  != null && contentView instanceof DataMigration){
            ((DataMigration)contentView).onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
          if (contentView  != null && contentView instanceof WifiSettingView){
              ((WifiSettingView)contentView).onActivityResult(requestCode,resultCode,data);
          }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int flag = ((Activity)context).getWindow().getDecorView().getSystemUiVisibility();
        if (contentView instanceof NavigationVisibilityChange) {
            if ((flag & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {//没有导航条
                ((NavigationVisibilityChange)contentView).NavigationVisibilityChangeState(false);
            } else {//有导航条
                ((NavigationVisibilityChange)contentView).NavigationVisibilityChangeState(true);
            }
        }
    }
}
