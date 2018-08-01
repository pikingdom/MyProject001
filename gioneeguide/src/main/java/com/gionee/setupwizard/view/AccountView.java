package com.gionee.setupwizard.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gionee.setupwizard.R;
import com.gionee.setupwizard.widget.banner.BannerViewGroup;
import com.gionee.setupwizard.widget.banner.BannerViewdata;

/**
 * 作者：xiaomao on 2017/7/18.
 */

public class AccountView extends LinearLayout {
    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    private TextView accountIgnore;//跳过
    private Button accountLoginBtn;//一键登录
    private Button accountRegisterBtn;//注册
    private BannerViewGroup bannerViewGroup;
    private ImageView imageView0,imageView1,imageView2;

    private Handler handler = new Handler();
    public AccountView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initView();
        initData();
        initListener();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_account,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        accountIgnore = (TextView) findViewById(R.id.gn_sw_layout_account_ignore);
        accountLoginBtn = (Button) findViewById(R.id.gn_sw_layout_account_login_btn);
        accountRegisterBtn = (Button) findViewById(R.id.gn_sw_layout_account_register_btn);
        bannerViewGroup = (BannerViewGroup) findViewById(R.id.gn_anim_account_viewpager);
        imageView0 = (ImageView) findViewById(R.id.gn_sw_layout_accout_progress_bar0);
        imageView1 = (ImageView) findViewById(R.id.gn_sw_layout_accout_progress_bar1);
        imageView2 = (ImageView) findViewById(R.id.gn_sw_layout_accout_progress_bar2);
        bannerViewGroup.setOnChangeListener(new BannerViewGroup.OnChangeListener() {
            @Override
            public void onChange(int index) {
                imageView0.setBackgroundResource(R.drawable.gn_feature_point);
                imageView1.setBackgroundResource(R.drawable.gn_feature_point);
                imageView2.setBackgroundResource(R.drawable.gn_feature_point);
                switch (index){
                    case 0:
                        imageView0.setBackgroundResource(R.drawable.gn_feature_point_cur);
                        break;
                    case 1:
                        imageView1.setBackgroundResource(R.drawable.gn_feature_point_cur);
                        break;
                    case 2:
                        imageView2.setBackgroundResource(R.drawable.gn_feature_point_cur);
                        break;
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        BannerViewdata data0 = new BannerViewdata(context, 0, null, null, getContentView(0), true, false);
        BannerViewdata data1 = new BannerViewdata(context, 1, null, null, getContentView(1), false, false);
        BannerViewdata data2 = new BannerViewdata(context, 2, null, null, getContentView(2), false, true);

        data0.setNextviewdata(data1);
        data0.setPreviewdata(data2);
        data1.setNextviewdata(data2);
        data1.setPreviewdata(data0);
        data2.setPreviewdata(data1);
        data2.setNextviewdata(data0);
        bannerViewGroup.setBannerViewData(data0);
    }
    private View getContentView(int index) {
        View view = inflate(context,R.layout.gn_item_layout_account,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.account_imageview);
        TextView textView = (TextView)view.findViewById(R.id.account_textview);
        TextView summaryTextView = (TextView) view.findViewById(R.id.account_summary_textview);
        if(index == 0){
            imageView.setBackgroundResource(R.drawable.gn_sw_layout_account_cloud);
            textView.setText(R.string.gn_sw_string_account_slide_title_text);
            summaryTextView.setText(R.string.gn_sw_string_account_slide_text_warranty);
        }else if (index == 1){
            imageView.setBackgroundResource(R.drawable.gn_sw_layout_account_location);
            textView.setText(R.string.gn_sw_string_account_slide_title_text_2);
            summaryTextView.setText(R.string.gn_sw_string_account_slide_text_2);
        }else{
            imageView.setBackgroundResource(R.drawable.gn_sw_layout_account_amigoos);
            textView.setText(R.string.gn_sw_string_account_slide_title_text_3);
            summaryTextView.setText(R.string.gn_sw_string_account_slide_text_3);
        }
        return view;
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        accountIgnore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.FINISH_VIEW);
                Toast.makeText(context,R.string.gn_sw_string_account_slide_ignore_warranty,Toast.LENGTH_SHORT).show();
            }
        });
        accountLoginBtn.setOnClickListener(new OnClickListener() {//一键注册
            @Override
            public void onClick(View view) {
                doAccountLogin();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.FINISH_VIEW);
                    }
                },1000);
            }
        });
        accountRegisterBtn.setOnClickListener(new OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                doLogin();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.FINISH_VIEW);
                    }
                },1000);
            }
        });
    }


    /**
     * 登录
     */
    private static final String SHOW_REGISTER = "isShowRegister";
    private static final String FROM = "from";
    public static final String FROM_GUIDE = "guide";
    private static final String ACCOUNT_PACKAGE = "com.gionee.account";
    private static final String LOGIN_ACTIVITY = "com.gionee.account.activity.LoginActivity";
    private static final int LOGIN_REQUEST_CODE = 1000100;
    private static final int LOGIN_ONEKEY_CODE = 1000101;
    private void doAccountLogin() {
        try {
            Intent intent = new Intent();
            intent.putExtra(SHOW_REGISTER, false);
            intent.putExtra(FROM, FROM_GUIDE);
            intent.setClassName(ACCOUNT_PACKAGE, LOGIN_ACTIVITY);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                ((Activity) context).startActivityForResult(intent, LOGIN_REQUEST_CODE);
                ((Activity) context).overridePendingTransition(R.anim.push_down_in, R.anim.push_up_out);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(context, context.getString(R.string.gn_sw_string_register_account_not_ready), Toast.LENGTH_SHORT).show();
    }

    private void doLogin(){
        try {
            Intent intent = new Intent();
            intent.putExtra(SHOW_REGISTER, false);
            intent.putExtra(FROM, FROM_GUIDE);
            intent.setClassName(ACCOUNT_PACKAGE, "com.gionee.account.activity.SmsDownRegisterStep1Activity");
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                ((Activity) context).startActivityForResult(intent, LOGIN_ONEKEY_CODE);
                ((Activity) context).overridePendingTransition(R.anim.push_down_in, R.anim.push_up_out);
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(context, context.getString(R.string.gn_sw_string_register_account_not_ready), Toast.LENGTH_SHORT).show();
    }
}
