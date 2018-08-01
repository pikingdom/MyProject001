package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.wifi.xiaomiguide.Bean.WifiBean;
import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.Util.LogUtil;

public class MIUIGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface {

    private Context context;
    private IGuideCallback iGuideCallback;
    private View contentView = null;
    private boolean mIsFirstStartView = false;
    private boolean mIsBackToMIUIView = false;
    private float mDirection = AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT;

    public MIUIGuideMainView(Context context, IGuideCallback iGuideCallback) {
        super(context);
        this.context = context;
        this.iGuideCallback = iGuideCallback;
        this.setBackgroundColor(Color.parseColor("#fffcfcfc"));
        mIsFirstStartView = true;
        setCurrentlyShowView(MIUI_VIEW, null);
    }


    @Override
    public void setCurrentlyShowView(int viewType, Object obj) {
        switch (viewType){
            case MIUI_VIEW:
                mIsFirstStartView = true;
                contentView = new MiuiView(context,this);
                break;
            case LANGUAGE_VIEW:  //语言视图
                if (obj != null) {
                    contentView = new LanguageSelectView(context, this, (int) obj);
                }
                break;
            case WIFI_VIEW:  //WIFI视图
                contentView = new WifiSettingView(context,this);
                break;

            case WIFI_CHECK_SET_INFO_VIEW:  //WIFI查看设置信息视图
                if (obj != null) {
                    WifiSetInfoView1 wifiSetInfoView = new WifiSetInfoView1(context, this);
                    WifiBean wifiBean = (WifiBean)obj;
                    wifiSetInfoView.setWifiBeanData(wifiBean);
                    contentView = wifiSetInfoView;
                }

                break;
            case USAGE_PRIVACY_VIEW:
                contentView = new UsagePrivacyView(context, this);
                break;
            case SIM_CAR_VIEW:
                contentView = new SimCarView(context, this);
                break;
            case NET_CONMUNICATION_VIEW:
                contentView = new NetConmunicationView(context, this);
                break;
            case OTHER_SETTING_VIEW:
                contentView = new OtherSettingView(context, this);
                break;
            case FINISH_VIEW:
                contentView = new ComplateView(context, this);
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
        if (!mIsFirstStartView) {
            if (mDirection == AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT) {
                AnimationTranslateBusiness.setAnimationTranslate(context, contentView, AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            } else {
                AnimationTranslateBusiness.setAnimationTranslate(context, contentView, AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            }
        }else {
            mIsFirstStartView = false;
        }

        addView(contentView,lp);
    }

    @Override
    public void saveReturnDirection(float direction) {
        mDirection = direction;
    }

    @Override
    public void onBackPressed() {
        LogUtil.i("====llf", "onBackPressed");
        if (contentView == null) return;

        if (contentView instanceof WifiSettingView || contentView instanceof WifiSetInfoView1) {
            setCurrentlyShowView(LANGUAGE_VIEW, 0);
        } else if (contentView instanceof LanguageSelectView) {
            mIsBackToMIUIView = true;
            setCurrentlyShowView(MIUI_VIEW, null);
        }else if (contentView instanceof UsagePrivacyView) {
            ((UsagePrivacyView)contentView).hideChilderView();
        }else if (contentView instanceof SimCarView) {
            setCurrentlyShowView(USAGE_PRIVACY_VIEW, null);
        }else if (contentView instanceof NetConmunicationView) {
            boolean isDoBack = ((NetConmunicationView) contentView).onBackPressed();
            if(isDoBack){
                setCurrentlyShowView(SIM_CAR_VIEW, null);
            }
        }else if (contentView instanceof OtherSettingView) {
            setCurrentlyShowView(NET_CONMUNICATION_VIEW, null);
        }else if (contentView instanceof ComplateView) {
            setCurrentlyShowView(OTHER_SETTING_VIEW, null);
        }

        if (mIsBackToMIUIView) return;
        mIsBackToMIUIView = false;
        AnimationTranslateBusiness.setAnimationTranslate(context, contentView, AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("====llf", "onActivityResult");
    }
}
