package com.wifi.vivoguide.y79a;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;

/**
 * vivo  y79a 引导主界面
 */
public class VivoY79AGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface {
    private Context context;
    private IGuideCallback iGuideCallback;
    private  View contentView = null;
    private boolean isSkipVivoServiceDesc = false;;//是否跳过显示服务说明页面
    public VivoY79AGuideMainView(Context context, IGuideCallback iGuideCallback) {
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
                contentView = new VivoLanguageChooseView_Y79a(context,this);
                break;
            case SERVICE_DES://服务说明
                contentView = new VivoServiceDesView_Y79a(context,isSkipVivoServiceDesc,this, new VivoServiceDesView_Y79a.onVivoClickListener() {
                    @Override
                    public void onAgreeClickListener(boolean isSkipVivoServiceDesc1) {
                        isSkipVivoServiceDesc = isSkipVivoServiceDesc1;
                    }
                });
                break;
            case WIFI_VIEW://wifi视图
                contentView = new VivoWifiSelectView_y79a(context,this);
                break;
            case LOGIN_VIVO_ACCOUNT://登录vivo账号
                contentView = new VivoAccountView_y79a(context,this);
            break;
            case SELECT_SERVICE://选择服务
                contentView = new VivoChooseServiceView_y79a(context,this);
            break;
            case FINISH_VIEW://完成视图
                contentView = new CongratulationsView_y79a(context,this);
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
        if (contentView  != null && contentView instanceof VivoServiceDesView_Y79a){
            ((VivoServiceDesView_Y79a)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoWifiSelectView_y79a){
            ((VivoWifiSelectView_y79a)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoAccountView_y79a){
            ((VivoAccountView_y79a)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoChooseServiceView_y79a){
             ((VivoChooseServiceView_y79a)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof CongratulationsView_y79a){
             ((CongratulationsView_y79a)contentView).onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
