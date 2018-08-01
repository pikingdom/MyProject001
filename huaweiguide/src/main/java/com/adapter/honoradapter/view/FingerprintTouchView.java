package com.adapter.honoradapter.view;

import android.content.Context;
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

public class FingerprintTouchView extends FrameLayout implements View.OnClickListener,NavigationVisibilityChange {

    private TextView mPrevBtn;
    private TextView mNextBtn;
    private RelativeLayout buttonBar;
    private ChangeViewInterface changeViewInterface;
    public FingerprintTouchView(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        this.changeViewInterface = changeViewInterface;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.page_fingerprint_touch,this);
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
        this.mPrevBtn.setVisibility(View.GONE);
        this.mNextBtn = (TextView) findViewById(R.id.next_btn);
        this.mNextBtn.setVisibility(View.VISIBLE);
        this.mNextBtn.setText(R.string.next_);
        this.mNextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mNextBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_CHECK_VIEW);
            }
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
