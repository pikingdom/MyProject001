package com.hissenseguide;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hissenseguide.listener.ChangeViewInterface;

/**使用条款
 * Created by xuqunxing on 2017/11/21.
 */
public class HiSenseE77_GuideAgreementAndTermsView extends RelativeLayout implements View.OnClickListener {

    private TextView nextTv;
    private TextView backTv;
    private LinearLayout userAgreementLL;

    public HiSenseE77_GuideAgreementAndTermsView(Context context) {
        super(context);
        initView();
    }

    public HiSenseE77_GuideAgreementAndTermsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HiSenseE77_GuideAgreementAndTermsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public HiSenseE77_GuideAgreementAndTermsView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private ChangeViewInterface changeViewInterface;

    private void initView(){
        View.inflate(getContext(), R.layout.guide_agreement_and_terms_activity,this);
        nextTv = (TextView) findViewById(R.id.guide_activity_next_textview);
        backTv = (TextView) findViewById(R.id.guide_activity_back_textview);
        nextTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
        userAgreementLL = (LinearLayout) findViewById(R.id.guide_agreement_and_terms_useragreement);
        userAgreementLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LOGIN_HISENSE_ACCOUNT);
            }
        }else if(view == backTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
            }
        }else if(view == userAgreementLL){
            try{//跳转到用户协议
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.android.guide", "com.android.guide.GuideUserAgreement");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cn);
                getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
