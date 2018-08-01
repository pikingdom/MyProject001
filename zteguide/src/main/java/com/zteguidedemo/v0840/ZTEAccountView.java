package com.zteguidedemo.v0840;

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

public class ZTEAccountView extends LinearLayout {
    private ZTEGuideMainView zteGuideMainView;
    private TextView previousStepBtn;//上一步
    private TextView nextStepBtn;//下一步
    private TextView registerBtn;//注册
    private TextView loginBtn;//登录


    public ZTEAccountView(Context context, ZTEGuideMainView zteGuideMainView) {
        super(context);
        this.zteGuideMainView = zteGuideMainView;
        initView();
    }

    private void initView(){
        addView(LayoutInflater.from(getContext()).inflate(R.layout.zte_account_view,null),
                new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        previousStepBtn = (TextView) findViewById(R.id.zte_previous_step_btn);
        nextStepBtn = (TextView) findViewById(R.id.zte_next_step_btn);
        registerBtn = (TextView) findViewById(R.id.zte_register_btn);
        loginBtn = (TextView) findViewById(R.id.zte_login_btn);

        previousStepBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zteGuideMainView.setCurrentlyShowView(ChangeViewInterface.MYPHONE_VIEW);
            }
        });
        nextStepBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zteGuideMainView.setCurrentlyShowView(ChangeViewInterface.FINGERPRINT_VIEW);
            }
        });
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent();
                    intent.setClassName("org.zx.AuthComp","cn.zte.auth.activity.Register");
                    getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent();
                    intent.setClassName("org.zx.AuthComp","cn.zte.auth.activity.Login");
                    getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
