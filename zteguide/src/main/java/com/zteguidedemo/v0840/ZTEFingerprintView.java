package com.zteguidedemo.v0840;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zteguidedemo.R;

/**
 * 账号界面
 * 作者：xiaomao on 2017/8/21.
 */

public class ZTEFingerprintView extends LinearLayout {
    private ZTEGuideMainView zteGuideMainView;
    private TextView addFingerprintBtn;//添加指纹
    private TextView previousStepBtn;//上一步
    private TextView nextStepBtn;//下一步

    private Context context;
    public ZTEFingerprintView(Context context, ZTEGuideMainView zteGuideMainView) {
        super(context);
        this.context = context;
        this.zteGuideMainView = zteGuideMainView;
        initView();
    }

    private void initView(){
        addView(LayoutInflater.from(getContext()).inflate(R.layout.zte_fingerprint_view,null),
                new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        addFingerprintBtn = (TextView) findViewById(R.id.zte_add_fingerprint_btn);
        previousStepBtn = (TextView) findViewById(R.id.zte_previous_step_btn);
        nextStepBtn = (TextView) findViewById(R.id.zte_next_step_btn);

        previousStepBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zteGuideMainView.setCurrentlyShowView(ChangeViewInterface.ACCOUNT_VIEW);
            }
        });
        nextStepBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zteGuideMainView.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW);
            }
        });
        addFingerprintBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent();
                    intent.setClassName("com.android.settings","com.android.settings.ChooseLockGeneric");
                    getContext().startActivity(intent);
                    ((Activity)context).overridePendingTransition(R.anim.activity_in_from_right,0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
