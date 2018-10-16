package com.wifi.vivoguide.y85a;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.wifi.vivoguide.y79.CongratulationsView_y79;
import com.wifi.vivoguide.y79.VivoChooseServiceView_y79;
import com.wifi.vivoguide.y79.VivoImportData_y79;
import com.wifi.vivoguide.y79.VivoServiceDesView_Y79;
import com.wifi.vivoguide.y79.VivoWifiSelectView_y79;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**
 * vivo  y85a 引导主界面
 */
public class VivoY85aGuideMainView extends LinearLayout implements IGuideView,ChangeViewInterface {
    private Context context;
    private IGuideCallback iGuideCallback;
    private  View contentView = null;
    private boolean isSkipVivoServiceDesc = false;;//是否跳过显示服务说明页面
    public VivoY85aGuideMainView(Context context, IGuideCallback iGuideCallback) {
        super(context);
        this.context = context;
        this.iGuideCallback = iGuideCallback;
        this.setBackgroundColor(Color.parseColor("#fff6f6f6"));
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
                contentView = new VivoLanguageChooseView_Y85a(context,this);
                break;
            case FINGLE_ACTION_1:
                contentView = new VivoFingelAction1_y85a(context,this);
                break;
            case FINGLE_ACTION_2:
                contentView = new VivoFingelAction2_y85a(context,this);
                break;
            case SERVICE_DES://服务说明
                contentView = new VivoServiceDesView_Y79(context,isSkipVivoServiceDesc,this, new VivoServiceDesView_Y79.onVivoClickListener() {
                    @Override
                    public void onAgreeClickListener(boolean isSkipVivoServiceDesc1) {
                        isSkipVivoServiceDesc = isSkipVivoServiceDesc1;
                    }
                });
                break;
            case WIFI_VIEW://wifi视图
                contentView = new VivoWifiSelectView_y79(context,this);
                break;
            case SELECT_SERVICE://选择服务
                contentView = new VivoChooseServiceView_y79(context,this);
            break;
            case IMPORT_DATA://导入数据
                contentView = new VivoImportData_y79(context,this);
            break;
            case FINISH_VIEW://完成视图
                contentView = new CongratulationsView_y79(context,this);
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
        if (contentView  != null && contentView instanceof VivoServiceDesView_Y79){
            ((VivoServiceDesView_Y79)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoWifiSelectView_y79){
            ((VivoWifiSelectView_y79)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoChooseServiceView_y79){
             ((VivoChooseServiceView_y79)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof VivoImportData_y79){
            ((VivoImportData_y79)contentView).onBackPressed();
        }else if (contentView  != null && contentView instanceof CongratulationsView_y79){
             ((CongratulationsView_y79)contentView).onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
