package com.gionee.setupwizard.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.gionee.setupwizard.R;
import com.gionee.setupwizard.widget.TimeSwitcher;
import com.gionee.setupwizard.widget.TimeView;

import java.util.Calendar;

/**
 * 时间设置界面
 * 作者：xiaomao on 2017/7/15.
 */

public class TimeSettingView  extends LinearLayout {
    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    private TimeView mTimeView;
    private Button mJumpButton;
    private TimeSwitcher timeswitcher;
    private CheckBox checkBox;

    public TimeSettingView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_time,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        mTimeView =  (TimeView) findViewById(R.id.gn_sw_layout_datetime_time);
        mTimeView.updateTime(System.currentTimeMillis(),false);
        mJumpButton = (Button) findViewById(R.id.gn_sw_layout_datetime_guide_next);
        timeswitcher = (TimeSwitcher) findViewById(R.id.layout_time_timeswitcher);
        timeswitcher.setRefurbishTimeViewTime(new TimeSwitcher.RefurbishTimeViewTime() {
            @Override
            public void refurbishTime(Calendar calendar) {
                mTimeView.updateTime(calendar.getTimeInMillis(),false);//回调刷新上面时间控件显示的时间
                //TODO 这里去修改系统时间，没有系统权限ORZ修改不了啊~~
            }
        });
        checkBox = (CheckBox) findViewById(R.id.layout_time_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){//联网自动获取时间
                    timeswitcher.setOnEnableColor(false);
                }else{//手动修改时间
                    timeswitcher.setOnEnableColor(true);
                }
            }
        });
        mJumpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.INPUT_METHOD_VIEW);
            }
        });
    }
}
