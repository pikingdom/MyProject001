package com.huawei.hwstartupguide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

/**
 * 应用权限开关说明
 * Created by llf on 2017/7/20.
 */

public class ServiceAuthorityView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange{

    private ChangeViewInterface mChangeViewInterface;
    private Context mContent;

    private TextView mNextStepBtn;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private CheckBox mCheckBox5;
    private CheckBox mCheckBox6;
    private LinearLayout mCheckBoxLaout1,mCheckBoxLaout2,mCheckBoxLaout3,mCheckBoxLaout4,mCheckBoxLaout5,mCheckBoxLaout6;

    public ServiceAuthorityView(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        mContent = context;
        mChangeViewInterface = changeViewInterface;
        init();
    }

    public ServiceAuthorityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(mContent).inflate(R.layout.hauwei_service_authory_view, this);
        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mNextStepBtn = (TextView) findViewById(R.id.nextstep_btn);
        mCheckBox1 = (CheckBox) findViewById(R.id.checkbox1);
        mCheckBox2 = (CheckBox) findViewById(R.id.checkbox2);
        mCheckBox3 = (CheckBox) findViewById(R.id.checkbox3);
        mCheckBox4 = (CheckBox) findViewById(R.id.checkbox4);
        mCheckBox5 = (CheckBox) findViewById(R.id.checkbox5);
        mCheckBox6 = (CheckBox) findViewById(R.id.checkbox6);

        mCheckBoxLaout1 = (LinearLayout) findViewById(R.id.check_layout1);
        mCheckBoxLaout2 = (LinearLayout) findViewById(R.id.check_layout2);
        mCheckBoxLaout3 = (LinearLayout) findViewById(R.id.check_layout3);
        mCheckBoxLaout4 = (LinearLayout) findViewById(R.id.check_layout4);
        mCheckBoxLaout5 = (LinearLayout) findViewById(R.id.check_layout5);
        mCheckBoxLaout6 = (LinearLayout) findViewById(R.id.check_layout6);




    }

    private void setViews() {
        mCheckBox1.setChecked(true);
        mCheckBox2.setChecked(false);
        mCheckBox3.setChecked(true);
        mCheckBox4.setChecked(false);
        mCheckBox5.setChecked(true);
        mCheckBox6.setChecked(false);
    }

    private void setListeners(){
        mNextStepBtn.setOnClickListener(this);
        mCheckBoxLaout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox1.isChecked()) {
                    mCheckBox2.setChecked(true);
                    mCheckBox1.setChecked(false);
                } else {
                    mCheckBox1.setChecked(true);
                    mCheckBox2.setChecked(false);
                }
            }
        });

        mCheckBoxLaout2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox2.isChecked()) {
                    mCheckBox1.setChecked(true);
                    mCheckBox2.setChecked(false);
                } else {
                    mCheckBox2.setChecked(true);
                    mCheckBox1.setChecked(false);
                }
            }
        });

        mCheckBoxLaout3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox3.isChecked()) {
                    mCheckBox4.setChecked(true);
                    mCheckBox3.setChecked(false);
                } else {
                    mCheckBox3.setChecked(true);
                    mCheckBox4.setChecked(false);
                }
            }
        });

        mCheckBoxLaout4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox4.isChecked()) {
                    mCheckBox3.setChecked(true);
                    mCheckBox4.setChecked(false);
                } else {
                    mCheckBox4.setChecked(true);
                    mCheckBox3.setChecked(false);
                }
            }
        });

        mCheckBoxLaout5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox5.isChecked()) {
                    mCheckBox6.setChecked(true);
                    mCheckBox5.setChecked(false);
                } else {
                    mCheckBox5.setChecked(true);
                    mCheckBox6.setChecked(false);
                }
            }
        });

        mCheckBoxLaout6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox6.isChecked()) {
                    mCheckBox5.setChecked(true);
                    mCheckBox6.setChecked(false);
                } else {
                    mCheckBox6.setChecked(true);
                    mCheckBox5.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.nextstep_btn) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
        }
    }

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mNextStepBtn.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
        }
        mNextStepBtn.setLayoutParams(lp);
        requestLayout();
    }
}
