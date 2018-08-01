package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;

/**
 * 华为引导主界面
 * 作者：xiaomao on 2017/7/20.
 */

public class GioneeGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface {
    private Context context;
    private IGuideCallback iGuideCallback;
    private  View contentView = null;

    public GioneeGuideMainView(Context context, IGuideCallback iGuideCallback) {
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
        switch (type){
            case LANGUAGE_VIEW://语言视图
                //todo 形如这样创建视图：contentView = new LanguageView(context,this);然后public LanguageView(Context,ChangeViewInterface)
                contentView = new LanguageSelectView(context,this);
                break;
            case WIFI_VIEW://wifi 页面
                contentView = new WifiSettingView(context,this);
                break;
            case GIONEE_ACCOUNT://注册金立会员
                contentView = new GineeAccountView(context,this);
                break;
            case TERM_OF_USE://用户协议
                contentView = new TermOfUseView(context,this);
                break;
            case FINISH_VIEW://完成视图
                contentView = new CompleteView(context,this);
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
        try {
            if (contentView  != null && contentView instanceof TermOfUseView){
                ((TermOfUseView)contentView).onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
