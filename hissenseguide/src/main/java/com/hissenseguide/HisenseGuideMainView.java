package com.hissenseguide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.hissenseguide.listener.ChangeViewInterface;

/**
 * 华为引导主界面
 * 作者：xiaomao on 2017/7/20.
 */
public class HisenseGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface {
    private Context context;
    private IGuideCallback iGuideCallback;
    private  View contentView = null;

    public HisenseGuideMainView(Context context, IGuideCallback iGuideCallback) {
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
        removeAllViews();
        switch (type){
            case LANGUAGE_VIEW://语言视图
                //todo 形如这样创建视图：contentView = new LanguageView(context,this);然后public LanguageView(Context,ChangeViewInterface)
                contentView = new LanguageSelectViewHissense(context,this);
                break;
            case AGREE_TERMS://使用条款
                contentView = new HiSenseE77_GuideAgreementAndTermsView(context,this);
                break;
            case LOGIN_HISENSE_ACCOUNT://登录海信账号
                contentView = new LoginHisenseAccoutView(context,this);
                break;
            case DATA_SETTING://权限开关
                contentView = new GuiDataSettingView(context,this);
                break;
            case FEATURED://服务权限说明
                contentView = new GuideFeaturedFeaturesView(context,this);
                break;
            case WIFI_VIEW://wifi视图
                break;
            case FINISH_VIEW://完成视图
                contentView = new GuideCompleteView(context,this);
                break;
            default:
                contentView = null;
                break;
        }
        if (contentView == null && iGuideCallback != null){
            this.setBackgroundColor(Color.TRANSPARENT);
            iGuideCallback.onComplete();
            return;
        }
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(contentView,lp);
    }

    @Override
    public void onBackPressed() {
//        if (contentView  != null && contentView instanceof LanguageSelectView){
//            //((LanguageSelectView)contentView).onBackPressed();
//        }else if (contentView  != null && contentView instanceof SimCarView){
//         //   ((SimCarView)contentView).onBackPressed();
//        }else if (contentView  != null && contentView instanceof TermsAndConditionsView){
//           // ((TermsAndConditionsView)contentView).onBackPressed();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LoginHisenseAccoutView.WIFICALLBACK){
            if(contentView != null && contentView instanceof LoginHisenseAccoutView){
                ((LoginHisenseAccoutView)contentView).onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
