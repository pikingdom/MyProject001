package com.zteguidedemo.v0840;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.zteguidedemo.v0840.wifi.view.ZTEWifiSettingView;


/**
 * 中兴V0840引导主入口
 * 作者：xiaoo on 2017/8/21.
 */

public class ZTEGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface{
    private Context context;
    private IGuideCallback iGuideCallback;
    private View contentView = null;

    public ZTEGuideMainView(Context context,IGuideCallback iGuideCallback) {
        super(context);
        this.context = context;
        this.iGuideCallback = iGuideCallback;
        this.setBackgroundColor(Color.parseColor("#fffafafa"));
        setCurrentlyShowView(LANGUAGE_VIEW);
    }

    @Override
    public void setCurrentlyShowView(int viewType) {
        switch (viewType){
            case LANGUAGE_VIEW://语言
                contentView = new ZTE0840LanguageSelectView(getContext(),this);
                break;
            case WIFI_VIEW://wifi
                contentView = new ZTEWifiSettingView(getContext(),this);
                break;
            case SIM_VIEW://SIM
                contentView = new ZTE0840SimView(getContext(),this);
                break;
            case MYPHONE_VIEW://我的手机
                contentView = new ZTE0840MyPhoneView(getContext(),this);
                break;
            case ACCOUNT_VIEW://账号
                contentView = new ZTEAccountView(context,this);
                break;
            case FINGERPRINT_VIEW://指纹
                contentView = new ZTEFingerprintView(context,this);
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

    @Override
    public void onBackPressed() {
        if(contentView instanceof  ZTE0840MyPhoneView){
            ((ZTE0840MyPhoneView)contentView).onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
