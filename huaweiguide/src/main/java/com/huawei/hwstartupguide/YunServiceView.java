package com.huawei.hwstartupguide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

/**
 * 应用权限开关说明
 * Created by llf on 2017/7/20.
 */

public class YunServiceView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange{

    private ChangeViewInterface mChangeViewInterface;
    private Context mContent;

    private TextView mBackBtn;
    private TextView mBeginYunServiceTet;
    private TextView mNoBeginYunServiceTet;

    private TextView mPrevBtn;
    private TextView mNextBtn;
    private RelativeLayout buttonBar;

    public YunServiceView(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        mContent = context;
        mChangeViewInterface = changeViewInterface;
        init();
    }

    public YunServiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(mContent).inflate(R.layout.hauwei_yu_service_view, this);
        findViewsById();
        setListeners();
        initButtonBar();
    }

    protected void initButtonBar() {
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);
        this.mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        this.mPrevBtn.setVisibility(View.VISIBLE);
        this.mPrevBtn.setText(R.string.skip_format);
        this.mPrevBtn.setOnClickListener(this);
        this.mNextBtn = (TextView) findViewById(R.id.next_btn);
        this.mNextBtn.setVisibility(View.VISIBLE);
        this.mNextBtn.setText(R.string.begin_start_yun_service);
        this.mNextBtn.setOnClickListener(this);
    }

    private void findViewsById() {
//        mBackBtn = (TextView) findViewById(R.id.back_btn);
        mBeginYunServiceTet = (TextView) findViewById(R.id.begin_yun_service);
        mNoBeginYunServiceTet = (TextView) findViewById(R.id.no_begin_yun_service);
    }

    private void setListeners(){
//        mBackBtn.setOnClickListener(this);
        mBeginYunServiceTet.setOnClickListener(this);
        mNoBeginYunServiceTet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
//        if (id == R.id.back_btn) {
//            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_MIGRATION_VIEW);
//        } else
        if (id == R.id.begin_yun_service) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_TOUCH_VIEW);
        } else if (id == R.id.no_begin_yun_service) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_TOUCH_VIEW);
        } else if(view == mPrevBtn){
            if(mChangeViewInterface != null){
                mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_TOUCH_VIEW);
            }
        } else if(view == mNextBtn){
            if(mChangeViewInterface != null){
                mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_TOUCH_VIEW);
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
