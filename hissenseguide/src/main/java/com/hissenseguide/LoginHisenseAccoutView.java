package com.hissenseguide;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hissenseguide.listener.ChangeViewInterface;

/**登录海信账号
 * Created by xuqunxing on 2017/11/22.
 */
public class LoginHisenseAccoutView extends RelativeLayout implements View.OnClickListener {

    public static int WIFICALLBACK = 1000;//代开WiFi页面的回调（activityResult）
    private TextView backTv;
    private TextView nextTv;
    private ChangeViewInterface changeViewInterface;
    private TextView loginTv;
    private TextView registerTv;
    private Context context ;
    public LoginHisenseAccoutView(Context context) {
        super(context);
        this.context  = context;
        initView();
    }

    public LoginHisenseAccoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context  = context;
        initView();
    }

    public LoginHisenseAccoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context  = context;
        initView();
    }

    public LoginHisenseAccoutView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.context  = context;
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.guide_login_hisense_account,this);
        loginTv = (TextView) findViewById(R.id.guide_agreement_and_terms_login);
        registerTv = (TextView) findViewById(R.id.guide_agreement_and_terms_register);
        backTv = (TextView) findViewById(R.id.guide_activity_back_textview);
        nextTv = (TextView) findViewById(R.id.guide_activity_next_textview);
        nextTv.setText(getContext().getString(R.string.guide_skip));
        registerTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        backTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.AGREE_TERMS);
            }
        }else if(view == nextTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_SETTING);
            }
        }else if(view ==registerTv){
            try{//跳转到注册页面
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.hmct.account", "com.hmct.account.ui.RegistActivity");
                intent.setComponent(cn);
                getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(view ==loginTv){
            try{//跳转感到登录页面
                Intent intent = new Intent();
                ComponentName cn = new ComponentName("com.hmct.account", "com.hmct.account.ui.LoginActivity");
                intent.setComponent(cn);
                getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_SETTING);
        }
    }
}
