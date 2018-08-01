package com.adapter.honoradapter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.honoradapter.util.UIUtil;
import com.huawei.hwstartupguide.ChangeViewInterface;
import com.huawei.hwstartupguide.R;
import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FingerprintVerifyView extends FrameLayout implements View.OnClickListener,NavigationVisibilityChange {
    private TextView mPrevBtn;
    private TextView mNextBtn;
    private RelativeLayout buttonBar;
    private ChangeViewInterface changeViewInterface;

    public FingerprintVerifyView(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        this.changeViewInterface = changeViewInterface;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.page_fingerprint_verify,this);
        initButtonBar();
        setLogoViewHeight();
    }
    private void setLogoViewHeight() {
        View logoView = findViewById(R.id.logo_view);
        View simLogo = findViewById(R.id.logo);
        if (logoView != null && simLogo != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int logoViewHeight;
            if (!UIUtil.isPadScreenDevice(getContext())) {
                logoViewHeight = (displayMetrics.widthPixels * 4) / 5;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 4) / 25;
            } else if (UIUtil.isLandScreen(getContext())) {
                logoViewHeight = (displayMetrics.widthPixels * 268) / 1000;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 13) / 100;
            } else {
                logoViewHeight = (displayMetrics.widthPixels * 62) / 100;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 38) / 100;
            }
        }
    }

    protected void initButtonBar() {
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);
        this.mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        this.mPrevBtn.setVisibility(View.VISIBLE);
        this.mPrevBtn.setText(R.string.skip_format);
        this.mPrevBtn.setOnClickListener(this);
        this.mNextBtn = (TextView) findViewById(R.id.next_btn);
        this.mNextBtn.setVisibility(View.VISIBLE);
        this.mNextBtn.setText(R.string.fingerverify_start);
        this.mNextBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == mPrevBtn) {
            //跳过
            if (changeViewInterface != null) {
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
            }
        } else if (v == mNextBtn) {
            //启用指纹
//            intentToFinger();
//            if(changeViewInterface != null){
//                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_BEFORE_START_VIEW);
//            }
            jumpToFingerprintSetting();
            if (changeViewInterface != null) {
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
            }
        }
    }

    private boolean jumpToFingerprintSetting(){
        try {
            Intent jumpToFS = new Intent();
            jumpToFS.setClassName("com.android.settings", "com.android.settings.fingerprint.FingerprintSettingsActivity");
            jumpToFS.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(jumpToFS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void intentToFinger() {
            Intent intent = new Intent();
            intent.setAction("com.android.settings.ACTION_FINGERPRINT_START");
            intent.setPackage("com.android.settings");
            Context context = getContext();
            if(context != null){
                ((Activity)context).startActivityForResult(intent, 1);
                ((Activity)context).overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_from_alpah);
             }
    }


    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) buttonBar.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
        }
        buttonBar.setLayoutParams(lp);
        requestLayout();
    }
}
