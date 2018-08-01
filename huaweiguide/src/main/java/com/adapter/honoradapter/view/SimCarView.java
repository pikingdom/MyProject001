package com.adapter.honoradapter.view;

import android.content.Context;
import android.util.AttributeSet;
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
 * Created by xuqunxing on 2017/7/20.
 */
public class SimCarView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange {

    private ChangeViewInterface changeViewInterface;
    private TextView mPrevBtn;
    private TextView mNextBtn;
    private RelativeLayout buttonBar;

    public SimCarView(Context context) {
        super(context);
    }

    public SimCarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SimCarView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_sim_car,this);
        setLogoViewHeight();
        initButtonBar();
    }

    protected void initButtonBar() {
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);
        mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        mPrevBtn.setVisibility(View.VISIBLE);
        mPrevBtn.setText(getContext().getString(R.string.back_format));
        mNextBtn = (TextView) findViewById(R.id.next_btn);
        mNextBtn.setVisibility(View.VISIBLE);
        mNextBtn.setText(getContext().getString(R.string.skip_format));
        mPrevBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
    }

    private void setLogoViewHeight() {
        View logoView = findViewById(R.id.logo_view);
        View simLogo = findViewById(R.id.sim_logo);
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

    @Override
    public void onClick(View view) {
        if(view == mPrevBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
            }
        }else if(view == mNextBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.PROTOCOL_VIEW);
            }
        }
    }

    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
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
